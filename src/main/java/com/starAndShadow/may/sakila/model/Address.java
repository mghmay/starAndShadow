package com.starAndShadow.may.sakila.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@NoArgsConstructor
@Entity
@Getter
@Table(name="address")
public class Address {
    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;
    @Column(name = "address")
    private String address;
    @Column(name = "address2")
    private String address2;
    @Column(name = "district")
    private String district;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "phone")
    private String phone;
    @Column(name = "location")
    private String location;
    @Column(name = "last_update")
    private String lastUpdate;
}