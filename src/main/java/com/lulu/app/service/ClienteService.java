package com.lulu.app.service;

import com.lulu.app.model.Cliente;
import com.lulu.app.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Cliente crear(Cliente cliente) {
        if (cliente.getTelefono() != null && !cliente.getTelefono().isBlank()
                && clienteRepository.existsByTelefono(cliente.getTelefono())) {
            throw new RuntimeException("Ya existe un cliente con ese telefono");
        }
        if (cliente.getEmail() != null && !cliente.getEmail().isBlank()
                && clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Ya existe un cliente con ese email");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente datos) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));

        cliente.setNombre(datos.getNombre());
        cliente.setTelefono(datos.getTelefono());
        cliente.setEmail(datos.getEmail());
        cliente.setDireccion(datos.getDireccion());
        cliente.setNotas(datos.getNotas());

        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
        clienteRepository.deleteById(id);
    }
}