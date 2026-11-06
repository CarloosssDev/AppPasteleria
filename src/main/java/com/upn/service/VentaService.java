package com.upn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upn.model.Cliente;
import com.upn.model.DetalleVenta;
import com.upn.model.Empleado;
import com.upn.model.ProductoPasteleria;
import com.upn.model.Venta;
import com.upn.repository.IProductoRepository;
import com.upn.repository.IVentaRepository;

import jakarta.transaction.Transactional;

@Service
public class VentaService {
    @Autowired
    private IVentaRepository repository;

    @Autowired
    private IProductoRepository productoRepository;

    @Transactional 
    public Venta procesarVenta(Cliente cliente, Empleado empleado, List<DetalleVenta> detalles, Double total, Double adelanto,
            Double saldo) {
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setEmpleado(empleado);
        venta.setTotal(total);
        venta.setAdelanto(adelanto);
        venta.setSaldo(saldo);

        for (DetalleVenta detalle : detalles) {
            ProductoPasteleria producto = productoRepository.findById(detalle.getProducto().getId()).orElseThrow(
                    () -> new RuntimeException("Producto no encontrado ID:" + detalle.getProducto().getId()));
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);
            venta.agregarDetalle(detalle);
        }

        return repository.save(venta);
    }

}
