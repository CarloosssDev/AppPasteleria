package com.upn.repository;

import com.upn.model.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByEstadoTrue();

    @Query("SELECT c FROM Categoria c "
            + "WHERE (:estado IS NULL OR c.estado = :estado) "
            + "AND (:nombre = '' OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    List<Categoria> filtrarCategorias(
            @Param("estado") Boolean estado,
            @Param("nombre") String nombre
    );

}
