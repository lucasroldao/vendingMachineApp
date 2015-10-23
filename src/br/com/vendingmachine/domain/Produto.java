package br.com.vendingmachine.domain;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class Produto implements Serializable {
	
	private static final long serialVersionUID = -4147158415237233931L;
	@Expose (serialize = false)
	private Long id;
	@Expose (serialize = false)
	private String codigo;
	@Expose (serialize = false)
	private String descricao;
	@Expose (serialize = false)
	private Double valorUnitario;
	@Expose (serialize = false)
	private Double precoVenda;
	@Expose(deserialize = false,serialize = false)
	private Integer quantidade;
	@Expose(deserialize = false)
	private Double valorTotal;
	@Expose(deserialize = false,serialize = false)
	private Double valorRetirado;
	
	public Double getValorRetirado() {
		return valorRetirado;
	}
	public void setValorRetirado(Double valorRetirado) {
		this.valorRetirado = valorRetirado;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public Double getPrecoVenda() {
		return precoVenda;
	}
	public void setPrecoVenda(Double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	@Override
	public String toString() {
		return descricao;
	}
    
	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
}
