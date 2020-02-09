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

import com.marcosprata.sas.repository.ProvasRepository;

import io.swagger.annotations.ApiOperation;

import com.marcosprata.sas.models.*;

@RestController
@RequestMapping(value = "/api")
public class ProvasController {

	@Autowired
	ProvasRepository provasRepository;

	@PostMapping("/provas")
	@ApiOperation(value = "Adiciona uma prova na base.")
	public Prova salvaProva(@RequestBody Prova prova) {
		return provasRepository.save(prova.calculaNota());
	}
	
	@GetMapping("/provas/{id_prova}")
	@ApiOperation(value = "Pega uma prova pelo seu ID")
	public Prova pegaProva(@PathVariable(value = "id_prova") long id) {
		return provasRepository.findById(id).calculaNota();
	}
	
	@GetMapping("/provas")
	@ApiOperation(value = "Lista todas as Provas")
	public List<Prova> listaProvas() {
		return provasRepository.findAll();
	}
	
	@PutMapping("/provas")
	@ApiOperation(value = "Altera uma Prova")
	public Prova atualizaProva(@RequestBody Prova prova) {
		return provasRepository.save(prova);
	}
	
	@DeleteMapping("/provas")
	@ApiOperation(value = "Deleta uma prova da base.")
	public void deletaProva(@RequestBody Prova prova) {
		provasRepository.delete(prova);
	}
	
	@PutMapping("/provas/{id_prova}/responderQuestao")
	@ApiOperation(value = "Recebe o ID de uma prova, o numero da questao e a alternativa escolhida pelo aluno para preencher o Gabarito.")
	public ResponseEntity<?> pegaProva(@PathVariable(value = "id_prova") long id, @RequestParam(value = "questao") int questao, @RequestParam(value = "resposta") String resposta) {
		questao--;
		Prova prova = provasRepository.findById(id);		
		if( ((questao>=0)&&(questao<=10))  ) {
			prova.getGabarito().set(questao, resposta);
			provasRepository.save(prova.calculaNota());
			return new ResponseEntity<>(prova, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}

}
