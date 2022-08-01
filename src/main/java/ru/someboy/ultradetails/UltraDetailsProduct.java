package ru.someboy.ultradetails;

import java.util.List;

public class UltraDetailsProduct {
    private String name;
    private List<String> inStock;
    private int price;
    private List<String> count;
    private String productLink;
    private String photoLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getInStock() {
        return inStock;
    }

    public void setInStock(List<String> inStock) {
        this.inStock = inStock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getCount() {
        return count;
    }

    public void setCount(List<String> count) {
        this.count = count;
    }

    public String getProductLink() {
        return productLink;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    @Override
    public String toString() {
        return "UltraDetailsProduct{" +
                "name='" + name + '\'' +
                ", inStock=" + inStock +
                ", price=" + price +
                ", count='" + count + '\'' +
                ", productLink='" + productLink + '\'' +
                ", photoLink='" + photoLink + '\'' +
                '}';
    }
}
