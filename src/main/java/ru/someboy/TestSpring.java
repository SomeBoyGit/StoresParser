package ru.someboy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.someboy.greenspark.GreenSparkMsk;
import ru.someboy.moba.MobaApp;

public class TestSpring {
    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//                "applicationContext.xml"
//        );
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        Parser parser = context.getBean("parser", Parser.class);
        long startTime = System.nanoTime();
        parser.parseSite();
        long endTime = System.nanoTime();
        long duration = ((endTime - startTime)/1_000_000_000);
        System.out.println(duration);
        System.out.println(duration/60);


        context.close();
    }
}
