package com.restaurant.ms_producto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.ms_producto.model.Categoria;
import com.restaurant.ms_producto.repository.CategoriaRepository;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService (CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria save (Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public boolean existeId(long id) {
        return categoriaRepository.existsById(id);
    }

}
