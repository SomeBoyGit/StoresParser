package ru.someboy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.someboy.greenspark.GreenSparkMsk;
import ru.someboy.moba.MobaMsk;

@Component
public class Parser {
    private ParserInterface green;
    private ParserInterface moba;
//    private MobaMsk mobaMsk;
//    private GreenSparkMsk greenSparkMsk;

    @Autowired
    public Parser(@Qualifier("green") ParserInterface green, @Qualifier("moba") ParserInterface moba) {
        this.green = green;
        this.moba = moba;
    }

//    public Parser(MobaMsk mobaMsk, GreenSparkMsk greenSparkMsk) {
//        this.mobaMsk = mobaMsk;
//        this.greenSparkMsk = greenSparkMsk;
//    }

    public void parseSite() {
        System.out.println("Parsing: ...");
        green.parseSite();
        System.out.println("Parsing: ...");
        moba.parseSite();
    }
}
