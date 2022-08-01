package ru.someboy.partdirect;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ru.someboy.AbstractApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartsDirectApp extends AbstractApp {
    private static final String PARTS_DIRECT = "https://www.partsdirect.ru";
    private static final String PARTS_DIRECT_CATALOG = "https://www.partsdirect.ru/goods";
    private static int countNum = 0;

    public static void main(String[] args) {
        PartsDirectApp partsDirectApp = new PartsDirectApp();
//        partsDirectApp.getAllLinks(PARTS_DIRECT_CATALOG).forEach(System.out::println);
        partsDirectApp.getData(partsDirectApp.getAllLinks(PARTS_DIRECT_CATALOG)).forEach(System.out::println);
    }

    @Override
    public List<String> getAllLinks(String url) {
        List<String> catalogLinks = new ArrayList<>();
        Document document;
        try {
            document = Jsoup.connect(url).userAgent("Mozilla").get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements elements = document.getElementsByClass("all-h1").next();
        elements.forEach(element -> {
            element.children().forEach(e -> {
                System.out.println(PARTS_DIRECT + e.children().select("a").get(0).attr("href") + "?p=all");
                catalogLinks.add(PARTS_DIRECT + e.children().select("a").get(0).attr("href") + "?p=all");
            });
        });
        return catalogLinks;
    }

    @Override
    public List<PartsDirectProduct> getData(List<String> allLinks) {
        List<PartsDirectProduct> data = new ArrayList<>();
        allLinks.forEach(link -> {
//        String link = "https://www.partsdirect.ru/notebooks?p=35";
            Document products;
            try {
                products = Jsoup.connect(link).userAgent("Mozilla").get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements productList = products.getElementsByTag("tr");
            productList.forEach(product -> {
//                System.out.println("________________");
                System.out.println(countNum++);
                PartsDirectProduct partsDirectProduct = new PartsDirectProduct();
//                System.out.println(product.getElementsByTag("td").get(1).select("a").get(0).text());
                partsDirectProduct.setName(product.getElementsByTag("td").get(1).select("a").get(0).text());
//                System.out.println("span" + "\n" + product.getElementsByTag("td"));
//                System.out.println(product.getElementsByTag("td").get(2).select("span").size() == 0
//                        ? "Нет в наличии" : "В наличии");
                partsDirectProduct.setInStock(product.getElementsByTag("td").get(2).select("span").size() == 0
                        ? "Нет в наличии" : "В наличии");
                Elements price = product.getElementsByTag("td").select("span");
//                System.out.println(price);
                if (price.get(1).text().equals("ПДбонус+")) {
                    switch (price.get(2).text().split(" ").length) {
                        case 3:
                            partsDirectProduct.setRetailPrice(Integer.parseInt(price.get(2).text().split("&")[0].split(" ")[0] + price.get(2).text().split("&")[0].split(" ")[1]));
                            break;
                        case 2:
                            partsDirectProduct.setRetailPrice(Integer.parseInt(price.get(2).text().split("&")[0].split(" ")[0]));
                            break;
                    }
                } else if (price.get(0).text().equals("еще")) {
                    switch (price.get(4).text().split(" ").length) {
                        case 3:
                            partsDirectProduct.setRetailPrice(Integer.parseInt(price.get(4).text().split("&")[0].split(" ")[0] + price.get(4).text().split("&")[0].split(" ")[1]));
                            break;
                        case 2:
                            partsDirectProduct.setRetailPrice(Integer.parseInt(price.get(4).text().split("&")[0].split(" ")[0]));
                            break;
                    }
                } else {
                    switch (price.get(1).text().split(" ").length) {
                        case 3:
                            partsDirectProduct.setRetailPrice(Integer.parseInt(price.get(1).text().split("&")[0].split(" ")[0] + price.get(1).text().split("&")[0].split(" ")[1]));
                            break;
                        case 2:
                            partsDirectProduct.setRetailPrice(Integer.parseInt(price.get(1).text().split("&")[0].split(" ")[0]));
                            break;
                    }
                }
                if (price.get(1).text().equals("ПДбонус+")) {
                    switch (price.get(3).text().split("р")[0].split(" ").length) {
                        case 2:
                            partsDirectProduct.setWholesalePrice(Integer.parseInt(price.get(3).text().split("р")[0].split(" ")[0] + price.get(3).text().split("р")[0].split(" ")[1]));
                            break;
                        case 1:
                            partsDirectProduct.setWholesalePrice(Integer.parseInt(price.get(3).text().split("р")[0].split(" ")[0]));
                            break;
                    }
                } else if (price.get(0).text().equals("еще")) {
                    switch (price.get(5).text().split(" ").length) {
                        case 3:
                            partsDirectProduct.setRetailPrice(Integer.parseInt(price.get(5).text().split("&")[0].split(" ")[0] + price.get(5).text().split("&")[0].split(" ")[1]));
                            break;
                        case 2:
                            partsDirectProduct.setRetailPrice(Integer.parseInt(price.get(5).text().split("&")[0].split(" ")[0]));
                            break;
                    }
                } else {
                    switch (price.get(2).text().split("р")[0].split(" ").length) {
                        case 2:
                            partsDirectProduct.setWholesalePrice(Integer.parseInt(price.get(2).text().split("р")[0].split(" ")[0] + price.get(2).text().split("р")[0].split(" ")[1]));
                            break;
                        case 1:
                            partsDirectProduct.setWholesalePrice(Integer.parseInt(price.get(2).text().split("р")[0].split(" ")[0]));
                            break;
                    }
                }
                String count = product.getElementsByTag("td").get(2).select("span.strong").text();
                switch (count.split(" ").length) {
                    case 3:
                        partsDirectProduct.setCount(count.split(" ")[1]);
                        break;
                    case 2:
                        partsDirectProduct.setCount(count.split(" ")[0]);
                        break;
                    case 1:
                        partsDirectProduct.setCount("0");
                }
                partsDirectProduct.setProductLink(PARTS_DIRECT + product.getElementsByTag("td").select("a").attr("href"));
                String photoLink = product.getElementsByTag("td").select("a").attr("href").split("/")[2];
                partsDirectProduct.setPhotoLink("https://v2.partsdirect.ru/goods/good_small_pics2/" + photoLink + "s.jpg");
                data.add(partsDirectProduct);
//                partsDirectProduct = null;
//                System.out.println(partsDirectProduct);
            });
        });
        return data;
    }
}
