package com.upn.repository;

import com.upn.model.Venta;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IVentaRepository extends JpaRepository<Venta, Long> {

    @Query("SELECT DISTINCT v FROM Venta v "
            + "LEFT JOIN FETCH v.cliente "
            + "LEFT JOIN FETCH v.empleado "
            + "LEFT JOIN FETCH v.detalles d "
            + "LEFT JOIN FETCH d.producto "
            + "WHERE v.fecha BETWEEN :inicio AND :fin "
            + "ORDER BY v.fecha DESC")
    List<Venta> findByFechaBetweenWithDetails(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    @Query("SELECT DISTINCT v FROM Venta v "
            + "LEFT JOIN FETCH v.cliente "
            + "LEFT JOIN FETCH v.empleado "
            + "LEFT JOIN FETCH v.detalles d "
            + "LEFT JOIN FETCH d.producto "
            + "ORDER BY v.fecha DESC")
    List<Venta> findAllWithDetails();
}
