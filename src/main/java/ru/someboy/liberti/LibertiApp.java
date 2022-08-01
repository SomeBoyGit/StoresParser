package ru.someboy.liberti;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class LibertiApp {
    private static final String LIBERTI = "https://liberti.ru";

    private static Set<String> allLinks;


    public static void main(String[] args) throws IOException {
        getPaginationInCategories().forEach(System.out::println);
    }

//    private static String getDeepInto(String url) {
//        if()
//    }

    private static List<String> getPaginationInCategories() throws IOException {
        List<String> list = getCatalogLinks(LIBERTI);
        System.out.println(list);
        list.forEach(link -> {
            System.out.println("Сканируем категорию: " + link);
            Document document;
            try {
                document = Jsoup.connect(link).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int pagination;
            try {
                pagination = Integer.parseInt(document.getElementsByClass("lb-pagination__nav").select("a").last().text());
            } catch (Exception e) {
                pagination = 1;
            }
            System.out.println(pagination);

        });
        return null;
    }

    protected static List<String> getCatalogLinks(String url) throws IOException {
        List<String> catalogLinks = new LinkedList<>();
        Document catalogPage = Jsoup.connect(url).get();
        Elements links = catalogPage.getElementsByClass("catalog-wide-menu-item__link catalog-wide-menu-item__link--level-1");
        links.forEach(link -> {
            String URL = LIBERTI + link.attr("href");
//            System.out.println(URL);
            catalogLinks.add(URL);
        });
        return catalogLinks;
    }
}
