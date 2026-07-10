package com.lulu.app.repository;

import com.lulu.app.model.Venta;
import com.lulu.app.model.EstadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByClienteIdOrderByFechaVentaDesc(Long clienteId);

    List<Venta> findByEstadoPagoOrderByFechaVentaDesc(EstadoPago estadoPago);

    List<Venta> findByFechaVentaBetweenOrderByFechaVentaDesc(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT COALESCE(SUM(v.montoTotal), 0) FROM Venta v")
    BigDecimal sumTotalVentas();

    @Query("SELECT COALESCE(SUM(v.montoPagado), 0) FROM Venta v")
    BigDecimal sumTotalPagado();

    @Query("SELECT COALESCE(SUM(v.montoTotal - v.montoPagado), 0) FROM Venta v WHERE v.estadoPago != 'PAGADO'")
    BigDecimal sumTotalAdeudado();

    Long countByEstadoPago(EstadoPago estadoPago);

    @Query("SELECT v FROM Venta v WHERE v.estadoPago IN ('ADEUDADO', 'PARCIAL') ORDER BY v.fechaVenta DESC")
    List<Venta> findVentasConDeuda();
}