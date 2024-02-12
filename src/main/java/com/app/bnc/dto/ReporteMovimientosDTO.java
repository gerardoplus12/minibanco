package com.app.bnc.dto;

import java.math.BigDecimal;

public interface ReporteMovimientosDTO {
	BigDecimal getId(); 
	String getFecha(); 
	String getNombre();
	String getNroCuenta();
	String getTipoMovimiento();
	Double getSaldoInicial();
	String getEstado();
	Double getMonto();
	String getDescripcion();
	BigDecimal getSaldo(); 
}