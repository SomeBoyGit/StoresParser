package ru.someboy.moba;

import org.springframework.stereotype.Component;
import ru.someboy.ParserInterface;

import java.util.Arrays;
import java.util.List;

@Component("moba")
public class MobaMsk implements ParserInterface {
    private static final List<String> MOBA_MSK = Arrays.asList(
            "https://moba.ru/catalog/?cid=9705"/*,
            "https://moba.ru/catalog/?cid=14544",
            "https://moba.ru/catalog/?cid=37620",
            "https://moba.ru/catalog/?cid=12352",
            "https://moba.ru/catalog/?cid=9703",
            "https://moba.ru/catalog/?cid=9704",
            "https://moba.ru/catalog/?cid=9706",
            "https://moba.ru/catalog/?cid=14562"*/
    );

//    public static void main(String[] args) {
//        for (String shop : MOBA_MSK) {
//            MobaApp mobaApp = new MobaApp();
//            mobaApp.getData(mobaApp.getAllLinks(shop)).forEach(System.out::println);
//        }
//    }

    @Override
    public void parseSite() {
        long startTime = System.nanoTime();
        for (String shop : MOBA_MSK) {
            MobaApp mobaApp = new MobaApp();
//            mobaApp.getData(mobaApp.getAllLinks(shop)).forEach(System.out::println);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\n\n\n\n" + duration);
    }
}
