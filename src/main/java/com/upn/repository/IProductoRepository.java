package com.upn.repository;

import com.upn.model.ProductoPasteleria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductoRepository extends JpaRepository<ProductoPasteleria, Long> {

    @Query("SELECT p FROM ProductoPasteleria p JOIN FETCH p.categoria")
    List<ProductoPasteleria> findAllWithCategoria();

    @Query("SELECT p FROM ProductoPasteleria p JOIN FETCH p.categoria WHERE p.estado = false")
    List<ProductoPasteleria> findAllInactivosWithCategoria();

    @Query("SELECT p FROM ProductoPasteleria p JOIN FETCH p.categoria WHERE p.estado = true")
    List<ProductoPasteleria> findAllActivosWithCategoria();

    @Query("SELECT p FROM ProductoPasteleria p JOIN FETCH p.categoria WHERE p.estado = true AND LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<ProductoPasteleria> findByNombreContainingIgnoreCaseWithCategoria(@Param("nombre") String nombre);

    @Query("SELECT p FROM ProductoPasteleria p JOIN FETCH p.categoria WHERE p.estado = true AND (:idCategoria = 0 OR p.categoria.id = :idCategoria)")
    List<ProductoPasteleria> findByCategoriaIdWithCategoria(@Param("idCategoria") Long idCategoria);

    @Query("SELECT p FROM ProductoPasteleria p JOIN FETCH p.categoria c "
            + "WHERE (:idCategoria = 0 OR c.id = :idCategoria) "
            + "AND (:estado IS NULL OR p.estado = :estado) "
            + "AND (:nombre = '' OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    List<ProductoPasteleria> filtrarProductosCombinados(
            @Param("idCategoria") Long idCategoria,
            @Param("estado") Boolean estado,
            @Param("nombre") String nombre
    );

}
