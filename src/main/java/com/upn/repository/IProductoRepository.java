package com.upn.repository;

import com.upn.model.ProductoPasteleria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<ProductoPasteleria, Long> {

    List<ProductoPasteleria> findByCategoriaId(Long id);

    List<ProductoPasteleria> findByNombreContainingIgnoreCase(String nombre);

}
