package com.restaurant.ms_pago.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.ms_pago.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository <Pago, Long>{
    // Búsqueda por método de pago (case insensitive)
    List<Pago> findByMetodoPagoIgnoreCase(String metodoPago);
}
