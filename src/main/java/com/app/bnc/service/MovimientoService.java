package com.app.bnc.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.app.bnc.dto.MovimientoDTO;
import com.app.bnc.dto.ReporteMovimientosDTO;
import com.app.bnc.model.entities.Cuenta;
import com.app.bnc.model.entities.Movimiento;

public interface MovimientoService {
	Movimiento guardarMovimiento(MovimientoDTO cuenta);
	void eliminarOReversarMovimiento(Long id); 
	BigDecimal getSaldoPorCuentaCliente(Cuenta cuenta)throws Exception;
	List<ReporteMovimientosDTO> getMovimientosPorCliente(LocalDate fechaDesde, LocalDate fechaHasta, Long idCliente);
}