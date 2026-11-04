package com.upn.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Cliente")
public class Cliente extends Persona {

    @Size(max = 150, message = "La dirección no puede exceder los 150 caracteres")
    @Column(length = 150)
    private String direccion;

    @Email(message = "Debe proporcionar un correo electrónico válido")
    @Size(max = 100, message = "El correo no puede exceder los 100 caracteres")
    @Column(length = 100)
    private String email;

    // Relación Uno a Muchos: Un cliente puede tener muchas ventas
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Venta> ventas = new ArrayList<>();

    public Cliente() {
        super();
    }

    public Cliente(Long id, String dni, String nombre, String apellido, String telefono, String direccion, String email) {
        super(id, dni, nombre, apellido, telefono);
        this.direccion = direccion;
        this.email = email;
    }

    // Método helper para asociar ventas
    public void agregarVenta(Venta venta) {
        ventas.add(venta);
        venta.setCliente(this);
    }

    // Getters y Setters
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }
}