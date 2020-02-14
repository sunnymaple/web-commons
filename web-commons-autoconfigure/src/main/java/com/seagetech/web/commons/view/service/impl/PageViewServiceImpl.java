package com.seagetech.web.commons.view.service.impl;

import cn.afterturn.easypoi.exception.excel.ExcelExportException;
import com.seagetech.common.util.SeageJson;
import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.bind.FunctionType;
import com.seagetech.web.commons.bind.OptionType;
import com.seagetech.web.commons.login.session.ISessionHandler;
import com.seagetech.web.commons.util.Excel;
import com.seagetech.web.commons.util.ExcelUtils;
import com.seagetech.web.commons.view.exception.NoSuchTemplateException;
import com.seagetech.web.commons.view.exception.UniqueException;
import com.seagetech.web.commons.view.load.*;
import com.seagetech.web.commons.view.load.exception.FileDownLoadException;
import com.seagetech.web.commons.view.mapper.PageViewMapper;
import com.seagetech.web.commons.view.mapper.def.DefaultValueEnum;
import com.seagetech.web.commons.view.mapper.def.IDefaultValue;
import com.seagetech.web.commons.view.service.PageViewService;
import com.seagetech.web.exception.ParamVerifyException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.AbstractResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

    @Autowired
    private Validator validator;

    private ApplicationContext applicationContext;

    @Autowired
    private HttpServletRequest request;

    /**
     * 列表分页查询
     *
     * @param viewName 视图名称
     * @param params   查询条件
     * @return
     */
    @Override
    public List<Map<String, Object>> getList(String viewName, Map<String, Object> params) {
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
        distinct(pageViewInfo,params);
        //插入数据
        pageViewMapper.insert(sessionHandler.getUserName(), viewName, params);
    }

    /**
     * 去重
     * @param pageViewInfo
     * @param params
     */
    private void distinct(PageViewInfo pageViewInfo,Map<String, Object> params){
        distinct(pageViewInfo,params,null);
    }

    /**
     * 去重
     * @param pageViewInfo
     * @param params
     * @param primaryKey 是否更新
     */
    private void distinct(PageViewInfo pageViewInfo,Map<String, Object> params,String primaryKey){
        List<IFunctionInfo> functionInfos = pageViewInfo.get(FunctionType.ADD);
        functionInfos.forEach(iFunctionInfo -> {
            AddInfo addInfo = (AddInfo) iFunctionInfo;
            if (addInfo.isUnique()) {
                String name = addInfo.getName();
                Object value = params.get(name);
                if (!SeageUtils.isEmpty(value)) {
                    Map<String, Object> queryParams = new HashMap<>(1);
                    queryParams.put(name, value);
                    List<Map<String, Object>> results = getList(pageViewInfo.getViewName(), queryParams);
                    if (results != null && results.size() > 0) {
                        if (SeageUtils.isEmpty(primaryKey) || results.size()>1){
                            throw new UniqueException(value + "已存在!");
                        }
                        Map<String, Object> result = results.get(0);
                        PrimaryKeyInfo primaryKeyInfo = pageViewInfo.getPrimaryKey();
                        String primaryKeyName = primaryKeyInfo.getName();
                        Object resultPrimaryKey = result.get(primaryKeyName);
                        if (!SeageUtils.isEmpty(resultPrimaryKey) && !Objects.equals(resultPrimaryKey.toString(),primaryKey)){
                            throw new UniqueException(value + "已存在!");
                        }
                    }

                }
            }
        });
    }

    /**
     * 修改功能
     *
     * @param viewName 视图名称
     * @param params   修改内容
     */
    @Override
    public void update(String viewName, Map<String, Object> params) {
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);
        boolean enableCustomUpdate = pageViewInfo.enableCustomFunction(FunctionType.UPDATE);
        if (enableCustomUpdate){
            pageViewInfo.getPageViewCustom().customUpdate(viewName, params);
        }
        //获取主键
        PrimaryKeyInfo primaryKeyInfo = pageViewInfo.getPrimaryKey();
        String primaryKeyName = primaryKeyInfo.getName();
        Optional<Object> primaryKeyOp = Optional.ofNullable(params.get(primaryKeyName));
        String primaryKey = primaryKeyOp.orElseThrow(() -> new ParamVerifyException("主键值" + primaryKeyName + "不能为空！")).toString();
        //判重
        distinct(pageViewInfo,params,primaryKey);
        //
        pageViewMapper.update(sessionHandler.getUserName(), viewName, params);
    }

    /**
     * 根据主键删除数据
     * @param viewName 视图名称
     * @param ids 删除主键ID
     * @param status 如果是逻辑删除需要传递值
     *              如启用、禁用、删除，通过状态去控制
     */
    @Override
    public void deleteById(String viewName, String[] ids,String status) {
        pageViewMapper.deleteById(viewName, ids,status);
    }

    /**
     * 导入文件
     *
     * @param viewName 视图名称
     * @param multipartFile  文件
     */
    @Override
    public void importTable(String viewName, MultipartFile multipartFile) throws Exception {
        //文件验证
        Excel.verifyThrow(multipartFile);
        Workbook workbook = Excel.getWorkbook(multipartFile);
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
        List<IFunctionInfo> functionInfos = pageViewInfo.getThrow(FunctionType.IMPORT);
        List<Map<String, Object>> columns = new ArrayList<>();
        for (int j = firstRow; j <= sheet.getLastRowNum(); j++) {
            Row row = sheet.getRow(j);
            if (row == null || row.getFirstCellNum() == j) {
                continue;
            }
            //第一行，拼接sql字段
            Map<String, Object> values = new HashMap<>(functionInfos.size());
            for (IFunctionInfo functionInfo : functionInfos) {
                ImportInfo importInfo = (ImportInfo) functionInfo;
                String value = null;
                //需要判断是否有需要赋默认值的,默认值优先
                if (!SeageUtils.isEmpty(importInfo.getDefaultValue())) {
                    //默认值
                    Class<? extends IDefaultValue> defClass = DefaultValueEnum.getDefClass(importInfo.getDefaultValue());
                    IDefaultValue iDefaultValue = defClass.newInstance();
                    value = iDefaultValue.getDefaultValue(sessionHandler.getUserName(), importInfo.getName(), importInfo.getDefaultValue());
                } else if (!SeageUtils.isEmpty(importInfo.getOption())){
                    //获取自定义的选项，一般是通过对应的value（Excel导入的值）获取对应的key(一般为数据库表主键，或者字典表的id值)
                    Class<? extends IOption> iOptionClass = importInfo.getOption();
                    IOption iOption = applicationContext.getBean(iOptionClass);
                    value = iOption.getOptions(importInfo.getOptionParams())
                            .stream()
                            .filter(option -> Objects.equals(option.getValue(), getCellValue(row.getCell(importInfo.getCol()))))
                            .map(Option::getKey)
                            .findFirst()
                            .orElseThrow(newViewException(420, "第" + (j + 1) + "行第" + (importInfo.getCol() + 1) + "列用户导入字段未找到对应值"));

                }else {
                    //可以直接读取的
                    value = getCellValue(row.getCell(importInfo.getCol()));
                }
                if (value!=null){
                    values.put(importInfo.getColumnName().toLowerCase(), value);
                }
            }
            Object o = SeageJson.map2Object(values, pageViewClass);
            //参数验证
            validateBean(j + 1, o);
            //判重
            distinct(pageViewInfo,values);
            columns.add(values);
        }
        pageViewMapper.importTable(columns, tableName);
    }

    /**
     * 模板下载
     *
     * @param viewName 视图名称
     * @throws Exception
     */
    @Override
    public ResponseEntity<AbstractResource> getTemplate(String viewName) throws Exception {
        PageViewInfo pageViewInfo = PageViewContainer.getPageViewInfo(viewName);
        String path = pageViewInfo.getExcelTemplatePath();
        if (!SeageUtils.isEmpty(path)){
            try {
                InputStream stream = this.getClass().getResourceAsStream(path);
                return Excel.export(stream);
            } catch (NullPointerException e) {
                throw new NoSuchTemplateException(path);
            }
        }else {
            throw new NoSuchTemplateException(path);
        }
    }


//    @Override
//    public void excelFormWork(String filePaths, String excelName, HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        if (SeageUtils.isEmpty(filePaths)) {
//            throw new FileDownLoadException("文件所在目录不能为空");
//        }
//        if (SeageUtils.isEmpty(excelName)) {
//            throw new FileDownLoadException("文件名称不能为空");
//        }
//        ExcelUtils excelUtils = new ExcelUtils();
//        excelUtils.downLoad(excelName, filePaths, req, resp);
//    }

    /**
     * excel表格导出
     *
     * @param viewName 视图名称
     * @param params   查询所用到的参数
     * @param request
     * @param response
     */
    @Override
    public void exportExcel(String viewName, Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> list = pageViewMapper.getList(viewName, params);
        List<List<String>> results = new ArrayList<>();
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);
        //不需要视图，直接使用表名称
        List<IFunctionInfo> functionInfos = pageViewInfo.get(FunctionType.EXPORT);
        if(CollectionUtils.isEmpty(functionInfos)){
            throw new ExcelExportException("未查找到需要导出信息");
        }
        for (int i = 0; i < list.size(); i++) {
            List<String> head = new LinkedList<>();
            List<String> body = new LinkedList<>();
            for (IFunctionInfo functionInfo : functionInfos) {
                ExportInfo importInfo = (ExportInfo) functionInfo;
                if (i == 0) {
                    head.add(importInfo.getHeadName());
                }
                Object value = list.get(i).get(importInfo.getName());
                body.add(SeageUtils.isEmpty(value) ? "" : value.toString());
            }
            if (i == 0) {
                results.add(head);
            }
            results.add(body);
        }
        ExcelUtils excelUtils = new ExcelUtils();
        excelUtils.makeExcelAndDownload(pageViewInfo.getViewName(), results, request, response);
    }

    /**
     * 获取下拉选项
     *
     * @param viewName 视图名称
     * @param params   请求参数
     * @return
     */
    @Override
    public List<Option> getOptions(String viewName, Map<String, Object> params) {
        PageViewContainer pageViewContainer = PageViewContainer.getInstance();
        PageViewInfo pageViewInfo = pageViewContainer.get(viewName);
        List<IFunctionInfo> options = pageViewInfo.getThrow(FunctionType.OPTION);
        List<Map<String, Object>> results = getList(viewName, params);
        return results.stream().map(map->{
            Option result = null;
            for (IFunctionInfo option : options){
                OptionInfo optionInfo = (OptionInfo) option;
                Object o = map.get(optionInfo.getName());
                if (!SeageUtils.isEmpty(o)){
                    if (SeageUtils.isEmpty(result)){
                        result = new Option();
                    }
                    if (Objects.equals(optionInfo.getOptionType(), OptionType.KEY)){
                        result.setKey(o.toString());
                    }else {
                        result.setValue(o.toString());
                    }
                }
            }
           return result;
        }).collect(Collectors.toList());
    }


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
        this.applicationContext = applicationContext;
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
