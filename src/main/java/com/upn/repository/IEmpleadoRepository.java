/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.upn.repository;

import com.upn.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author user
 */
public interface IEmpleadoRepository extends JpaRepository<Empleado, Long> {

}
