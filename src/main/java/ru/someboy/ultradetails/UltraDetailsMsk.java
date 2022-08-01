package ru.someboy.ultradetails;

public class UltraDetailsMsk extends UltraDetailsApp{
    private static final String ULTRA = "https://ultra-details.ru";

    public static void main(String[] args) {
        UltraDetailsApp ultraDetailsApp = new UltraDetailsApp();
        ultraDetailsApp.getData(ultraDetailsApp.getAllLinks(ULTRA)).forEach(System.out::println);
    }
}
