/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upn.service;

import com.upn.model.Categoria;
import com.upn.repository.ICategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author user
 */
@Service
public class CategoriaService {

    @Autowired
    private ICategoriaRepository repository;
    
    public List<Categoria>  listarTodas() {
        return repository.findAll();
    }
    

}
