package com.restaurant.ms_producto.dto;

import com.restaurant.ms_producto.model.Producto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDTO {
    private Long id;
    private String nombreProducto;
    private Double precio;
    private String descripcion;
    private Boolean estadoProducto;
    private CategoriaDTO categoria;

    public Producto toModel(){
        return new Producto(id, nombreProducto, precio, descripcion, estadoProducto ,categoria != null ? categoria.toModel() : null);
    }

    public static ProductoDTO fromModel(Producto resultado) {
        if(resultado == null) return null;
        return new ProductoDTO(resultado.getId(), resultado.getNombreProducto(), resultado.getPrecio(), resultado.getDescripcion(), resultado.isEstadoProducto(), CategoriaDTO.fromModel(resultado.getCategoria()));
    }
}
