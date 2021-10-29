package com.telebot;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainJsoup {
    private static Document javaDock;
    public static List <JavaNews> articlelist = new ArrayList<>();

    @SneakyThrows
    public static void main(String[] args) {


        try {
            javaDock= Jsoup.connect("https://www.nytimes.com/?utm_source=vsesmi_online").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements elemTitle = javaDock.getElementsByClass("css-sv6851");

        elemTitle.forEach(titleListElement-> {
            String title = titleListElement.text();
            String href = titleListElement.attr("href");
            articlelist.add(new JavaNews(title,href));
        });
        articlelist.forEach(System.out::println);
        writeIntoExcel("D://roo" +
                "t.xls");
    }

    public static void writeIntoExcel(String file) throws FileNotFoundException, IOException{
        Workbook data = new HSSFWorkbook();
        Sheet sheet = data.createSheet("NewYorkTimes Data");

        // Нумерация начинается с нуля
        Row row = sheet.createRow(0);
        // счетчик для строк
        int rowNum = 0;
        // Мы запишем ссылки и заголовки в два столбца
        Cell url = row.createCell(0);
        url.setCellValue("Links");

        Cell title = row.createCell(1);
        title.setCellValue("title");

        // Меняем размер столбца
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        for (JavaNews dataModel : articlelist) {
            createSheetHeader((HSSFSheet) sheet,++rowNum, dataModel);
        }
        // Записываем всё в файл
        data.write(new FileOutputStream(file));
        data.close();
    }

    private static void createSheetHeader(HSSFSheet sheet, int rowNum, JavaNews dataModel) {
        Row row = sheet.createRow(rowNum);

        row.createCell(0).setCellValue(dataModel.getHref());
        row.createCell(1).setCellValue(dataModel.getTitle());
    }
}

