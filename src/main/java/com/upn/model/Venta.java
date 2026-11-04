package com.upn.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha de la venta es obligatoria")
    @Column(name = "fecha_venta", nullable = false, updatable = false)
    private LocalDateTime fechaVenta = LocalDateTime.now();

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.0", message = "El total no puede ser negativo")
    @Column(nullable = false)
    private Double total;

    @NotNull(message = "El adelanto es obligatorio")
    @DecimalMin(value = "0.0", message = "El adelanto no puede ser negativo")
    @Column(nullable = false)
    private Double adelanto;

    @Column(nullable = false)
    private Double saldo;

    // Relación con Cliente
    @NotNull(message = "Debe asignar un cliente a la venta")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Relación con Empleado (el vendedor que registra)
    @NotNull(message = "Debe asignar un empleado a la venta")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    // Relación Uno a Muchos con DetalleVenta
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetalleVenta> detalles = new ArrayList<>();

    public Venta() {
        this.fechaVenta = LocalDateTime.now();
        this.total = 0.0;
        this.adelanto = 0.0;
        this.saldo = 0.0;
    }

    public Venta(Long id, LocalDateTime fechaVenta, Double total, Double adelanto, Cliente cliente, Empleado empleado) {
        this.id = id;
        this.fechaVenta = fechaVenta != null ? fechaVenta : LocalDateTime.now();
        this.total = total;
        this.adelanto = adelanto;
        this.cliente = cliente;
        this.empleado = empleado;
        calcularSaldo();
    }

    // Método de ciclo de vida JPA para calcular automáticamente el saldo antes de guardar o actualizar
    @PrePersist
    @PreUpdate
    public void calcularSaldo() {
        if (this.total != null && this.adelanto != null) {
            this.saldo = this.total - this.adelanto;
        }
    }

    // Método helper para añadir ítems a la venta
    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
        detalle.setVenta(this);
        recalcularTotal();
    }

    public void removerDetalle(DetalleVenta detalle) {
        detalles.remove(detalle);
        detalle.setVenta(null);
        recalcularTotal();
    }

    public void recalcularTotal() {
        this.total = detalles.stream()
                .mapToDouble(DetalleVenta::getSubtotal)
                .sum();
        calcularSaldo();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
        calcularSaldo();
    }

    public Double getAdelanto() {
        return adelanto;
    }

    public void setAdelanto(Double adelanto) {
        this.adelanto = adelanto;
        calcularSaldo();
    }

    public Double getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
}
