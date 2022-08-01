package ru.someboy.gizaservice;

import java.util.Arrays;
import java.util.List;

public class GizaServiceMsk extends GizaServiceApp{
    private static final String GIZA = "https://gizaservice.ru";

    public static void main(String[] args) {
        GizaServiceApp gizaServiceApp = new GizaServiceApp();
        gizaServiceApp.getData(gizaServiceApp.getAllLinks(GIZA)).forEach(System.out::println);
    }
}
