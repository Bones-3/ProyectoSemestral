package com.restaurant.ms_producto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.ms_producto.dto.ProductoDTO;
import com.restaurant.ms_producto.model.Categoria;
import com.restaurant.ms_producto.model.Producto;
import com.restaurant.ms_producto.repository.CategoriaRepository;
import com.restaurant.ms_producto.repository.ProductoRepository;

@Service
public class ProductoService {
    private final  ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

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

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public boolean existeid(Long id) {
        return productoRepository.existsById(id);
    }


}
