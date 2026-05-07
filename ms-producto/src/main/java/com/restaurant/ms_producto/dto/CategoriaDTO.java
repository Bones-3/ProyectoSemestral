package com.restaurant.ms_producto.dto;

import com.restaurant.ms_producto.model.Categoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaDTO {
    private Long id;
    private String nombreCategoria;

    public Categoria toModel() {
        return new Categoria(id, nombreCategoria);
    }

    public static CategoriaDTO fromModel(Categoria c) {
        if (c == null) return null;
        return new CategoriaDTO(c.getId(), c.getNombreCategoria());
    }
}
