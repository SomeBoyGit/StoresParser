package ru.someboy.ultradetails;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.someboy.AbstractApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UltraDetailsApp extends AbstractApp {
    private static final String ULTRA = "https://ultra-details.ru";

//    public static void main(String[] args) throws IOException {
//        getData(getAllLinks(ULTRA)).forEach(System.out::println);
////        String link = "https://ultra-details.ru/akkumulatory/?page=1";
////        getData(link).forEach(test -> System.out.println("__________________________________________________" + "\n" + test));
//    }

    public List<UltraDetailsProduct> getData(List<String> links) {
        List<UltraDetailsProduct> dataList = new LinkedList<>();
        links.forEach(link -> {
            System.out.println("Сканируем очередную страницу");
            Document document;
            try {
                document = Jsoup.connect(link).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements products = document.getElementsByClass("product-layout");
            products.forEach(product -> {
                System.out.println("Сканируем очередной элемент");
                UltraDetailsProduct ultraProduct = new UltraDetailsProduct();
                ultraProduct.setName(product.select("div.product-name").select("a").text());
                Document documentInStock;
                try {
                    documentInStock = Jsoup.connect(product.select("ul").next().select("script").get(0).data().split("url:")[1].split(",")[0].split("'")[1]).get();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                List<String> shopList = new ArrayList<>();
                List<String> countList = new ArrayList<>();
                documentInStock.select("body").forEach(p -> {
                    String[] shop = p.select("b").text().split(" ");
                    String[] count = p.select("span").text().split(" ");
                    shopList.add(Arrays.toString(shop));
                    countList.add(Arrays.toString(count));
                });
                ultraProduct.setInStock(shopList);
                ultraProduct.setPrice(Integer.parseInt(product.select("div.price").text().split(" ")[0]));
                ultraProduct.setCount(countList);
                ultraProduct.setProductLink(product.select("div.product-name").select("a").attr("href"));
                ultraProduct.setPhotoLink(product.select("div.stickers-ns").next().select("img").attr("data-src"));
                dataList.add(ultraProduct);
            });
        });
        return dataList;
    }

    public List<String> getAllLinks(String url) {
        List<String> allLinks = new LinkedList<>();
        Document catalogPages;
        try {
            catalogPages = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements catalogLinks = catalogPages.getElementsByClass("col-xs-12 col-sm-6 col-md-3").get(5).select("a");
        catalogLinks.forEach(catalogLink -> {
            String urlCatalog = url + catalogLink.attr("href");
            System.out.println("Сканируем категорию: " + urlCatalog);
            Document pagination;
            try {
                pagination = Jsoup.connect(urlCatalog).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Element pageCount = pagination
                    .getElementsByClass("pagination")
                    .select("a")
                    .last();
            int numberLastPage = pageCount == null ? 1 : Integer.parseInt(pageCount.attr("href").split("=")[1]);
            System.out.println("Создаём ссылки страниц с 1 по " + numberLastPage);
            for (int i = 1; i < numberLastPage + 1; i++) {
                String pageLink = url + "?page=" + i;
                allLinks.add(pageLink);
            }
        });
        return allLinks;
    }
}
