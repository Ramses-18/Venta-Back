package com.lulu.app.service;

import com.lulu.app.model.Venta;
import com.lulu.app.model.EstadoPago;
import com.lulu.app.repository.VentaRepository;
import com.lulu.app.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;

    public VentaService(VentaRepository ventaRepository, ClienteRepository clienteRepository) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Venta> obtenerTodas() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> obtenerPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public List<Venta> obtenerPorCliente(Long clienteId) {
        return ventaRepository.findByClienteIdOrderByFechaVentaDesc(clienteId);
    }

    public List<Venta> obtenerPorEstado(EstadoPago estadoPago) {
        return ventaRepository.findByEstadoPagoOrderByFechaVentaDesc(estadoPago);
    }

    public List<Venta> obtenerPorFecha(LocalDateTime inicio, LocalDateTime fin) {
        return ventaRepository.findByFechaVentaBetweenOrderByFechaVentaDesc(inicio, fin);
    }

    public List<Venta> obtenerVentasConDeuda() {
        return ventaRepository.findVentasConDeuda();
    }

    public Venta crear(Venta venta) {
        if (venta.getCliente() == null || venta.getCliente().getId() == null) {
            throw new RuntimeException("La venta debe tener un cliente asociado");
        }
        if (!clienteRepository.existsById(venta.getCliente().getId())) {
            throw new RuntimeException("El cliente no existe");
        }
        return ventaRepository.save(venta);
    }

    public Venta actualizar(Long id, Venta datos) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));

        venta.setDescripcion(datos.getDescripcion());
        venta.setMontoTotal(datos.getMontoTotal());
        venta.setMontoPagado(datos.getMontoPagado());
        venta.setEstadoPago(datos.getEstadoPago());
        venta.setFechaVenta(datos.getFechaVenta());
        venta.setNotas(datos.getNotas());

        return ventaRepository.save(venta);
    }

    public void eliminar(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada con id: " + id);
        }
        ventaRepository.deleteById(id);
    }

    // Datos para el dashboard
    public BigDecimal getTotalVentas() {
        return ventaRepository.sumTotalVentas();
    }

    public BigDecimal getTotalPagado() {
        return ventaRepository.sumTotalPagado();
    }

    public BigDecimal getTotalAdeudado() {
        return ventaRepository.sumTotalAdeudado();
    }

    public Long countByEstado(EstadoPago estado) {
        return ventaRepository.countByEstadoPago(estado);
    }
}