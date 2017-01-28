package br.com.emersonluiz.service;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.emersonluiz.dto.ContaDTO;
import br.com.emersonluiz.exception.FailureException;
import br.com.emersonluiz.model.Conta;
import br.com.emersonluiz.repository.ContaRepository;

@Named
public class ContaService {
	
	private ContaRepository contaRepository;

	@Inject
	public ContaService(ContaRepository contaRepository) {
		this.contaRepository = contaRepository;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public ContaDTO transferencia(ContaDTO contaDTO) throws FailureException {
		
		Conta contaOrigem = getConta(contaDTO.getAgenciaOrigem(), contaDTO.getNumeroOrigem(), "Origem");
		Conta contaDestino = getConta(contaDTO.getAgenciaDestino(), contaDTO.getNumeroDestino(), "Destino");
		
		if (verificarSaldo(contaOrigem, contaDTO.getValor())) {
			contaDTO.setContaOrigem((debitar(contaOrigem, contaDTO.getValor())));
		} else {
			throw new FailureException("Saldo insuficiente.");
		}
		
		contaDTO.setContaDestino((creditar(contaDestino, contaDTO.getValor())));
		return contaDTO;
	}
	
	private Conta creditar(Conta conta, double valor) {
		conta.setValor((conta.getValor() + valor));
		contaRepository.save(conta);
		return conta;
	}
	
	private Conta debitar(Conta conta, double valor) {
		conta.setValor((conta.getValor() - valor));
		contaRepository.save(conta);
		return conta;
	}
	
	private Conta getConta(long agencia, long numeroConta, String tipo) throws FailureException {
		Conta conta = contaRepository.findByAgenciaAndNumero(agencia, numeroConta);
		
		if (conta == null) {
			throw new FailureException("Agencia " + tipo + ": " + agencia + " e/ou Conta " + tipo + ": " + numeroConta + " estão incorretas.");
		}
		
		return conta;
	}
	
	private boolean verificarSaldo(Conta conta, double valor) {
		if (conta.getValor() >= valor) {
			return true;
		}
		return false;
	}

}
