package com.restaurant.ms_usuario.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.ms_usuario.dto.UsuarioDTO;
import com.restaurant.ms_usuario.model.Usuario;
import com.restaurant.ms_usuario.service.UsuarioService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController (UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping()
    public ResponseEntity <List<UsuarioDTO>> listar() {
        List<Usuario> usuarios = usuarioService.Listar();
        List<UsuarioDTO> dtos = usuarios.stream().map(UsuarioDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/Activos")
    public ResponseEntity<List<UsuarioDTO>> listarActivos() {
        List<Usuario> usuarios = usuarioService.listarUsuariosActivos();
        List<UsuarioDTO> dtos = usuarios.stream()
            .map(UsuarioDTO::fromModel)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioDTO.fromModel(usuario));
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existeUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }
    
    @PostMapping()
    public ResponseEntity <UsuarioDTO> saveUsuario (@RequestBody UsuarioDTO usuarioDTO) {
        Usuario nuevo = usuarioService.save(usuarioDTO.toModel());
        return ResponseEntity.ok(UsuarioDTO.fromModel(nuevo));
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<UsuarioDTO> putModificarProducto(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        Usuario resultado = usuarioService.modificarUsuario(id, usuarioDTO); // ← ya no se llama .toModel()

        if (resultado != null) {
            return ResponseEntity.ok(UsuarioDTO.fromModel(resultado));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        try {
            usuarioService.desactivarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
