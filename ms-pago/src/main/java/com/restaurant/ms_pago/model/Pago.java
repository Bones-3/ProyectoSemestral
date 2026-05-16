package com.restaurant.ms_pago.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pago")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long id;

    @Column(name = "pedido_id")
    private Long pedido_id;

    @Column(name = "usuario_id")
    private Long usuario_id;

    @Column(name = "monto")
    private Double monto;

    @Column(name = "metodo_pago")
    private String metodoPago;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;
}
