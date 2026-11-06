/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upn.service;

import com.upn.model.Cliente;
import com.upn.repository.IClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author user
 */
@Service
public class ClienteService {

    @Autowired
    private IClienteRepository repositroy;

    public Cliente buscarPorDni(String dni) {
        return repositroy.findByDni(dni).orElse(null);
    }

}
