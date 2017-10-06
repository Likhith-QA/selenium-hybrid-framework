package com.hybrid.framework.utility;

import com.hybrid.framework.config.Constants;
import com.hybrid.framework.executionEngine.Executor;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelUtils {
    private static XSSFSheet excelWSheet;
    private static XSSFWorkbook excelWBook;
    private static XSSFCell cell;
    private static XSSFRow row;

    public static void setExcelFile(String Path) {
        try {
            FileInputStream ExcelFile = new FileInputStream(Path);
            excelWBook = new XSSFWorkbook(ExcelFile);
        }
        catch (Exception e) {
            Executor.boolResult = false;
        }
    }

    public static String getCellData(int rowNum, int colNum, String sheetName) {
        try {
            excelWSheet = excelWBook.getSheet(sheetName);
            cell = excelWSheet.getRow(rowNum).getCell(colNum);
            return cell.getStringCellValue();
        }
        catch (Exception e) {
            Executor.boolResult = false;
            return "";
        }
    }

    public static int getRowCount(String sheetName) {
        int iNumber = 0;
        try {
            excelWSheet = excelWBook.getSheet(sheetName);
            iNumber = excelWSheet.getLastRowNum() + 1;
        }
        catch (Exception e) {
            Executor.boolResult = false;
        }
        return iNumber;
    }

    public static void deleteColumnContents(int colNum, String sheetName) {
        int iNumber = 0;
        try {
            excelWSheet = excelWBook.getSheet(sheetName);
            iNumber = excelWSheet.getLastRowNum() + 1;

            for (int i = 1; i < iNumber; i++) {
                cell = excelWSheet.getRow(i).getCell(colNum);
                cell.setCellValue("");
            }
        }
        catch (Exception e) {
            Executor.boolResult = false;
        }
    }

    public static int getRowContains(String sTestCaseName, int colNum,String sheetName) {
        int iRowNum=0;
        try {
            int rowCount = ExcelUtils.getRowCount(sheetName);
            for (; iRowNum<rowCount; iRowNum++) {
                if  (ExcelUtils.getCellData(iRowNum,colNum,sheetName).equalsIgnoreCase(sTestCaseName)) {
                    break;
                }
            }
        }
        catch (Exception e) {
            Executor.boolResult = false;
        }
        return iRowNum;
    }

    public static int getTestStepsCount(String sheetName, String sTestCaseID, int iTestCaseStart) {
        try {
            for (int i=iTestCaseStart;i<=ExcelUtils.getRowCount(sheetName);i++) {
                if(!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.COL_TESTCASEID, sheetName))) {
                    int number = i;
                    return number;
                }
            }
            excelWSheet = excelWBook.getSheet(sheetName);
            int number = excelWSheet.getLastRowNum()+1;
            return number;
        }
        catch (Exception e) {
            Executor.boolResult = false;
            return 0;
        }
    }

    public static void setCellData(String data,  int rowNum, int colNum, String sheetName) {
        try {
            excelWSheet = excelWBook.getSheet(sheetName);
            row = excelWSheet.getRow(rowNum);
            cell = row.getCell(colNum, row.RETURN_BLANK_AS_NULL);
            if (cell == null) {
                cell = row.createCell(colNum);
                cell.setCellValue(data);
            }
            else {
                cell.setCellValue(data);
            }
            FileOutputStream fileOut = new FileOutputStream(Constants.EXCEL_PATH);
            excelWBook.write(fileOut);
            fileOut.close();
            excelWBook = new XSSFWorkbook(new FileInputStream(Constants.EXCEL_PATH));
        }
        catch(Exception e) {
            Executor.boolResult = false;
        }
    }
}
