package com.marcosprata.sas.controllers;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcosprata.sas.repository.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.marcosprata.sas.models.*;

@RestController
@RequestMapping(value = "/api")
@Api(value = "Gerenciamento e CRUD das Provas")
public class ProvasController {

	@Autowired
	ProvasRepository provasRepository;

	@PostMapping("/provas")
	@ApiOperation(value = "Adiciona uma prova na base.")
	public ResponseEntity<?> salvaProva(@RequestBody Prova provaRecebida) {
		Prova provaVerificada = provasRepository.findById(provaRecebida.getId());
		if (provaVerificada != null) {
			return new ResponseEntity<>("Prova ja cadastrada.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(provasRepository.save(provaRecebida.calculaNota()), HttpStatus.OK);
	}
	
	@GetMapping("/provas/{id_prova}")
	@ApiOperation(value = "Pega uma prova pelo seu ID")
	public ResponseEntity<?> pegaProva(@PathVariable(value = "id_prova") long id) {
		Prova provaVerificada = provasRepository.findById(id);
		if (provaVerificada == null) {
			return new ResponseEntity<>("Prova nao cadastrada.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(provasRepository.findById(id).calculaNota(), HttpStatus.OK);
	}
	
	@GetMapping("/provas")
	@ApiOperation(value = "Lista todas as Provas")
	public List<Prova> listaProvas() {
		return provasRepository.findAll();
	}
	
	@PutMapping("/provas")
	@ApiOperation(value = "Altera uma Prova")
	public ResponseEntity<?> atualizaProva(@RequestBody Prova provaRecebida) {
		Prova provaVerificada = provasRepository.findById(provaRecebida.getId());
		if (provaVerificada == null) {
			return new ResponseEntity<>("Prova nao cadastrada.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(provasRepository.save(provaRecebida.calculaNota()), HttpStatus.OK);
	}
	
	@DeleteMapping("/provas")
	@ApiOperation(value = "Deleta uma prova da base.")
	public ResponseEntity<?> deletaProva(@RequestBody Prova provaRecebida) {
		Prova provaVerificada = provasRepository.findById(provaRecebida.getId());
		if (provaVerificada == null) {
			return new ResponseEntity<>("Prova nao cadastrada.", HttpStatus.NOT_FOUND);
		}
		provasRepository.delete(provaVerificada);
		return new ResponseEntity<>("Prova " + provaRecebida.getId() + " foi removido(a).", HttpStatus.OK);
		
	}
	
	@PutMapping("/provas/{id_prova}/responderQuestao")
	@ApiOperation(value = "Recebe o ID de uma prova, o numero da questao e a alternativa escolhida pelo aluno para preencher o Gabarito.")
	public ResponseEntity<?> responderQuestao(@PathVariable(value = "id_prova") long id, @RequestParam(value = "questao") int questao, @RequestParam(value = "resposta") String resposta) {
		questao--;
		Prova prova = provasRepository.findById(id);		
		if( ((questao>=0)&&(questao<=10))  ) {
			prova.getGabarito().set(questao, resposta);
			provasRepository.save(prova.calculaNota());
			return new ResponseEntity<>(prova, HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Parametros fora do esperado.",HttpStatus.BAD_REQUEST);
		}
		
	}

}
