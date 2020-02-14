package com.seagetech.web.commons.util;

import com.seagetech.common.util.SeageUtils;
import com.seagetech.web.commons.view.exception.ExcelFileNotFindException;
import com.seagetech.web.commons.view.exception.ExcelFileTypeException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author wangzb
 * @date 2020/1/20 11:00
 * @company 矽甲（上海）信息科技有限公司
 */
public class Excel {
    private static final String[] EXCEL_FILE_TYPE = new String[]{".xls",".xlsx"};

    private static final String XLS = ".xls";

    private static final String XLSX = ".xlsx";

    /**
     * 文件验证
     */
    public static void verifyThrow(MultipartFile multipartFile){
        //文件判空
        if (multipartFile == null){
            throw new ExcelFileNotFindException();
        }
        String filename = multipartFile.getOriginalFilename();
        //文件格式验证
        for (String type : EXCEL_FILE_TYPE){
            if (filename.endsWith(type)){
                return;
            }
        }
        throw new ExcelFileTypeException(filename);
    }

    /**
     * 获取{@link org.apache.poi.ss.usermodel.Workbook}
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbook(MultipartFile multipartFile) throws IOException {
        String filename = multipartFile.getOriginalFilename();
        if (!SeageUtils.isEmpty(filename) && filename.endsWith(XLSX)) {
            return new XSSFWorkbook(multipartFile.getInputStream());
        } else if (!SeageUtils.isEmpty(filename) && filename.endsWith(XLS)) {
            return new HSSFWorkbook(multipartFile.getInputStream());
        }
        return null;
    }

    /**
     * 下载模板
     * @param stream
     * @return
     */
    public static ResponseEntity<AbstractResource> export(InputStream stream) {
        if (stream == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".xlsx");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(stream));
    }
}
