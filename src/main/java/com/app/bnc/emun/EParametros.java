package com.app.bnc.emun;

public enum EParametros{
	LIMITEDIARIO(1000);
	private double limiteDiario;
    private EParametros(double valor){
    	limiteDiario = valor;
    }
    public double getLimiteDiario() {
        return limiteDiario;
    }
} 