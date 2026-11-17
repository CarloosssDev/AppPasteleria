package com.upn.repository;

import com.upn.enums.Rol;
import com.upn.model.Empleado;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByDniAndEstadoTrue(String dni);

    boolean existsByDni(String dni);

    boolean existsByDniAndIdNot(String dni, Long id);

    @Query("SELECT e FROM Empleado e "
            + "WHERE (:rol IS NULL OR e.rol = :rol) "
            + "AND (:estado IS NULL OR e.estado = :estado) "
            + "AND (:filtro = '' "
            + "     OR e.dni LIKE CONCAT('%', :filtro, '%') "
            + "     OR LOWER(e.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) "
            + "     OR LOWER(e.apellido) LIKE LOWER(CONCAT('%', :filtro, '%')))")
    List<Empleado> filtrarEmpleados(
            @Param("rol") Rol rol,
            @Param("estado") Boolean estado,
            @Param("filtro") String filtro
    );
}
