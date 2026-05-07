package com.restaurant.ms_producto.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.ms_producto.dto.CategoriaDTO;
import com.restaurant.ms_producto.model.Categoria;
import com.restaurant.ms_producto.service.CategoriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController (CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping()
    public ResponseEntity <List<CategoriaDTO>> listarCategorias() {
        List<Categoria> productos = categoriaService.listar();
        List<CategoriaDTO> dtos = productos.stream().map(CategoriaDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
	public ResponseEntity<Boolean> existeCategoria(@PathVariable Long id) {
		return ResponseEntity.ok(categoriaService.existeId(id));
	}

    @PostMapping()
	public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
	    Categoria nuevo = categoriaService.save(categoriaDTO.toModel());
		return ResponseEntity.ok(CategoriaDTO.fromModel(nuevo));
	}

}
