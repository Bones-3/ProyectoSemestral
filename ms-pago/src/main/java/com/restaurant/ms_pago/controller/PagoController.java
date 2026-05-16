package com.restaurant.ms_pago.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.ms_pago.dto.PagoDTO;
import com.restaurant.ms_pago.model.Pago;
import com.restaurant.ms_pago.service.PagoService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/pago")
public class PagoController {
    private final PagoService pagoService;

    public PagoController (PagoService pagoService) {
        this.pagoService = pagoService;
    }

    //listar pagos
    @GetMapping()
    public ResponseEntity <List<PagoDTO>> getListar() {
        List<Pago> pagos = pagoService.listar();
        List<PagoDTO> dtos = pagos.stream().map(PagoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    //buscar pago por id
    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtenerPorId(@PathVariable Long id) {
        Pago pago = pagoService.buscar(id);
        return ResponseEntity.ok(PagoDTO.fromModel(pago));
    }

    //list only for pay method
    @GetMapping("/metodo/{metodoPago}")
    public ResponseEntity<List<PagoDTO>> buscarPorMetodo(@PathVariable String metodoPago) {
        List<Pago> pagos = pagoService.buscarPorMetodoPago(metodoPago);
        List<PagoDTO> dtos = pagos.stream().map(PagoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    //save 
    @PostMapping()
    public ResponseEntity<PagoDTO> postSave(PagoDTO pagoDTO) {
        Pago pago = pagoService.save(pagoDTO.toModel());
        return ResponseEntity.ok(PagoDTO.fromModel(pago));
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<PagoDTO> putModificarProducto(@PathVariable Long id, @RequestBody PagoDTO pagoDTO) {
        Pago resultado = pagoService.modificar(id, pagoDTO.toModel()); // ← ya no se llama .toModel()

        if (resultado != null) {
            return ResponseEntity.ok(PagoDTO.fromModel(resultado));
        }
        return ResponseEntity.notFound().build();
    }


    
}
