package com.seagetech.web.commons.view.service.impl;

import com.seagetech.common.util.SeageJson;
import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.login.session.ISessionHandler;
import com.seagetech.web.commons.view.exception.UniqueException;
import com.seagetech.web.commons.view.load.*;
import com.seagetech.web.commons.view.load.exception.FileDownLoadException;
import com.seagetech.web.commons.view.mapper.PageViewMapper;
import com.seagetech.web.commons.view.mapper.def.DefaultValueEnum;
import com.seagetech.web.commons.view.mapper.def.IDefaultValue;
import com.seagetech.web.commons.view.service.PageViewService;
import com.seagetech.web.exception.ParamVerifyException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Supplier;

/**
 * 列表查询业务实现层
 *
 * @author wangzb
 * @date 2019/12/26 9:25
 * @company 矽甲（上海）信息科技有限公司
 */
@Service
public class PageViewServiceImpl implements PageViewService, ApplicationContextAware {

    @Autowired
    private PageViewMapper pageViewMapper;

    @Autowired
    private ISessionHandler sessionHandler;
    private ApplicationContext applicationContext;


    /**
     * 列表分页查询
     *
     * @param viewName 视图名称
     * @param params   查询条件
     * @return
     */
    @Override
    public List<Map<String, Object>> getListByPage(String viewName, Map<String, Object> params) {
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);
        boolean enableCustomQuery = pageViewInfo.enableCustomFunction(FunctionType.QUERY);
        if (enableCustomQuery) {
            return pageViewInfo.getPageViewCustom().customQuery(viewName, params);
        }
        List<Map<String, Object>> list = pageViewMapper.getList(viewName, params);
        return list;
    }


    /**
     * 添加、新增
     *
     * @param viewName 视图名称
     * @param params   新增内容
     */
    @Override
    public void add(String viewName, Map<String, Object> params) {
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);
        boolean enableCustomAdd = pageViewInfo.enableCustomFunction(FunctionType.ADD);
        if (enableCustomAdd) {
            //自定义方法
            pageViewInfo.getPageViewCustom().customAdd(viewName, params);
        }
        //判重
        List<IFunctionInfo> functionInfos = pageViewInfo.get(FunctionType.ADD);
        functionInfos.forEach(iFunctionInfo -> {
            AddInfo addInfo = (AddInfo) iFunctionInfo;
            if (addInfo.isUnique()) {
                String name = addInfo.getName();
                Object value = params.get(name);
                if (!SeageUtils.isEmpty(value)) {
                    Map<String, Object> queryParams = new HashMap<>(1);
                    queryParams.put(name, value);
                    List<Map<String, Object>> result = getListByPage(viewName, queryParams);
                    if (result != null && result.size() > 0) {
                        throw new UniqueException(value + "已存在!");
                    }
                }
            }
        });
        //插入数据
        pageViewMapper.insert(sessionHandler.getUser(), viewName, params);
    }

    /**
     * 根据主键删除
     *
     * @param viewName 视图名称
     * @param deleteId 删除主键ID
     */
    @Override
    public void deleteById(String viewName, Integer deleteId) {
        pageViewMapper.deleteById(viewName, deleteId);
    }

    /**
     * 导入文件
     *
     * @param viewName 视图名称
     * @param dataPic  文件
     */
    @Override
    public void importTable(String viewName, MultipartFile dataPic, HttpServletRequest request) throws Exception {
        String path = request.getSession().getServletContext().getRealPath("tempFile");
        String fileName = dataPic.getOriginalFilename();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file1 = new File(path + File.separator + System.currentTimeMillis() + "_" + fileName);
        if (file1.exists()) {
            file1.delete();
        }
        dataPic.transferTo(file1);
        FileInputStream stream = new FileInputStream(file1);
        // 获取Ecle对象
        Workbook workbook = WorkbookFactory.create(stream);
        //获取第一个sheet的数据
        Sheet sheet = workbook.getSheetAt(0);
        //获取容器，找到对应的实体类，获取相关信息
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);
        Class pageViewClass = pageViewInfo.getPageViewClass();
        //第几行开始
        Integer firstRow = pageViewInfo.getRow();
        //不需要视图，直接使用表名称
        String tableName = pageViewInfo.getTable();
        List<IFunctionInfo> functionInfos = pageViewInfo.get(FunctionType.IMPORT);

        List<Map<String, Object>> columns = new ArrayList<>();
        for (int j = firstRow; j <= sheet.getLastRowNum(); j++) {
            Row row = sheet.getRow(j);
            if (row == null || row.getFirstCellNum() == j) {
                continue;
            }
            //第一行，拼接sql字段
            Map<String, Object> values = new HashMap<>();
            for (IFunctionInfo functionInfo : functionInfos) {
                ImportInfo importInfo = (ImportInfo) functionInfo;
                //需要判断是否有需要赋默认值的
                if (importInfo.getCol().equals(-1)) {
                    Class<? extends IDefaultValue> defClass = DefaultValueEnum.getDefClass(importInfo.getDefaultValue());
                    IDefaultValue iDefaultValue = null;
                    iDefaultValue = defClass.newInstance();
                    String defaultValue = iDefaultValue.getDefaultValue("1", importInfo.getName(), importInfo.getDefaultValue());
                    values.put(importInfo.getColumnName().toLowerCase(), defaultValue);

                } else {
                    //默认为Void，如果被改了，需要重新获取
                    //字典表查询数据
                    if (!importInfo.getOption().equals(Void.class)) {
                        final OptionImpl bean = applicationContext.getBean(OptionImpl.class);
                        String any = bean.getOptions()
                                .stream()
                                .filter(option -> option.getValue().equals(getCellValue(row.getCell(importInfo.getCol()))))
                                .map(Option::getKey)
                                .findAny()
                                .orElseThrow(newViewException(420, "第" + (j + 1) + "行第" + (importInfo.getCol() + 1) + "列用户导入字段未找到对应值"));
                        values.put(importInfo.getColumnName(), any);
                    } else {
                        //可以直接读取的
                        values.put(importInfo.getColumnName().toLowerCase(), getCellValue(row.getCell(importInfo.getCol())));
                    }
                }


            }
            Object o = SeageJson.map2Object(values, pageViewClass);
            validateBean(j + 1, o);
            columns.add(values);
        }
        pageViewMapper.importTable(columns, tableName);
    }

    @Override
    public void excelFormWork(String filePaths, String excelName, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (SeageUtils.isEmpty(filePaths)) {
            throw new FileDownLoadException("文件所在目录不能为空");
        }
        if (SeageUtils.isEmpty(excelName)) {
            throw new FileDownLoadException("文件名称不能为空");
        }
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        //项目根目录（绝对路径）
        String filePath = getClass().getResource("/").getPath() + filePaths + File.separator;
        //导出excel文件名称,包括后缀名
        //2 拼接excel、
        resp.setContentType("multipart/form-data");
        resp.setHeader("Content-disposition", "attachment; filename="
                + encodeChineseDownloadFileName(req, excelName));
        //excel文件下载路径（相对路径，因为下载时使用的是相对路径）
        file = new File(filePath + excelName);
        in = new FileInputStream(file);
        //通过response获取OutputStream对象(out)
        out = new BufferedOutputStream(resp.getOutputStream());
        int b = 0;
        byte[] buffer = new byte[2048];
        while ((b = in.read(buffer)) != -1) {
            //输出到客户端
            out.write(buffer, 0, b);
            out.flush();
        }
        out.close();
    }

    /**
     * 文件名格式化
     */
    private static String encodeChineseDownloadFileName(HttpServletRequest req, String pFileName) throws UnsupportedEncodingException {
        String filename = null;
        String agent = req.getHeader("USER-AGENT");
        if (null != agent) {
            if (-1 != agent.indexOf("Firefox")) {//Firefox
                filename = "=?UTF-8?B?" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(pFileName.getBytes("UTF-8")))) + "?=";
            } else if (-1 != agent.indexOf("Chrome")) {//Chrome
                filename = new String(pFileName.getBytes(), "ISO8859-1");
            } else {//IE7+
                filename = java.net.URLEncoder.encode(pFileName, "UTF-8");
                filename = StringUtils.replace(filename, "+", "%20");//替换空格
            }
        } else {
            filename = pFileName;
        }
        return filename;
    }

    @Autowired
    private Validator validator;

    /**
     * 参数验证
     *
     * @param t      验证参数的类
     * @param groups
     * @param <T>
     */
    public <T> void validateBean(int i, T t, Class<?>... groups) {
        Set<ConstraintViolation<T>> violationSet = validator.validate(t, groups);
        boolean hasError = violationSet != null && violationSet.size() > 0;
        if (hasError) {
            for (ConstraintViolation<T> violation : violationSet) {
                throw new ParamVerifyException("第" + i + "行" + violation.getMessage());
            }
        }
    }

    public static Supplier<ParamVerifyException> newViewException(Integer status, String message) {
        if (Objects.equals(System.getenv("FILL_IN_STACK"), "true")) {
            return () -> new ParamVerifyException(message);
        } else {
            return () -> new ParamVerifyException(message) {
                @Override
                public synchronized Throwable fillInStackTrace() {
                    return this;
                }
            };
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = this.applicationContext;
    }

    /**
     * 读取可能存在自定义单元格格式的数据
     *
     * @param cell cell
     * @return
     */
    private static String getCellValue(Cell cell) {
        DecimalFormat df = new DecimalFormat("#");
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return df.format(cell.getNumericCellValue());
            default:
                return null;
        }
    }
}
