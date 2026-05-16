package com.restaurant.ms_producto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.ms_producto.dto.ProductoDTO;
import com.restaurant.ms_producto.model.Categoria;
import com.restaurant.ms_producto.model.Producto;
import com.restaurant.ms_producto.repository.CategoriaRepository;
import com.restaurant.ms_producto.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductoService {
    private final  ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;


    //Es el constructor de Producto Service
    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    //Permite ver todos lo productos
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    //Permite buscar el producto por id
    public Producto buscarPorId(Long id) {
    return productoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    //Verifica si existe la id
    public boolean existeid(Long id) {
        return productoRepository.existsById(id);
    }

    //Metodo de save para producto
    public Producto save(ProductoDTO dto) {
        Producto producto = dto.toModel();
        
        // 2. Extraemos el ID de la categoría desde el DTO anidado
        if (dto.getCategoria() != null && dto.getCategoria().getId() != null) {
            Long categoriaId = dto.getCategoria().getId();
            
            // 3. Buscamos la categoría real en MySQL
            Categoria categoriaReal = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Error: La categoría con ID " + categoriaId + " no existe"));
            
            // 4. Establecemos la relación completa en el objeto Producto[cite: 1]
            producto.setCategoria(categoriaReal);
        }
        // 5. Ahora guardamos el producto con su FK correctamente establecida[cite: 1]
        return productoRepository.save(producto);
    }

    //Permite modificar el producto
    public Producto modificar(Long id, ProductoDTO dto) {
        Producto p = productoRepository.findById(id).orElse(null);
        if (p == null) return null;

        p.setNombreProducto(dto.getNombreProducto());
        p.setPrecio(dto.getPrecio());
        p.setDescripcion(dto.getDescripcion());
        p.setEstadoProducto(dto.getEstadoProducto());

        if (dto.getCategoria() != null && dto.getCategoria().getId() != null) {
            Categoria cat = categoriaRepository.findById(dto.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            p.setCategoria(cat);
        }

        return productoRepository.save(p);
    }

    //Permite generar una lista solo con los productos que pertenezcan a la misma categoria
    public List<Producto> buscarPorCategoria(Long categoriaId) {
        if(!categoriaRepository.existsById(categoriaId)) {
            return null;
        }
        return productoRepository.findByCategoria_Id(categoriaId);
    }

    //El producto solo se desactiva no se elimana por el hecho que que hacer esto generaria errores en otros microservicios
    public void desactivarProducto(Long id) {
        // Buscamos el producto, si no existe lanzamos error
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        // Cambiamos el estado a false
        producto.setEstadoProducto(false);

        // Guardamos los cambios
        productoRepository.save(producto);
    }

    //Genera una lista con solo los producto activos(que esten disponibles)
    public List<Producto> listarSoloActivos() {
    return productoRepository.findByEstadoProductoTrue();
    }
}
