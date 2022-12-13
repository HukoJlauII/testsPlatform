package com.example.testsplatform.util.MireaParser;

import org.jsoup.Jsoup;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(2)
public class DownloadXLSX implements CommandLineRunner {

    private static final String URL = "https://www.mirea.ru/schedule/";
    private static final String PATH = "src/main/java/com/example/testsplatform/util/XLSXFile/";


    public static List<String> parseXML() {
        List<String> StringURLXlsx = new ArrayList<>();
        try {
            var document = Jsoup.connect(URL).get();
            StringURLXlsx = document.select("a").stream().
                    filter(e -> e.attr("href").
                            startsWith("https://webservices")).filter(e -> e.attr("href").
                            contains("IIT")).map(e -> e.attr("href")).toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringURLXlsx;
    }


    public static List<java.net.URL> convertStringToURL() {
        return parseXML().stream().map(e -> {
            try {
                return new URL(e);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        }).toList();
    }


    public void downloadFile() throws Exception {
        List<URL> url = convertStringToURL();
        for (int i = 0; i < convertStringToURL().size() - 10; i++) {
            try (InputStream in = url.get(i).openStream()) {
                Files.copy(in, Paths.get(PATH + Integer.valueOf(i + 1) + ".xlsx"), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
//        downloadFile();
    }
}
