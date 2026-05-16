package com.restaurant.ms_usuario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.ms_usuario.dto.UsuarioDTO;
import com.restaurant.ms_usuario.model.Usuario;
import com.restaurant.ms_usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    //Constructor
    public UsuarioService (UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Lista de usuarios
    public List<Usuario> Listar() {
        return usuarioRepository.findAll();
    }

    //Lista de usuarios activos
    public List<Usuario> listarUsuariosActivos() {
        return usuarioRepository.findAll();
    }

    //Buscamos por la id del usuario
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    //Verifica que exista el id
    public boolean findById (Long id) {
        return usuarioRepository.existsById(id);
    }

    //Creamos al usuario
    public Usuario save (Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    //modificamos el usuario
    public Usuario modificarUsuario(Long id, UsuarioDTO dto) {
        return usuarioRepository.findById(id).map(u -> {
            u.setCorreoUsuario(dto.getCorreoUsuario());
            u.setNombres(dto.getNombres());
            u.setApellidos(dto.getApellidos());
            u.setTelefonoUsuario(dto.getTelefonoUsuario());
            u.setEstadoUsuario(dto.isEstadoUsuario());
            return usuarioRepository.save(u);
        }).orElse(null);
    }

    //Implementamos el desactivar para que no se afecten otras tablas
    public void desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEstadoUsuario(false);
        usuarioRepository.save(usuario);
    }
    
}
