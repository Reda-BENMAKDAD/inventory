package com.ilemgroup.inventory.supplier;

import java.sql.Timestamp;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;

@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_seq")
    @SequenceGenerator(name = "supplier_seq", sequenceName = "supplier_seq", allocationSize = 1)
    private Long supplier_id;
    @NotEmpty
    private String name;
    @Email
    private String contact_email;
    private String phone;
    private String address;
    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp created_at;
    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updated_at;

//#region constructos
    public Supplier() {
    }

    public Supplier(Long supplier_id, @NotEmpty String name, @Email String contact_email, String phone, String address,
            Timestamp created_at, Timestamp updated_at) {
        this.supplier_id = supplier_id;
        this.name = name;
        this.contact_email = contact_email;
        this.phone = phone;
        this.address = address;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
//#endregion

//#region get&set
    public Long getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Long supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
//#endregion get&set

    @Override
    public String toString() {
        return "Supplier [supplier_id=" + supplier_id + ", name=" + name + ", contact_email=" + contact_email
                + ", phone=" + phone + ", address=" + address + ", created_at=" + created_at + ", updated_at="
                + updated_at + "]";
    }

}
