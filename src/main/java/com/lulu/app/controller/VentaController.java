package com.lulu.app.controller;

import com.lulu.app.model.Venta;
import com.lulu.app.model.EstadoPago;
import com.lulu.app.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public ResponseEntity<List<Venta>> listarTodas() {
        return ResponseEntity.ok(ventaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerPorId(@PathVariable Long id) {
        return ventaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Venta>> obtenerPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(ventaService.obtenerPorCliente(clienteId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Venta>> obtenerPorEstado(@PathVariable EstadoPago estado) {
        return ResponseEntity.ok(ventaService.obtenerPorEstado(estado));
    }

    @GetMapping("/deudas")
    public ResponseEntity<List<Venta>> obtenerVentasConDeuda() {
        return ResponseEntity.ok(ventaService.obtenerVentasConDeuda());
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<Venta>> obtenerPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(ventaService.obtenerPorFecha(inicio, fin));
    }

    @PostMapping
    public ResponseEntity<Venta> crear(@Valid @RequestBody Venta venta) {
        Venta creada = ventaService.crear(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizar(@PathVariable Long id, @Valid @RequestBody Venta venta) {
        try {
            return ResponseEntity.ok(ventaService.actualizar(id, venta));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            ventaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dashboard/resumen")
    public ResponseEntity<Map<String, Object>> obtenerResumen() {
        BigDecimal totalVentas = ventaService.getTotalVentas();
        BigDecimal totalPagado = ventaService.getTotalPagado();
        BigDecimal totalAdeudado = ventaService.getTotalAdeudado();
        Long countPagado = ventaService.countByEstado(EstadoPago.PAGADO);
        Long countAdeudado = ventaService.countByEstado(EstadoPago.ADEUDADO);
        Long countParcial = ventaService.countByEstado(EstadoPago.PARCIAL);

        return ResponseEntity.ok(Map.of(
                "totalVentas", totalVentas,
                "totalPagado", totalPagado,
                "totalAdeudado", totalAdeudado,
                "cantidadPagado", countPagado,
                "cantidadAdeudado", countAdeudado,
                "cantidadParcial", countParcial
        ));
    }
}