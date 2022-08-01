package ru.someboy.gizaservice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.someboy.AbstractApp;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class GizaServiceApp extends AbstractApp {
    public List<GizaServiceProduct> getData(List<String> allLinks) {
        List<GizaServiceProduct> dataList = new LinkedList<>();
        allLinks.forEach(link -> {
            Document document;
            try {
                document = Jsoup.connect(link).userAgent("Mozilla").get();
            } catch (IOException e) {
                System.out.println("Сбой соединения!");
                throw new RuntimeException(e);
            }
            Elements products = document.getElementsByClass("col-6 col-xl-4");
            products.forEach(product -> {
                GizaServiceProduct gizaProduct = new GizaServiceProduct();
                gizaProduct.setName(product.getElementsByClass("products__item-title").text().split(" \\(Арт.")[0].trim());
                gizaProduct.setInStock(product.getElementsByAttributeStarting("disabled").text().equals("Купить")
                        ? "Нет в наличии" : "В наличии");
                gizaProduct.setRetailPrice(Integer.parseInt(product.getElementsByClass("products__item-price").text().split(" ")[0]));
                gizaProduct.setWholesalePrice(0);
                gizaProduct.setCount("Нет данных");
                gizaProduct.setProductLink(product.getElementsByClass("products__item-images").select("a").attr("href"));
                gizaProduct.setPhotoLink(product.getElementsByClass("products__item-images").select("img").attr("data-src"));
                dataList.add(gizaProduct);
            });
        });

        return dataList;
    }

    public List<String> getAllLinks(String url) {
        List<String> allLinks = new LinkedList<>();
        Document homePage;
        try {
            homePage = Jsoup.connect(url)/*.userAgent("Mozilla")*/.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements elements = homePage
                .getElementsByClass("nav__link nav__link--arrow")
                .select("a");
        for (int i = 2; i < elements.size(); i++) {
            Document pagination;
            String firstPageCategory = elements.get(i).attr("href");
            try {
                pagination = Jsoup.connect(firstPageCategory + "?limit=100").get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Element linkPage = pagination.getElementsByClass("pagination").select("a").last();
            int countPage
                    = linkPage == null
                    ? 1 : Integer.parseInt(linkPage.attr("href").split("=")[linkPage.attr("href").split("=").length - 1]);
            for (int j = 1; j < countPage + 1; j++) {
                allLinks.add(firstPageCategory + "?limit=100&page=" + j);
            }
        }
        return allLinks;
    }
}
