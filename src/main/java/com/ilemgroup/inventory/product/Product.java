package com.ilemgroup.inventory.product;

public class Product {
    private long product_id;
    private String name;
    private String description;
    private Float price;

    

    public Product() {
    }

    public Product(String name, String description, Float price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }


    public Product(long product_id, String name, String description, Float price) {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
    public long getProduct_id() {
        return product_id;
    }
    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product [product_id=" + product_id + ", name=" + name + ", description=" + description + ", price="
                + price + "]";
    }




    
}
