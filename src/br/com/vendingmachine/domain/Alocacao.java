package br.com.vendingmachine.domain;

import java.io.Serializable;

public class Alocacao implements Serializable {
	
	private static final long serialVersionUID = 3311966813128540473L;
	private Long alocacaoId;
	private String dataSolicitacao;
	private Maquina maquina;
	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Maquina getMaquina() {
		return maquina;
	}
	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}

	public Long getAlocacaoId() {
		return alocacaoId;
	}
	public void setAlocacaoId(Long alocacaoId) {
		this.alocacaoId = alocacaoId;
	}
	public String getDataSolicitacao() {
		return dataSolicitacao;
	}
	public void setDataSolicitacao(String dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return maquina.toString();
	}
	
	public long getid() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
