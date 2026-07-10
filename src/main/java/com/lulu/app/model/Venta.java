package com.lulu.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El cliente es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotBlank(message = "La descripcion es obligatoria")
    @Column(nullable = false)
    private String descripcion;

    @NotNull(message = "El monto total es obligatorio")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "monto_pagado", precision = 12, scale = 2)
    private BigDecimal montoPagado;

    @NotNull(message = "El estado de pago es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false, length = 20)
    private EstadoPago estadoPago;

    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "notas")
    private String notas;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (fechaVenta == null) {
            fechaVenta = LocalDateTime.now();
        }
        if (montoPagado == null) {
            montoPagado = BigDecimal.ZERO;
        }
        if (estadoPago == EstadoPago.PAGADO && montoPagado.compareTo(BigDecimal.ZERO) == 0) {
            montoPagado = montoTotal;
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }

    public BigDecimal getMontoPagado() { return montoPagado; }
    public void setMontoPagado(BigDecimal montoPagado) { this.montoPagado = montoPagado; }

    public EstadoPago getEstadoPago() { return estadoPago; }
    public void setEstadoPago(EstadoPago estadoPago) { this.estadoPago = estadoPago; }

    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}