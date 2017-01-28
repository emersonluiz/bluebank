package br.com.emersonluiz.repository;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.emersonluiz.model.Conta;

@Named
public interface ContaRepository extends JpaRepository<Conta, Long> {

	Conta findByAgenciaAndNumero(long agencia, long numeroConta);
	
}
