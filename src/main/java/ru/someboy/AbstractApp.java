package ru.someboy;

import java.util.List;

public abstract class AbstractApp {
    public abstract List<String> getAllLinks(String url);

    public abstract List<?> getData(List<String> allLinks);
}
