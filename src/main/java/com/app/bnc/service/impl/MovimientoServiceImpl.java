package com.app.bnc.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.app.bnc.dto.MovimientoDTO;
import com.app.bnc.dto.ReporteMovimientosDTO;
import com.app.bnc.emun.EEstado;
import com.app.bnc.emun.EParametros;
import com.app.bnc.emun.ETipoMovimiento;
import com.app.bnc.exception.DataNotAllowedException;
import com.app.bnc.exception.DataNotFoundException;
import com.app.bnc.model.entities.Cuenta;
import com.app.bnc.model.entities.Movimiento;
import com.app.bnc.repository.CuentaRepository;
import com.app.bnc.repository.MovimientoRepository;
import com.app.bnc.service.MovimientoService;

import com.app.bnc.util.UtilDate;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MovimientoServiceImpl  implements MovimientoService{
	
	private static Logger logger = LogManager.getLogger(MovimientoServiceImpl.class);
	
	//Inyección de depedencias por constructor
	private MovimientoRepository movimientoRepository;
	private CuentaRepository cuentaRepository;
	private ModelMapper modelMapper;
	
	private static final BigDecimal BGCERO = new BigDecimal(0);
	private static final BigDecimal BGMENOS1 = new BigDecimal(-1);
	
	public void validarOperacionesCredito(BigDecimal montoCredito) {
		//Monto Credito Incorrecto
		if (montoCredito.compareTo(BGCERO)<=0) { //Monto Credito <= 0
			String mensaje = "Validación en Movimiento: El tipo de Movimiento ("+ETipoMovimiento.CREDITO+") debe manejar montos mayores a 0.";
			logger.info(mensaje);
    		throw new DataNotAllowedException(mensaje);
		}
	}
	
	public void validarOperacionesDebito(BigDecimal montoDebito, Cuenta cuentaActual) {
		//Monto Debito Incorrecto
		if (montoDebito.compareTo(BGCERO)>=0) {//Si Monto Debito >= 0
			String mensaje = "Validación en Movimiento: El tipo de Movimiento ("+ETipoMovimiento.DEBITO+") debe manejar montos menores a 0";
			logger.info(mensaje);
    		throw new DataNotAllowedException(mensaje);
		}
		//Saldo no Disponible (0)
		BigDecimal bgSaldoCuenta = this.getSaldoPorCuentaCliente(cuentaActual);
		if (bgSaldoCuenta.compareTo(BGCERO)==0) { //Si Saldo en Cuenta = 0
			String mensaje = "Validación en Movimiento de tipo Debito: Saldo no disponible”";
			logger.info(mensaje);
    		throw new DataNotAllowedException(mensaje);
		}
		//Saldo Insuficiente
		if (bgSaldoCuenta.add(montoDebito).compareTo(BGCERO)==-1) { //Si Debito-Saldo < 0
			String mensaje = "Validación en Movimiento de tipo Debito: Saldo induficiente (Saldo Actual:" + bgSaldoCuenta.toString()+")";
			logger.info(mensaje);
    		throw new DataNotAllowedException(mensaje);
		}
		//Limite Diario Excedido
		Double acumDebitosPorDia = this.getTotalRetiroPorDia(cuentaActual);
		if (acumDebitosPorDia>=EParametros.LIMITEDIARIO.getLimiteDiario()) { //Si Acumulado Diario es mayor o igual al Limite Diario
			String mensaje = "Validación en Movimiento de tipo Debito: Cupo de Retiro diario Excedido";
			logger.info(mensaje);
    		throw new DataNotAllowedException(mensaje);
		}
		
		//Monto Debito Excede el monto restante permitido por dia
		Double totalAValidar = acumDebitosPorDia + (Double.valueOf(montoDebito.toString()))*-1;
		logger.info("Total debito simulado a validar: " + totalAValidar.toString()  + "-" + cuentaActual.getNroCuenta());
		if (totalAValidar>EParametros.LIMITEDIARIO.getLimiteDiario()) {
			String mensaje = "Validación en Movimiento de tipo Debito: El debito excede al monto restante permitido por dia (Disponible para retirar:"+ (EParametros.LIMITEDIARIO.getLimiteDiario()-acumDebitosPorDia) ;
			logger.info(mensaje);
    		throw new DataNotAllowedException(mensaje);
		}
	}
	
	@Transactional
	public Double getTotalRetiroPorDia(Cuenta cuentaActual){
		try {
			String fechaConsulta = UtilDate.getDateStringToday("-");
			return movimientoRepository.getAcumDiarioPorCuenta(fechaConsulta,cuentaActual.getNroCuenta())
			.map((acumulado)-> acumulado)
			.orElse(0.00);
		}catch (Exception e){
			e.printStackTrace();
			String mensaje = "Error capturado On Movimiento(getTotalRetiroPorDia): " + e;
			logger.error(mensaje);
			throw new DataNotAllowedException(mensaje);
		}
	}
	
	@Override
	@Transactional
	public BigDecimal getSaldoPorCuentaCliente(Cuenta cuentaActual) {
		try {
			return movimientoRepository.getSaldoPorCuentaClienteRepo(cuentaActual.getNroCuenta())
			.map((saldo)-> saldo)
			.orElse(cuentaActual.getSaldoInicial());
		}catch (Exception e){
			e.printStackTrace();
			String mensaje = "Error capturado On Movimiento(getSaldoPorCuentaCliente): " + e;
			logger.info(mensaje);
			throw new DataNotAllowedException(mensaje);
		}
	}

	@Override
	public Movimiento guardarMovimiento(MovimientoDTO movimiento) {
		// TODO Auto-generated method stub
		String nroCuenta = movimiento.getNroCuenta();
		ETipoMovimiento eTipoMov = movimiento.getTipo_movimiento();
		BigDecimal bgMontoOperacion = movimiento.getMonto();
		
		Optional<Cuenta> cuentaActual = cuentaRepository.findById(nroCuenta);
		if(cuentaActual.isEmpty()){
			String mensaje = "Error de Validacion en Movimiento: no existe la cuenta: "+nroCuenta ;
			logger.info(mensaje);
			throw new DataNotFoundException(mensaje);
		}
		
		if(cuentaActual.get().getEstado().equals(EEstado.INACTIVO.toString())){
			String mensaje = "Error de Validacion en Movimiento: la cuenta: "+nroCuenta+" esta inactiva.";
			logger.info(mensaje);
			throw new DataNotFoundException(mensaje);
		}
		
		if (eTipoMov.equals(ETipoMovimiento.CREDITO)){
			validarOperacionesCredito(bgMontoOperacion);
		}else {
			if (eTipoMov.equals(ETipoMovimiento.DEBITO)){
				validarOperacionesDebito(bgMontoOperacion, cuentaActual.get());
			}
		}
		BigDecimal bgSaldoCtaActual = this.getSaldoPorCuentaCliente(cuentaActual.get());
		BigDecimal bgSaldoFinal = bgSaldoCtaActual.add(movimiento.getMonto());
		
		Movimiento movAGuardar = modelMapper.map(movimiento, Movimiento.class);		
		movAGuardar.setCuenta(cuentaActual.get());
		movAGuardar.setSaldo(bgSaldoFinal);
		movAGuardar.setDescripcion("Operación de " + eTipoMov + " por " +bgMontoOperacion.toString());
		return movimientoRepository.save(movAGuardar);
	}
	
	@Override
	public void eliminarOReversarMovimiento(Long id) {
		// TODO Auto-generated method stub
		Optional<Movimiento> movimientoActual = movimientoRepository.findById(id);
		if(!movimientoActual.isPresent()){
			String mensaje = "Error de Validacion en Movimiento: no existe el registro con Id: "+ id.toString() ;
			logger.info(mensaje);
			throw new DataNotFoundException(mensaje);
		}
		//Ejecutar el Proceso de reverso/eliminacion del movimeinto
		this.reversarElimnarMovimiento(movimientoActual.get());
	}
	
	public void reversarElimnarMovimiento (Movimiento movActual) {
		
		try {
			BigDecimal bgSaldoCuenta = this.getSaldoPorCuentaCliente(movActual.getCuenta());
			BigDecimal montoAReversar = movActual.getMonto().multiply(BGMENOS1);
			String tipoMov = movActual.getTipo_movimiento();
			Movimiento movAGuardar = new Movimiento();
			movAGuardar.setCuenta(movActual.getCuenta());
			movAGuardar.setFechaMovimiento(UtilDate.getLocalDateToday());
			movAGuardar.setDescripcion("Reverso de Operación  "+ movActual.getId() + " de " + tipoMov + " por " +montoAReversar.toString());
			if (tipoMov.equals(ETipoMovimiento.CREDITO.toString())){
				movAGuardar.setTipo_movimiento(ETipoMovimiento.DEBITO.toString());
				movAGuardar.setMonto(montoAReversar);
				movAGuardar.setSaldo(bgSaldoCuenta.add(montoAReversar));
			}else {
				if (tipoMov.equals(ETipoMovimiento.DEBITO.toString())){
					movAGuardar.setTipo_movimiento(ETipoMovimiento.CREDITO.toString());
					movAGuardar.setMonto(montoAReversar);
					movAGuardar.setSaldo(bgSaldoCuenta.add(montoAReversar));
				}
			}
			movimientoRepository.save(movAGuardar);
		}catch (Exception e){
			e.printStackTrace();
			String mensaje = "Error capturado On Movimiento(getSaldoPorCuentaCliente): " + e;
			logger.error(mensaje);
			throw new DataNotAllowedException(mensaje);
		}
	}
	
	@Override
	public List<ReporteMovimientosDTO> getMovimientosPorCliente(LocalDate fechaDesde, LocalDate fechaHasta, Long idCliente) {
		// TODO Auto-generated method stub
		try {
			List<ReporteMovimientosDTO> listData = movimientoRepository.getRptMovimientosPorCliente(fechaDesde,fechaHasta,idCliente);
			return listData;
		} catch (Exception e) {
			e.printStackTrace();
			String mensaje = "Error capturado On Movimiento(getMovimientosPorCliente): " + e;
			logger.error(mensaje);
			throw new DataNotAllowedException(mensaje);
		}
	}
}
