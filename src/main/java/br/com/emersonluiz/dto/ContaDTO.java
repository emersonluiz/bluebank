package br.com.emersonluiz.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.emersonluiz.model.Conta;

public class ContaDTO implements Serializable {
	
	private static final long serialVersionUID = 543819667793936546L;

	private long agenciaOrigem;
	
	private long numeroOrigem;
	
	private long agenciaDestino;
	
	private long numeroDestino;
	
	private double valor;

	@JsonIgnore
	private Conta contaOrigem;

	@JsonIgnore
	private Conta contaDestino;

	public long getAgenciaOrigem() {
		return agenciaOrigem;
	}

	public void setAgenciaOrigem(long agenciaOrigem) {
		this.agenciaOrigem = agenciaOrigem;
	}

	public long getNumeroOrigem() {
		return numeroOrigem;
	}

	public void setNumeroOrigem(long numeroOrigem) {
		this.numeroOrigem = numeroOrigem;
	}

	public long getAgenciaDestino() {
		return agenciaDestino;
	}

	public void setAgenciaDestino(long agenciaDestino) {
		this.agenciaDestino = agenciaDestino;
	}

	public long getNumeroDestino() {
		return numeroDestino;
	}

	public void setNumeroDestino(long numeroDestino) {
		this.numeroDestino = numeroDestino;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Conta getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public Conta getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}
}
