package com.restaurant.ms_producto.dto;

import com.restaurant.ms_producto.model.Producto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDTO {
    @NotNull(message = "El id del producto no puede ser nulo")  
    private Long id;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String nombreProducto;

    @NotNull(message = "El precio del producto no puede ser nulo")
    @Positive(message = "El precio del producto debe ser un valor positivo")
    private Double precio;

    @NotBlank(message = "La descripción del producto no puede estar vacía")
    private String descripcion;

    @NotNull(message = "El estado del producto no puede ser nulo")  
    private Boolean estadoProducto;

    @NotNull(message = "La categoria del producto no puede ser nula")
    @Valid
    private CategoriaDTO categoria;

    public Producto toModel(){
        return new Producto(id, nombreProducto, precio, descripcion, estadoProducto ,categoria != null ? categoria.toModel() : null);
    }

    public static ProductoDTO fromModel(Producto resultado) {
        if(resultado == null) return null;
        return new ProductoDTO(resultado.getId(), resultado.getNombreProducto(), resultado.getPrecio(), resultado.getDescripcion(), resultado.isEstadoProducto(), CategoriaDTO.fromModel(resultado.getCategoria()));
    }
}
