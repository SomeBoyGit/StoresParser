package ru.someboy;

import java.util.List;

public abstract class AbstractProduct {
    private String name;
    private String inStock;
    private List<String> inStockList;
    private int retailPrice;
    private int wholesalePrice;
    private String count;
    private String productLink;
    private String photoLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }

    public List<String> getInStockList() {
        return inStockList;
    }

    public void setInStockList(List<String> inStockList) {
        this.inStockList = inStockList;
    }

    public int getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(int retailPrice) {
        this.retailPrice = retailPrice;
    }

    public int getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(int wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
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
        return "AbstractProduct{" +
                "name='" + name + '\'' +
                ", inStock='" + inStock + '\'' +
                ", retailPrice=" + retailPrice +
                ", wholesalePrice=" + wholesalePrice +
                ", count='" + count + '\'' +
                ", productLink='" + productLink + '\'' +
                ", photoLink='" + photoLink + '\'' +
                '}';
    }
}
