package com.restaurant.ms_producto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.ms_producto.model.Categoria;
import com.restaurant.ms_producto.repository.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    //Es el constructor 
    public CategoriaService (CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    //Permite crear el objeto
    public Categoria save (Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    //Muestra la lista completa de Categoria
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    //Solo verifica que exista, es caso de que exista devuelve un true y en caso contrario un false
    public boolean existeId(long id) {
        return categoriaRepository.existsById(id);
    }

    //Metodo buscar por id 
    public Categoria buscarPorId(Long id){
    return categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }

    //Metodo modificar lacategoria 
    public Categoria modificar (long id, Categoria categoria) {
        return categoriaRepository.findById(id).map(categoriaExistente -> {
            categoriaExistente.setNombreCategoria(categoria.getNombreCategoria());
            categoriaExistente.setEstadoCategoria(categoria.isEstadoCategoria());
            return categoriaRepository.save(categoriaExistente);
        }).orElse(null);
    }

    //Metodo desactivar, no se utiliza el eliminar porque generaria problemas en las otras tabla que 
    //esten realacionadas con la tabla categoria.
    public void desactivarCategoria(Long id) {
        // Buscamos el producto, si no existe lanzamos error
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrado"));

        // Cambiamos el estado a false
        categoria.setEstadoCategoria(false);

        // Guardamos los cambios
        categoriaRepository.save(categoria);
    }

}
