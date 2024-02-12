package com.app.bnc.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.bnc.dto.ReporteMovimientosDTO;
import com.app.bnc.model.entities.Cuenta;
import com.app.bnc.model.entities.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long>{
	
	@Query(value = "SELECT U.saldo FROM movimiento U WHERE U.nro_cuenta = ?1 AND U.id = (SELECT MAX(M.id) "
			+ "FROM movimiento M WHERE M.nro_cuenta = ?1)", 
			  nativeQuery = true)
	Optional<BigDecimal> getSaldoPorCuentaClienteRepo(String nroCuenta);
	
	List<Movimiento> findAllByCuenta (Cuenta cuenta);
	
	@Query(value = "SELECT ABS(SUM(U.monto)) FROM movimiento U WHERE U.fecha = ?1 AND U.nro_cuenta = ?2 "
			+ "AND tipo_movimiento = 'DEBITO'",
			  nativeQuery = true)
	Optional<Double> getAcumDiarioPorCuenta(String fechaConsulta, String nroCuenta) throws Exception;
	
	@Query(value = "SELECT U.id, U.fecha as fecha, Per.nombre as nombre, U.nro_cuenta AS nroCuenta, U.tipo_movimiento AS tipoMovimiento,"
			+ " Cta.saldo_inicial AS saldoInicial, Cta.estado as estado, U.monto as monto, U.descripcion as descripcion , U.saldo as saldo "
			+ "FROM movimiento U "
			+ "INNER JOIN cuenta Cta ON (Cta.nro_cuenta = U.nro_cuenta)"
			+ "INNER JOIN cliente Clte ON (Clte.id_cliente = Cta.id_cliente)"
			+ "INNER JOIN persona Per ON (Clte.id_persona = Per.id_persona)"
			+ "WHERE U.fecha BETWEEN ?1 AND ?2 AND Cta.id_cliente = ?3 ORDER BY id DESC",
			 nativeQuery = true)
	List<ReporteMovimientosDTO> getRptMovimientosPorCliente(LocalDate fechaDesde, LocalDate fechaHasta, Long idCliente) throws Exception;
	
}