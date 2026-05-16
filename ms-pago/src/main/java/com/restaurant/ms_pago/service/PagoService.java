package com.restaurant.ms_pago.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.restaurant.ms_pago.feign.PedidoFeign;
import com.restaurant.ms_pago.feign.UsuarioFeign;
import com.restaurant.ms_pago.model.Pago;
import com.restaurant.ms_pago.repository.PagoRepository;

@Service
public class PagoService {
    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    private final PagoRepository pagoRepository;
    private final PedidoFeign pedidoFeign;
    private final UsuarioFeign usuarioFeign;

    public PagoService (PagoRepository pagoRepository, PedidoFeign pedidoFeign, UsuarioFeign usuarioFeign) {
        this.pagoRepository = pagoRepository;
        this.pedidoFeign = pedidoFeign;
        this.usuarioFeign = usuarioFeign;
    }

    //buscar por id
    public Pago buscar (Long id) {
        return pagoRepository.findById(id).get();//al usar get no nos retornara ningun valor.
    }

    //save pago
    public Pago save(Pago pago) {
        log.info("[PagoService] Intentando crear pago para pedido_id={}", pago.getPedido_id());
 
        // Valida que el pedido exista en ms-pedido
        Boolean pedidoExiste = pedidoFeign.existePedido(pago.getPedido_id());
        if (Boolean.FALSE.equals(pedidoExiste)) {
            log.error("[PagoService] El pedido con id={} no existe en ms-pedido", pago.getPedido_id());
            throw new RuntimeException("El pedido con id=" + pago.getPedido_id() + " no existe.");
        }
 
        // Valida que el usuario exista en ms-usuario (usando usuario_id del pago)
        if (pago.getUsuario_id() != null) {
            Boolean usuarioExiste = usuarioFeign.existeUsuario(pago.getUsuario_id());
            if (Boolean.FALSE.equals(usuarioExiste)) {
                log.error("[PagoService] El usuario con id={} no existe en ms-usuario", pago.getUsuario_id());
                throw new RuntimeException("El usuario con id=" + pago.getUsuario_id() + " no existe.");
            }
        }
 
        Pago guardado = pagoRepository.save(pago);
        log.info("[PagoService] Pago creado con id={}", guardado.getId());
        return guardado;
    }

    //put pago
    public Pago modificar (Long id, Pago pago) {
        return pagoRepository.findById(id).map(pagoExiste -> {
            pagoExiste.setMetodoPago(pago.getMetodoPago());
            pagoExiste.setFechaPago(pago.getFechaPago());
            return pagoRepository.save(pagoExiste);
        }).orElse(null);
    }

    //DElete method
    public void eliminar(Long id) {
        log.info("[PagoService] Eliminando pago con id={}", id);
        if (!pagoRepository.existsById(id)) {
            log.warn("[PagoService] Pago con id={} no encontrado para eliminar", id);
            throw new RuntimeException("Pago no encontrado con id: " + id);
        }
        pagoRepository.deleteById(id);
    }    

    //list 
    public List<Pago> listar() {
        return pagoRepository.findAll();
    }    

    //list for metodopago
    public List<Pago> buscarPorMetodoPago(String metodoPago) {
        log.info("[PagoService] Buscando pagos con metodoPago={}", metodoPago);
        return pagoRepository.findByMetodoPagoIgnoreCase(metodoPago);
    }
}
