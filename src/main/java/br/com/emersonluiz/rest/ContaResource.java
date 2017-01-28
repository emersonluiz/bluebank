package br.com.emersonluiz.rest;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.emersonluiz.dto.ContaDTO;
import br.com.emersonluiz.exception.FailureException;
import br.com.emersonluiz.exception.FailureReason;
import br.com.emersonluiz.service.ContaService;

@RestController
@RequestMapping("/conta")
public class ContaResource {
	
	@Inject
	private ContaService contaService;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Serializable> transferencia(@RequestBody ContaDTO contaDTO) {
		try {
			contaService.transferencia(contaDTO);
			return ResponseEntity.noContent().build();
		} catch(FailureException fe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FailureReason(fe.getMessage()));			
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FailureException("Erro no servidor"));
		}
	}
}
