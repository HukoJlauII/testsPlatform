package com.example.testsplatform.util.MireaParser;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class ParserXLSX {

    public static void main(String[] args) throws IOException {
        huy();
    }
    public static void huy() throws IOException {

        try {
//            FileInputStream fis = new FileInputStream("src/main/java/com/example/testsplatform/util/XLSXFile/1.xlsx");
            File file = new File("src/main/java/com/example/testsplatform/util/XLSXFile/1.xlsx");
            OPCPackage pkg = OPCPackage.open(file);
            XSSFWorkbook book = new XSSFWorkbook(pkg);
            XSSFSheet sheet = book.getSheet("Лист1");
            Iterator<Row> ri = sheet.rowIterator();
            readCells(sheet);

        } catch (EncryptedDocumentException | IOException e) {
             e.printStackTrace();
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }

    }
    private static void readCells(XSSFSheet sheet)
    {
        // Определение граничных строк обработки
        int rowStart = Math.min(  0, sheet.getFirstRowNum());
        int rowEnd   = Math.max(100, sheet.getLastRowNum ());

        for (int rw = rowStart; rw < rowEnd; rw++) {
            XSSFRow row = sheet.getRow(rw);
            if (row == null) {
                continue;
            }
            short minCol = row.getFirstCellNum();
            short maxCol = row.getLastCellNum();

            for(short col = minCol; col < 5; col++) {
                XSSFCell cell = row.getCell(col);
                if (cell == null) {
                    continue;
                }
                printCell(row, cell);
            }
        }
    }
    private static void printCell(XSSFRow row, XSSFCell cell)
    {
        DataFormatter formatter = new DataFormatter();
        CellReference cellRef = new CellReference(row.getRowNum(),
                cell.getColumnIndex());
        String text = formatter.formatCellValue(cell);

        if(!text.equals("")) {
            System.out.print(cellRef.formatAsString());
            System.out.print(" : ");
            System.out.print(text);
        }

        // Вывод значения в консоль
        switch (cell.getCellType()) {
            case STRING:
                System.out.println(cell.getRichStringCellValue()
                        .getString());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell))
                    System.out.println(cell.getDateCellValue());
                else
                    System.out.println(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                System.out.println(cell.getBooleanCellValue());
                break;
            case FORMULA:
                System.out.println(cell.getCellFormula());
                break;
            case BLANK:
                break;
        }
    }



}
