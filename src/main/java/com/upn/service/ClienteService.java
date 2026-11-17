package com.upn.service;

import com.upn.model.Cliente;
import com.upn.repository.IClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public Cliente buscarPorDni(String dni) {
        if (dni == null || dni.isBlank()) {
            return null;
        }
        return clienteRepository.findByDniAndEstadoTrue(dni.trim()).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cliente> filtrarClientes(Boolean estado, String texto) {
        String filtro = (texto == null) ? "" : texto.trim();
        return clienteRepository.filtrarClientes(estado, filtro);
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Transactional
    public Cliente guardar(Cliente cliente) {
        String dni = cliente.getDni() != null ? cliente.getDni().trim() : "";
        cliente.setDni(dni);

        if (cliente.getId() == null) {
            if (clienteRepository.existsByDni(dni)) {
                throw new IllegalArgumentException("Ya existe un cliente registrado con el DNI: " + dni);
            }
        } else {
            if (clienteRepository.existsByDniAndIdNot(dni, cliente.getId())) {
                throw new IllegalArgumentException("Otro cliente ya tiene registrado el DNI: " + dni);
            }
        }

        return clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminarLogico(Long id) {
        Cliente cliente = buscarPorId(id);
        if (cliente != null) {
            cliente.setEstado(false);
            clienteRepository.save(cliente);
        }
    }
}
