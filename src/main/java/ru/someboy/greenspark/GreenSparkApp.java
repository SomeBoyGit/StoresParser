package ru.someboy.greenspark;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.someboy.AbstractApp;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
public class GreenSparkApp extends AbstractApp {
    private static final String GREEN_SPARK = "https://green-spark.ru";

    public List<GreenSparkProduct> getData(List<String> allLinks) {
        List<GreenSparkProduct> data = new LinkedList<>();
        allLinks.forEach(link -> {
//        String link = allLinks.get(0);
            System.out.println("Парсим страницу: " + link);
            Document document;
        try {
            document = Jsoup.connect(link).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements products = document.getElementsByClass("card catalog-card");
        products.forEach(product -> {
            GreenSparkProduct gsProduct = new GreenSparkProduct();
            gsProduct.setName(product.getElementsByClass("card-title").select("a").text());
                switch (product.getElementsByClass("icon-batery").select("use").attr("xlink:href").split("#")[1].trim()) {
                    case "batery-full": gsProduct.setInStock("очень много");
                        break;
                    case "batery-not-full": gsProduct.setInStock("много");
                        break;
                    case "batery-orange": gsProduct.setInStock("мало");
                        break;
                    case "batery-low": gsProduct.setInStock("очень мало");
                        break;
                    case "batery-null": gsProduct.setInStock("нет в наличии");
                        break;
                }
                String price = product.getElementsByClass("price-name").next().text();
                gsProduct.setRetailPrice(Integer.parseInt(price.length() == 0 ? "0" : price.split(" ")[0]));
                gsProduct.setWholesalePrice(Integer.parseInt(price.length() == 0 ? "0" : price.split(" ")[1]));
                gsProduct.setCount("Нет данных");
                gsProduct.setProductLink(GREEN_SPARK + product.select("a").attr("href"));
                gsProduct.setPhotoLink(GREEN_SPARK + product.select("img").attr("src"));
                data.add(gsProduct);
            });
        });
        return data;
    }

    public List<String> getAllLinks(String url) {
        List<String> allLinks = new LinkedList<>();
        Document homePage;
        try {
            homePage = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(homePage.getElementsByClass("city_name").text());
        Elements elements = homePage.getElementsByClass("d-flex flex-row flex-wrap catalog-category").select("a");
        elements.forEach(element -> {
            Document pagination;
            try {
                pagination = Jsoup.connect(GREEN_SPARK + element.attr("href") + "?per_page=100").get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int countPage = Integer.parseInt(pagination.getElementsByClass("page-item").text()
                    .split(" ")[pagination.getElementsByClass("page-item").text().split(" ").length - 1]);
            for (int i = 1; i < countPage + 1; i++) {
                String link = GREEN_SPARK + element.attr("href") + url.split("/")[url.split("/").length-1] + "/" + "?per_page=100&PAGEN_1=" + i;
                allLinks.add(link);
                System.out.println("Ссылка: " + link);
            }
        });
        return allLinks;
    }
}
