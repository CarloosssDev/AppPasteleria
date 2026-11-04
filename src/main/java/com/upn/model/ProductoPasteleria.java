/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upn.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author user
 */
@Entity
public class ProductoPasteleria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int stock;
    private String nombre;
    private double PrecioBase;

    public ProductoPasteleria() {
    }

    public ProductoPasteleria(Long id, int stock, String nombre, double PrecioBase) {
        this.id = id;
        this.stock = stock;
        this.nombre = nombre;
        this.PrecioBase = PrecioBase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioBase() {
        return PrecioBase;
    }

    public void setPrecioBase(double PrecioBase) {
        this.PrecioBase = PrecioBase;
    }

}
