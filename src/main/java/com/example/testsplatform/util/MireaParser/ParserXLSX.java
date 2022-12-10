package com.example.testsplatform.util.MireaParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minidev.json.JSONObject;
import netscape.javascript.JSObject;
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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ParserXLSX {

    public static Map<String, Map<Integer, ArrayList<ArrayList<Map<String, String>>>>> groups = new HashMap<>();

    public static void main(String[] args) throws IOException {
        huy();
    }
    public static void huy() throws IOException {

        try {
//            FileInputStream fis = new FileInputStream("src/main/java/com/example/testsplatform/util/XLSXFile/1.xlsx");

            for (int i = 1; i < 5; i++) {
                File file = new File("src/main/java/com/example/testsplatform/util/XLSXFile/" + i + ".xlsx");
                OPCPackage pkg = OPCPackage.open(file);
                XSSFWorkbook book = new XSSFWorkbook(pkg);
                XSSFSheet sheet = book.getSheetAt(0);
                readCells(sheet);
            }

            JSONObject object = new JSONObject(groups);
            try (Writer writer = new FileWriter("Output.json")) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(object, writer);
            }

        } catch (EncryptedDocumentException | IOException e) {
             e.printStackTrace();
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }

    }

    static ArrayList<ArrayList<Map<String, String>>> constructWeek(){
        ArrayList<ArrayList<Map<String, String>>> elem = new ArrayList<>(6);
        for (int i = 0; i < 6; i++)
            elem.add(new ArrayList<>(7));
        return elem;
    }
    private static void readCells(XSSFSheet sheet) throws IOException {


        short maxCol = sheet.getRow(0).getLastCellNum();

        for(int col = 5; col < maxCol ; col = col + 5) {

            Map<Integer, ArrayList<ArrayList<Map<String, String>>>> week = new HashMap<>();
            week.put(1, constructWeek());
            week.put(2, constructWeek());

            // Встретился день недели
            if (col % 15 == 0)
                continue;
            System.out.println();
            String groupName = printCell(sheet.getRow(1).getCell(col));

            for (int rw = 3; rw < 85; rw ++) {
                XSSFCell cell = sheet.getRow(rw).getCell(col);
                XSSFCell cell2 = sheet.getRow(rw).getCell(col + 1);
                XSSFCell cell3 = sheet.getRow(rw).getCell(col + 2);
                XSSFCell cell4 = sheet.getRow(rw).getCell(col + 3);

                Map<String, String> subject = new HashMap<>();
                subject.put("title", printCell(cell).replaceAll("\n", ""));
                subject.put("type", printCell(cell2).replaceAll("\n", ""));
                subject.put("teacher", printCell(cell3).replaceAll("\n", ""));
                subject.put("audit", printCell(cell4).replaceAll("\n", ""));
                week.get(rw % 2 + 1).get(((rw - 3) / 14) % 6).add(subject);
            }
            groups.put(groupName, week);
        }
    }
    private static String printCell(XSSFCell cell){

        // Вывод значения в консоль
        switch (cell.getCellType()) {
            case STRING:
                return cell.getRichStringCellValue()
                        .getString();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell))
                    return cell.getDateCellValue().toString();
                else
                    return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return String.valueOf(cell.getCellFormula());
            default:
                return "";
        }
    }



}
