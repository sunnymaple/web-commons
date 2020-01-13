package com.seagetech.web.commons.util;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.Boolean;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * excel工具类
 *
 * @author gdl
 * @date 2020/1/13 9:45
 * @company 矽甲（上海）信息科技有限公司
 */
public class ExcelUtils {
    /**
     * 文件下载
     *
     * @param excelName 文件名
     * @param filePaths 路径
     * @param req       request
     * @param resp      response
     * @throws Exception
     */
    public void downLoad(String excelName, String filePaths, HttpServletRequest req, HttpServletResponse resp) throws Exception {
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

    /**
     * excel组装并导出
     *
     * @param fileName 文件名
     * @param list     内容
     * @param req      request
     * @param resp     response
     * @throws Exception
     */
    public void makeExcelAndDownload(String fileName, List<List<String>> list, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        FileInputStream in = null;
        OutputStream out = null;
        File file = null;
        //项目根目录（绝对路径）
        String filePath = getClass().getResource("/").getPath() + fileName + File.separator;
        //导出excel文件名称,包括后缀名
        String fileNameXls = fileName + ".xls";
        try {
            //创建输出流
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(filePath, fileNameXls);
            if (!file.exists()) {
                file.createNewFile(); //创建文件
            }
            out = new FileOutputStream(file);
            if (list.isEmpty()) {
                throw new Exception("当前没有数据需要导出！");
            }
            //jxl方式生成excel表格
            makeExcel(fileName, list, out);

            //2 拼接excel
            resp.setContentType("multipart/form-data");
            resp.setHeader("Content-disposition", "attachment; filename=\""
                    + encodeChineseDownloadFileName(req, fileName + ".xls") + "\"");
            //excel文件下载路径（相对路径，因为下载时使用的是相对路径）
            file = new File(filePath + fileNameXls);
            in = new FileInputStream(file);
            //通过response获取OutputStream对象(out)
            out = new BufferedOutputStream(resp.getOutputStream());
            int b = 0;
            byte[] buffer = new byte[2048];
            while ((b = in.read(buffer)) != -1) {
                //输出到客户端
                out.write(buffer, 0, b);
            }
            out.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 制作Excel
     */
    private static void makeExcel(String fileName, List<List<String>> list, OutputStream out) throws Exception {
        WritableWorkbook workbook = null;
        try {
            //创建工作薄
            workbook = Workbook.createWorkbook(out);
            //创建sheet页
            WritableSheet sheet = workbook.createSheet(fileName, 0);
            int cols = list.get(0).size();
            //设置表列宽度
            setColsWidth(sheet, cols);
            //建立表格头部标题
//            headerBuilder(sheet, fileName, cols);
            //建立中间内容
            centerBuilder(sheet, list);
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }


    /**
     * 设置列宽
     */
    private static void setColsWidth(WritableSheet sheet, int cols) {
        for (int i = 0; i < cols + 1; i++) {
            if (i == 0) {
                sheet.setColumnView(i, 8);
                continue;
            }
            if (cols <= 10) {
                sheet.setColumnView(i, 20);
            } else {
                sheet.setColumnView(i, 15);
            }
        }
    }

    /**
     * 设置标题  未使用
     */
    private static void headerBuilder(WritableSheet sheet, String fileName, int cols) throws Exception {
        // 设置第一行（title）
        WritableCellFormat first = getStyle(18, true);
        sheet.addCell(new Label(0, 0, fileName, first));
        WritableCellFormat datas = getStyleColor(11);
        int dateSpan = 0;
        //自适应列数样式
        if (cols <= 5) {
            dateSpan = 1;
        } else {
            dateSpan = 2;
        }
        //标题独占2行
        sheet.mergeCells(0, 0, cols, 1);
        //日期行
        sheet.mergeCells(0, 2, cols - 1 - dateSpan, 2);
        sheet.addCell(new Label(cols - dateSpan, 2, "导出时间", datas));
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        sheet.addCell(new Label(cols + 1 - dateSpan, 2, date, datas));
        sheet.mergeCells(cols + 1 - dateSpan, 2, cols, 2);
    }

    /**
     * 设置中部主体内容
     *
     * @throws WriteException
     * @throws RowsExceededException
     */
    private static void centerBuilder(WritableSheet sheet, List<List<String>> list) throws Exception {
        int count = 1;
        WritableCellFormat data = getStyleColor(12);
        WritableCellFormat head = getStyleColor(14);
        for (int i = 0; i < list.size(); i++) {
            List<String> subList = list.get(i);
            if (i == 0) {
                sheet.addCell(new Label(0, i, "序号", data));
            } else {
                sheet.addCell(new Label(0, i, String.valueOf(count++), data));
            }
            for (int j = 0; j < subList.size(); j++) {
                if (i == 0) {
                    sheet.addCell(new Label(j + 1, i, subList.get(j), head));
                } else {
                    sheet.addCell(new Label(j + 1, i, subList.get(j), data));
                }

            }
        }
    }

    /**
     * 获取单元格样式
     *
     * @param font 字号
     * @param bold 是否加粗
     * @return
     * @throws Exception
     */
    private static WritableCellFormat getStyle(int font, boolean bold) {
        WritableCellFormat write = null;
        try {
            WritableFont wfont = null;
            if (bold) {
                // 加粗
                wfont = new WritableFont(WritableFont.ARIAL, font,
                        WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            } else {
                wfont = new WritableFont(WritableFont.ARIAL, font,
                        WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            }
            write = new WritableCellFormat(wfont);
            write.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);// 黑色边框
            write.setWrap(true);// 是自动换行
            //单元格文本显示方式
            write.setAlignment(jxl.format.Alignment.CENTRE);// 水平居中
        } catch (Exception e) {
            e.printStackTrace();
        }
        return write;
    }

    private static WritableCellFormat getStyleColor(int font) {
        WritableCellFormat write = null;
        try {
            WritableFont wfont = null;
            wfont = new WritableFont(WritableFont.ARIAL, font,
                    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            write = new WritableCellFormat(wfont);
            write.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);// 黑色边框
            write.setWrap(true);// 是自动换行
            write.setAlignment(jxl.format.Alignment.CENTRE);// 水平居中

        } catch (Exception e) {
            e.printStackTrace();
        }
        return write;
    }
}
