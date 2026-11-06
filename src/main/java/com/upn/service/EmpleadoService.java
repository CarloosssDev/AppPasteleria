package com.upn.service;

import com.upn.model.Empleado;
import com.upn.repository.IEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    @Autowired
    private IEmpleadoRepository repository;

    public Empleado buscarEmpleado(String dni) {
        return repository.findByDni(dni).orElse(null);
    }
}