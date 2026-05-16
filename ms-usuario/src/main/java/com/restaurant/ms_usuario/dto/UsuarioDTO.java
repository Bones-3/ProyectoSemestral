package com.restaurant.ms_usuario.dto;

import com.restaurant.ms_usuario.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    private String correoUsuario;
    private String nombres;
    private String apellidos;
    private String telefonoUsuario;
    private boolean estadoUsuario;
    
    public Usuario toModel(){
        return new Usuario(id, correoUsuario, nombres, apellidos, telefonoUsuario, estadoUsuario);
    }

    public static UsuarioDTO fromModel(Usuario u) {
        if(u == null) return null;
        return new UsuarioDTO(u.getId(), u.getCorreoUsuario(), u.getNombres(), u.getApellidos(), u.getTelefonoUsuario(), u.isEstadoUsuario());
    }

}
