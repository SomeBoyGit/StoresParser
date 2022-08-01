package ru.someboy.mastermobile;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MasterMobileApp {
    private static final String MASTER = "https://master-mobile.ru";
    private static final String MASTER_CATALOG = "https://master-mobile.ru/catalog";
    private static final String MASTER_CATALOG123 = "https://master-mobile.ru/catalog/zapchasti_dlya_telefonov_1/?PAGEN_1=2";

    public static void main(String[] args) {
//        getAllDataLinks().forEach(System.out::println);
//        getPagesLinks(MASTER_CATALOG).forEach(System.out::println);
        getData(MASTER_CATALOG123).forEach(System.out::println);
    }

    private static List<String> getData(String url) {
        List<String> data = new LinkedList<>();
        getPagesLinks(url).forEach(pagesLink -> {
            Document pagesLinkConnect;
            try {
                pagesLinkConnect = Jsoup.connect(pagesLink).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Elements elements = pagesLinkConnect.getElementsByClass("item-title");
            System.out.println(elements);
        });
        return null;
    }
    private static List<String> getPagesLinks(String url) {
        List<String> allPagesLinks = new LinkedList<>();
        Document catalogPage;
        try {
            catalogPage = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements links = catalogPage.getElementsByClass("icons_fa parent");
        String endingLinks = "?PAGEN_1=";
        links.forEach(link ->  {
            String urlCatalog = MASTER + link.attr("href");
            Document categoryLink;
            try {
                categoryLink = Jsoup.connect(urlCatalog).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements elements = categoryLink.getElementsByClass("nums").select("a");
            int num = elements.size() == 0 ? 1 : Integer.parseInt(elements.get(elements.size() - 2).text());
            System.out.println("Создаём ссылки страниц с 1 по " + num);
            for (int i = 1; i < num + 1; i++) {
                String linkPage = urlCatalog + endingLinks + i;
                allPagesLinks.add(linkPage);
                System.out.println(linkPage);
            }
        });
        return allPagesLinks;
    }
}
