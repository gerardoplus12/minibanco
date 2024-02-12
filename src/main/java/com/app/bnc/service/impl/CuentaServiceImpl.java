package com.app.bnc.service.impl;
  
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bnc.dto.CuentaDTO;
import com.app.bnc.dto.CuentaDTORequest;
import com.app.bnc.emun.EEstado;
import com.app.bnc.exception.DataNotFoundException;
import com.app.bnc.model.entities.Cliente;
import com.app.bnc.model.entities.Cuenta;
import com.app.bnc.repository.ClienteRepository;
import com.app.bnc.repository.CuentaRepository;
import com.app.bnc.service.CuentaService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CuentaServiceImpl  implements CuentaService{
	
	private static Logger logger = LogManager.getLogger(CuentaServiceImpl.class);
	
	//Inyección de depedencias por constructor
	private ClienteRepository clienteRepository;
	private CuentaRepository cuentaRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Cuenta guardarCuenta(CuentaDTO cuentaDto) {
		// TODO Auto-generated method stub
		Optional<Cliente> clienteOptional = clienteRepository.findById(cuentaDto.getIdCliente());
		if(!clienteOptional.isPresent()) {
			String mensaje = "Error de Validación en Cuenta: No se puede crear la cuenta, el cliente "+cuentaDto.getIdCliente()+" no existe.";
			logger.info(mensaje);
    		throw new DataNotFoundException(mensaje);
		}
		
		Cuenta cuentaAGuardar = modelMapper.map(cuentaDto, Cuenta.class);	
		if(clienteOptional.get().getEstado().equals(EEstado.INACTIVO.toString())){
			String mensaje = "Error de Validación en Cuenta: No se puede crear la cuenta, el cliente "+cuentaDto.getIdCliente()+" esta inactivo.";
			logger.info(mensaje);
    		throw new DataNotFoundException(mensaje);
		}
		cuentaAGuardar.setCliente(clienteOptional.get());
		return cuentaRepository.save(cuentaAGuardar);
	}

	@Override
	public List<Cuenta> getAllCuentas() { 
		// TODO Auto-generated method stub
		return cuentaRepository.findAll();
	}

	@Override
	public Optional<Cuenta> getCuentaById(String nroCuenta) {
		// TODO Auto-generated method stub
		return cuentaRepository.findById(nroCuenta);
	}

	@Override
	public Cuenta actualizarCuenta(CuentaDTORequest cuentaActualizar) {
		// TODO Auto-generated method stub
		Optional<Cuenta> cuentaActual = cuentaRepository.findById(cuentaActualizar.getNroCuenta());
		if(!cuentaActual.isPresent()){
			String mensaje = "Error de Validacion en Cuenta: no existe el registro a actualizar: "+cuentaActualizar.getNroCuenta() ;
			logger.info(mensaje);
			throw new DataNotFoundException(mensaje);
		}
		cuentaActual.get().setSaldoInicial(cuentaActualizar.getSaldoInicial());
		cuentaActual.get().setEstado(cuentaActualizar.getEstado());
		return cuentaRepository.save(cuentaActual.get());
	}

	@Override
	public void eliminarCuenta(String nroCuenta) {
		// TODO Auto-generated method stub
		Optional<Cuenta> cuentaActual = cuentaRepository.findById(nroCuenta);
		if(!cuentaActual.isPresent()){
			String mensaje = "Error de Validacion en Cuenta: no existe el registro a eliminar: "+nroCuenta ;
			logger.info(mensaje);
			throw new DataNotFoundException(mensaje);
		}
		//Eliminacion Logica por campo Estado
		cuentaActual.get().setEstado(EEstado.INACTIVO.toString());
		cuentaRepository.save(cuentaActual.get());
	}
}
