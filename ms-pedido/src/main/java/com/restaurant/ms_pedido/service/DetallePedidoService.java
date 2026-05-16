package com.restaurant.ms_pedido.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.restaurant.ms_pedido.feign.ProductoFeign;
import com.restaurant.ms_pedido.feign.ProductoResponse;
import com.restaurant.ms_pedido.model.DetallePedido;
import com.restaurant.ms_pedido.repository.DetallePedidoRepository;

@Service
public class DetallePedidoService {
    private static final Logger log = LoggerFactory.getLogger(DetallePedidoService.class);

    private DetallePedidoRepository detallePedidoRepository;
    private ProductoFeign productoFeign;

    public DetallePedidoService (DetallePedidoRepository detallePedidoRepository, ProductoFeign productoFeign) {
        this.detallePedidoRepository = detallePedidoRepository;
        this.productoFeign = productoFeign;
    }

    //Nos trae todos los detalle pedido que existen
    public List<DetallePedido> listar () {
        return detallePedidoRepository.findAll();
    }

    //Nos permite verificar si existe el detalle, sin traer todos los datos de este detalle retornando un true or false
    public boolean find (Long id){
        return detallePedidoRepository.existsById(id);
    }

    //permite crear el objeto detalle pedido
    public DetallePedido saveDetallePedido(DetallePedido detallePedido) {
        log.info("[DetallePedidoService] Creando detalle para productoId={}", detallePedido.getProductoId());
 
        Boolean productoExiste = productoFeign.existeProducto(detallePedido.getProductoId());
        if (Boolean.FALSE.equals(productoExiste)) {
            log.error("[DetallePedidoService] El producto con id={} no existe", detallePedido.getProductoId());
            throw new RuntimeException("El producto con id=" + detallePedido.getProductoId() + " no existe.");
        }
 
        // Obtener precio actual del producto para calcular subtotal
        ProductoResponse producto = productoFeign.obtenerProducto(detallePedido.getProductoId());
        detallePedido.setPrecioUnitario(producto.getPrecio());
        detallePedido.setSubtotal(producto.getPrecio() * detallePedido.getCantidad());
 
        log.info("[DetallePedidoService] Subtotal calculado: {}", detallePedido.getSubtotal());
        return detallePedidoRepository.save(detallePedido);
    }
    //
    public DetallePedido modificar (Long id, DetallePedido detallePedido) {
        return detallePedidoRepository.findById(id).map(detalleExiste -> {
            detalleExiste.setCantidad(detallePedido.getCantidad());
            detalleExiste.setNotasPedido(detallePedido.getNotasPedido());
            return detallePedidoRepository.save(detalleExiste);

        }).orElse(null);
    } 
    
    public DetallePedido buscar (Long id) {
        return detallePedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Detalle pedido no encontrado"));
    }
}
