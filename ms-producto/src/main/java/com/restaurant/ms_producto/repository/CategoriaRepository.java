package com.restaurant.ms_producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.ms_producto.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository <Categoria, Long>{

}
