package com.upn.service;

import com.upn.enums.Rol;
import com.upn.model.Empleado;
import com.upn.repository.IEmpleadoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpleadoService {

    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Transactional(readOnly = true)
    public Empleado buscarEmpleado(String dni) {
        if (dni == null || dni.isBlank()) {
            return null;
        }
        return empleadoRepository.findByDniAndEstadoTrue(dni.trim()).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Empleado> filtrarEmpleados(Rol rol, Boolean estado, String texto) {
        String filtro = (texto == null) ? "" : texto.trim();
        return empleadoRepository.filtrarEmpleados(rol, estado, filtro);
    }

    @Transactional(readOnly = true)
    public Empleado buscarPorId(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Empleado guardar(Empleado empleado) {
        String dni = empleado.getDni() != null ? empleado.getDni().trim() : "";
        empleado.setDni(dni);

        if (empleado.getId() == null) {
            if (empleadoRepository.existsByDni(dni)) {
                throw new IllegalArgumentException("Ya existe un empleado registrado con el DNI: " + dni);
            }
        } else {
            if (empleadoRepository.existsByDniAndIdNot(dni, empleado.getId())) {
                throw new IllegalArgumentException("Otro empleado ya tiene registrado el DNI: " + dni);
            }
        }

        return empleadoRepository.save(empleado);
    }

    @Transactional
    public void eliminarLogico(Long id) {
        Empleado emp = buscarPorId(id);
        if (emp != null) {
            emp.setEstado(false);
            empleadoRepository.save(emp);
        }
    }
}
