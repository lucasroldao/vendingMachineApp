package br.com.vendingmachine.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Maquina implements Serializable {

	private static final long serialVersionUID = -4667181014529260036L;
	private Long id;
	private String codigo;
	private String modelo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	@Override
	public String toString() {
		return modelo;
	}
}
