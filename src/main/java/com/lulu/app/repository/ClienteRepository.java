package com.lulu.app.repository;

import com.lulu.app.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByTelefono(String telefono);
    Optional<Cliente> findByEmail(String email);
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByTelefono(String telefono);
    boolean existsByEmail(String email);
}