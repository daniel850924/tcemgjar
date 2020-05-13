package br.gov.mg.tcemg.util;

public class Parametro {

	private Object object;
	private boolean paramEntrada;
	
	public Parametro(Object object, boolean paramEntrada) {
		super();
		this.object = object;
		this.paramEntrada = paramEntrada;
	}

	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public boolean isParamEntrada() {
		return paramEntrada;
	}
	public void setParamEntrada(boolean paramEntrada) {
		this.paramEntrada = paramEntrada;
	}
	
}
