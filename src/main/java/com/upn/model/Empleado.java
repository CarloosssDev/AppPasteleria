package com.upn.model;

import com.upn.enums.Rol;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Empleados")
public class Empleado extends Persona {

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, max = 100, message = "La contraseña debe tener al menos 4 caracteres")
    @Column(nullable = false, length = 100)
    private String password;

    @NotNull(message = "El rol del empleado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rol rol;

    // Relación Uno a Muchos: Un empleado puede registrar muchas ventas
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Venta> ventas = new ArrayList<>();

    public Empleado() {
        super();
    }

    public Empleado(Long id, String dni, String nombre, String apellido, String telefono, String password, Rol rol) {
        super(id, dni, nombre, apellido, telefono);
        this.password = password;
        this.rol = rol;
    }

    // Helper para asociar ventas
    public void agregarVenta(Venta venta) {
        ventas.add(venta);
        venta.setEmpleado(this);
    }

    // Getters y Setters
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public List<Venta> getVentas() { return ventas; }
    public void setVentas(List<Venta> ventas) { this.ventas = ventas; }
}