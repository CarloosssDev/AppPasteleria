package com.upn.service;

import com.upn.model.ProductoPasteleria;
import com.upn.repository.IProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private IProductoRepository productoRepository;

    @Transactional
    public List<ProductoPasteleria> listarTodos() {
        return productoRepository.findAllWithCategoria();
    }

    @Transactional
    public List<ProductoPasteleria> listarActivos() {
        return productoRepository.findAllActivosWithCategoria();
    }

    @Transactional
    public List<ProductoPasteleria> listarInactivos() {
        return productoRepository.findAllInactivosWithCategoria();
    }

    public List<ProductoPasteleria> listarPorCategoria(Long idCategoria) {
        return productoRepository.findByCategoriaIdWithCategoria(idCategoria);
    }

    public List<ProductoPasteleria> filtrarProductosCombinados(Long idCategoria, Boolean estado, String nombre) {
        return productoRepository.filtrarProductosCombinados(idCategoria, estado, nombre != null ? nombre.trim() : "");
    }

    public List<ProductoPasteleria> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCaseWithCategoria(nombre);
    }

    public ProductoPasteleria buscarPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public ProductoPasteleria guardar(ProductoPasteleria producto) {
        return productoRepository.save(producto);
    }

    public void eliminarPorId(Long idProducto) {
        productoRepository.deleteById(idProducto);
    }

}
