package com.app.bnc.controller;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.bnc.dto.MovimientoDTO;
import com.app.bnc.dto.ReporteMovimientosDTO;
import com.app.bnc.service.MovimientoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/movimientos")
public class MovimientoController {

	//Inyección de depedencias por constructor
	private MovimientoService movimientoService;
	
	private static Logger logger = LogManager.getLogger(CuentaController.class);
	
	@PostMapping("/crear") 
	public ResponseEntity<String> guardarMovimientoPorCuenta(@RequestBody MovimientoDTO movDto) {
		movimientoService.guardarMovimiento(movDto);
		String response = "Movimiento a cuenta ("+movDto.getNroCuenta()+") Registrado con Exito.";
		logger.info(response);
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
	} 
	
	@PostMapping("eliminar/{id}")
	public ResponseEntity<String> eliminarMovimiento(@PathVariable("id") Long id) {
		movimientoService.eliminarOReversarMovimiento(id);
		String response = "Movimiento "+id+" eliminado/reversado exitosamente.";
		logger.info(response);
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
	
	@GetMapping("/reportes")
	public ResponseEntity<List<ReporteMovimientosDTO>> geerarRptMovPorCliente(
			@RequestParam(value = "fechaDesde", required = true, defaultValue = "") LocalDate fechaDesde, 
            @RequestParam(value = "fechaHasta", required = true, defaultValue = "") LocalDate fechaHasta,
            @RequestParam(value = "idCliente", required = true, defaultValue = "0") Long idCliente){
		return  ResponseEntity.ok(movimientoService.getMovimientosPorCliente(fechaDesde, fechaHasta, idCliente));
	}
}
