package com.upn.repository;

import com.upn.model.Cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByDni(String dni);

    Optional<Cliente> findByDniAndEstadoTrue(String dni);

    boolean existsByDni(String dni);

    boolean existsByDniAndIdNot(String dni, Long id);

    @Query("SELECT c FROM Cliente c "
            + "WHERE (:estado IS NULL OR c.estado = :estado) "
            + "AND (:filtro = '' "
            + "     OR c.dni LIKE CONCAT('%', :filtro, '%') "
            + "     OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) "
            + "     OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', :filtro, '%')))")
    List<Cliente> filtrarClientes(
            @Param("estado") Boolean estado,
            @Param("filtro") String filtro
    );
}
