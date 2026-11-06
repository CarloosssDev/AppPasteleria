package com.upn.service;

import com.upn.model.ProductoPasteleria;
import com.upn.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private IProductoRepository productoRepository;

    public List<ProductoPasteleria> listarTodos() {
        return productoRepository.findAll();
    }

    public List<ProductoPasteleria> listarPorCategoria(Long idCategoria) {
        return productoRepository.findByCategoriaId(idCategoria);
    }

    public List<ProductoPasteleria> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}