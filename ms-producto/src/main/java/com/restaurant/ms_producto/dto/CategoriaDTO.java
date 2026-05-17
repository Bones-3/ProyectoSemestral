package com.restaurant.ms_producto.dto;

import com.restaurant.ms_producto.model.Categoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaDTO {
    @NotNull(message = "El id de la categoria no puede ser nulo")
    private Long id;
    @NotNull(message = "El nombre de la categoria no puede ser nulo")
    private String nombreCategoria;
    @NotNull(message = "El estado de la categoria no puede ser nulo")
    private Boolean estadoCategoria;

    public Categoria toModel() {
        return new Categoria(id, nombreCategoria, estadoCategoria);
    }

    public static CategoriaDTO fromModel(Categoria c) {
        if (c == null) return null;
        return new CategoriaDTO(c.getId(), c.getNombreCategoria(), c.getEstadoCategoria());
    }
}
