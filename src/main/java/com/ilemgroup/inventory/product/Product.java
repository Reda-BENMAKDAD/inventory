package com.ilemgroup.inventory.product;

import java.sql.Timestamp;


import jakarta.validation.constraints.NotEmpty;

public class Product {

    private Long product_id;
    @NotEmpty
    private String name;
    private String description;
    @NotEmpty
    private float price;
    private float cost;
    private int quantity_in_stock;
    private int reorder_level;
    private boolean is_active;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Product() {
    }

    public Product(Long product_id, String name, String description, float price, float cost, int quantity_in_stock,
            int reorder_level, boolean is_active, Timestamp created_at, Timestamp updated_at) {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.cost = cost;
        this.quantity_in_stock = quantity_in_stock;
        this.reorder_level = reorder_level;
        this.is_active = is_active;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
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

    public float getPrice() {
        return price;
    } 

    public void setPrice(float price) {
        this.price = price;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getQuantity_in_stock() {
        return quantity_in_stock;
    }

    public void setQuantity_in_stock(int quantity_in_stock) {
        this.quantity_in_stock = quantity_in_stock;
    }

    public int getReorder_level() {
        return reorder_level;
    }

    public void setReorder_level(int reorder_level) {
        this.reorder_level = reorder_level;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Product [product_id=" + product_id + ", name=" + name + ", description=" + description + ", price="
                + price + ", cost=" + cost + ", quantity_in_stock=" + quantity_in_stock + ", reorder_level="
                + reorder_level + ", is_active=" + is_active + ", created_at=" + created_at + ", updated_at="
                + updated_at + "]";
    } 
}
