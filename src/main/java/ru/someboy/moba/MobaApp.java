package ru.someboy.moba;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.someboy.AbstractApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MobaApp/* extends AbstractApp */{
    private static final String MOBA = "https://moba.ru";

    public static void main(String[] args) {
        getData().forEach(System.out::println);
    }

//    public List<MobaProduct> getData(List<String> allLinks) {
    public static List<MobaProduct> getData() {
        List<MobaProduct> dataList = new ArrayList<>();
//        allLinks.forEach(link -> {
        String link = "https://moba.ru/catalog/akkumulyatory-1/?PAGEN_1=2";
            System.out.println("Парсим страницу: " + link);
            Document document;
            try {
                document = Jsoup.connect(link).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Elements products = document.getElementsByClass("item main_item_wrapper");

            products.forEach(product -> {
                MobaProduct mobaProduct = new MobaProduct();
                mobaProduct.setName(product.select("td.item-name-cell").select("div.title ").text());
                mobaProduct.setInStock(product.select("td.item-name-cell").select("div.item-stock").text());
                String priceString = product.select("div.price").attr("data-value").trim();
                try {
                    mobaProduct.setRetailPrice(Integer.parseInt(priceString));
                } catch (NumberFormatException e) {
                    mobaProduct.setRetailPrice(0);
                }
                mobaProduct.setWholesalePrice(0);
                mobaProduct.setCount(product.select("td.but-cell").select("span.plus").attr("data-max"));
                mobaProduct.setProductLink(MOBA + product.select("td.item-name-cell").select("div.title ").select("a").attr("href"));
                mobaProduct.setPhotoLink(MOBA + product.select("td.foto-cell").select("img.image_item").attr("src"));

//                System.out.println(mobaProduct);
                dataList.add(mobaProduct);
            });
//        });
        return dataList;
//        return null;
    }

    public List<String> getAllLinks(String url) {
        List<String> allLinks = new ArrayList<>();
        Document catalogPage;
        try {
            catalogPage = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements links = catalogPage.select("li.name");
        String shopName = catalogPage.getElementsByClass("header-v3 header-wrapper").select("div.shop_name").text();
        System.out.println("Подключение к {" + shopName + "}");
        links.forEach(element -> {
            String URL = MOBA + element.child(0).attr("href");
            System.out.println("Сканируем категорию: " + URL);
            Document pagination;
            try {
                pagination = Jsoup.connect(URL).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Element pageCount = pagination
                    .getElementsByClass("flex-direction-nav")
                    .nextAll()
                    .select("a")
                    .last();
            int num = pageCount == null ? 1 : Integer.parseInt(pageCount.text());
            System.out.println("Создаём ссылки страниц с 1 по " + num);
            for (int i = 1; i < num + 1; i++) {
                String link = URL + "?PAGEN_1=" + i;
                allLinks.add(link);
            }
        });
        return allLinks;
    }
}