package com.zacharye.book.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static Map<String, Object> getDataFromExcel (String filename,InputStream inputStream) {
        Workbook workbook = null;
        StringBuilder excelStr = new StringBuilder();
        try {
            if (filename.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (filename.endsWith(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            }
            Sheet sheet = workbook.getSheetAt(0);
            Row rowHead = sheet.getRow(0);
            int totalRowNum = sheet.getLastRowNum();
            Map<String,Object> result = new HashMap<>();

            int maxCellNUm = rowHead.getLastCellNum();
            boolean ifStartsWithNum  = false;
            for (int i = 1; i <= totalRowNum; i++) {
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(0);
                String val = getCellValue(cell);
                ifStartsWithNum = startsWithNumeric(val);
                if (ifStartsWithNum) {
                    if (!"".equals(excelStr.toString())) {
                        excelStr.append("@").append(val).append("|");
                    } else {
                        excelStr.append(val).append("|");
                    }
                    continue;
                }
                for (int j = 0; j < maxCellNUm; j++) {
                    Cell mCell = row.getCell(j);
                    String mStr = getCellValue(mCell);
                    excelStr.append(mStr + ";");
                }
                excelStr.append("|");
            }
        } catch (Exception e) {
            e.printStackTrace();
           //logger.error("FILEUTILS Excel - " + ex.getMessage(), ex);
        }
        return null;
    }

    public static String getCellValue (Cell  cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellTypeEnum()) {
            default:
                return (cell + "").trim();
        }

    }

    public static boolean startsWithNumeric (String str) {
        if ("".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\d+");
        String cha = str.substring(0,1);
        Matcher m = pattern.matcher(cha);
        return m.matches();
    }


    public static ByteArrayOutputStream saveaIns (InputStream ins) {
       ByteArrayOutputStream outputStream = null;
       try {
            outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = ins.read(buffer)) > -1) {
                outputStream.write(buffer, 0, len);
            }
       } catch (IOException e) {
           e.printStackTrace();
       }
       return outputStream;
    }
}
