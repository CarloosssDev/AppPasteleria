package com.upn.service;

import com.upn.model.Categoria;
import com.upn.repository.ICategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<Categoria> listarActivas() {
        return categoriaRepository.findByEstadoTrue();
    }

    @Transactional(readOnly = true)
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Categoria> filtrarCategorias(Boolean estado, String nombre) {
        String filtroNombre = (nombre == null) ? "" : nombre.trim();
        return categoriaRepository.filtrarCategorias(estado, filtroNombre);
    }

    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public void eliminarLogico(Long id) {
        Categoria cat = buscarPorId(id);
        if (cat != null) {
            cat.setEstado(false);
            categoriaRepository.save(cat);
        }
    }
}
