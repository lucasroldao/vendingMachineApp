package br.com.vendingmachine.domain;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 4322779589251568019L;
	@Expose
	private Long id;
	@Expose
	private String nomeFantasia;
	private String telefone;
	private String logradouro;
	private Long numero;
	
	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return nomeFantasia;
	}
}

