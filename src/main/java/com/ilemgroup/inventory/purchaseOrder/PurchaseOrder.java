package com.ilemgroup.inventory.purchaseOrder;

import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import com.ilemgroup.inventory.supplier.Supplier;

@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_order_seq")
    @SequenceGenerator(name = "purchase_order_seq", sequenceName = "purchase_order_seq", allocationSize = 1)
    private Long order_id;
    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id")
    private Supplier supplier;
    @NotBlank
    private String order_date;
    @NotBlank
    private String status_id;
    @NotBlank
    private Double total_cost;
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp created_at;
    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updated_at;

    public PurchaseOrder() {
    }

    public PurchaseOrder(Long order_id, Supplier supplier, String order_date, String status_id, Double total_cost,
            Timestamp created_at, Timestamp updated_at) {
        this.order_id = order_id;
        this.supplier = supplier;
        this.order_date = order_date;
        this.status_id = status_id;
        this.total_cost = total_cost;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Supplier getSupplier_id() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public Double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(Double total_cost) {
        this.total_cost = total_cost;
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
        return "PurchaseOrder [order_id=" + order_id + ", supplier_id=" + supplier + ", order_date=" + order_date
                + ", status_id=" + status_id + ", total_cost=" + total_cost + ", created_at=" + created_at
                + ", updated_at=" + updated_at + "]";
    }
    
}
    
