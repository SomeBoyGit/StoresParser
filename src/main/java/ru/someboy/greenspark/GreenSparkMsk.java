package ru.someboy.greenspark;

import org.springframework.stereotype.Component;
import ru.someboy.ParserInterface;

import java.util.Arrays;
import java.util.List;

@Component("green")
public class GreenSparkMsk implements ParserInterface {
    private static final List<String> GREEN_SPARK_MSK = Arrays.asList(
            "https://green-spark.ru/?set_city=16366"/*,
            "https://green-spark.ru/?set_city=16367",
            "https://green-spark.ru/?set_city=75242",
            "https://green-spark.ru/?set_city=78260"*/
    );

//    public static void main(String[] args) {
//        for(String shop: GREEN_SPARK_MSK) {
//            GreenSparkApp greenSparkApp = new GreenSparkApp();
//            greenSparkApp.getData(greenSparkApp.getAllLinks(shop)).forEach(System.out::println);
//        }
//    }

    @Override
    public void parseSite() {
        for(String shop: GREEN_SPARK_MSK) {
            GreenSparkApp greenSparkApp = new GreenSparkApp();
            greenSparkApp.getData(greenSparkApp.getAllLinks(shop)).forEach(System.out::println);
        }
    }
}
