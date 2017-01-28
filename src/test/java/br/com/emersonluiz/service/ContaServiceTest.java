package br.com.emersonluiz.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.emersonluiz.dto.ContaDTO;
import br.com.emersonluiz.exception.FailureException;
import br.com.emersonluiz.model.Conta;
import br.com.emersonluiz.repository.ContaRepository;

public class ContaServiceTest {
	
	private static ContaRepository contaRepository;
    private static ContaService contaService;
    
    private static ContaDTO contaDTO;
    private static Conta contaOrgiem;
    private static Conta contaDestino;

    @BeforeClass
    public static void start(){
        contaRepository = mock(ContaRepository.class);
        contaService = new ContaService(contaRepository);
    }
    
    @Before
    public void init() {
    	contaDTO = new ContaDTO();
    	contaDTO.setAgenciaOrigem(123);
    	contaDTO.setNumeroOrigem(123);
    	contaDTO.setAgenciaDestino(123);
    	contaDTO.setNumeroDestino(456);
    	contaDTO.setValor(123);
    	
    	contaOrgiem = new Conta();
    	contaOrgiem.setId(1);
    	contaOrgiem.setAgencia(123);
    	contaOrgiem.setNumero(123);
    	contaOrgiem.setValor(123);
    	
    	contaDestino = new Conta();
    	contaDestino.setId(2);
    	contaDestino.setAgencia(123);
    	contaDestino.setNumero(456);
    	contaDestino.setValor(123);
    }
    
    @Test
    public void transferirSucesso() throws Exception {
    	when(contaRepository.findByAgenciaAndNumero(123, 123)).thenReturn(contaOrgiem);
    	when(contaRepository.findByAgenciaAndNumero(123, 456)).thenReturn(contaDestino);
    	
    	ContaDTO ret = contaService.transferencia(contaDTO);
    	assertEquals(246, ret.getContaDestino().getValor(), 0);
    	assertEquals(0, ret.getContaOrigem().getValor(), 0);
    }
    
    @Test(expected=FailureException.class)
    public void transferirContaOrigemNaoValida() throws Exception {
    	contaDTO.setNumeroOrigem(122);

    	when(contaRepository.findByAgenciaAndNumero(123, 122)).thenReturn(null);

    	contaService.transferencia(contaDTO);
    }
    
    @Test(expected=FailureException.class)
    public void transferirContaDestinoNaoValida() throws Exception {
    	contaDTO.setNumeroDestino(452);

    	when(contaRepository.findByAgenciaAndNumero(123, 123)).thenReturn(contaOrgiem);
    	when(contaRepository.findByAgenciaAndNumero(123, 452)).thenReturn(null);
    	
    	contaService.transferencia(contaDTO);
    }
    
    @Test(expected=FailureException.class)
    public void transferirSaldoInsuficiente() throws Exception {
    	contaDTO.setValor(500);

    	when(contaRepository.findByAgenciaAndNumero(123, 123)).thenReturn(contaOrgiem);
    	when(contaRepository.findByAgenciaAndNumero(123, 456)).thenReturn(contaDestino);
    	
    	contaService.transferencia(contaDTO);
    }

}
