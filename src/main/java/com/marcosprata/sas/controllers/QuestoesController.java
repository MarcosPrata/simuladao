package com.marcosprata.sas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcosprata.sas.repository.QuestoesRepository;

import io.swagger.annotations.ApiOperation;

import com.marcosprata.sas.models.*;

@RestController
@RequestMapping(value="/api")
public class QuestoesController {

	@Autowired
	QuestoesRepository questaoRepository;
	
	@PostMapping("/questoes")
	@ApiOperation(value = "Adiciona uma questao na base.")
	public Questao salvaQuestao(@RequestBody Questao questao) {
		return questaoRepository.save(questao);
	}
	
	@GetMapping("/questoes/{id_questao}")
	@ApiOperation(value = "Pega uma questao pelo seu ID.")
	public Questao pegaQuestao(@PathVariable(value="id_questao") long id){
		return questaoRepository.findById(id);
	}
	
	@GetMapping("/questoes")
	@ApiOperation(value = "Lista todas as questoes.")
	public List<Questao> listaQuestoes(){
		return questaoRepository.findAll();
	}
	
	@PutMapping("/questoes")
	@ApiOperation(value = "Altera uma questao.")
	public Questao atualizaQuestao(@RequestBody Questao questao) {
		return questaoRepository.save(questao);
	}
	
	@DeleteMapping("/questoes")
	@ApiOperation(value = "Deleta uma questao da base.")
	public void deletaQuestao(@RequestBody Questao questao) {
		questaoRepository.delete(questao);
	}	
	
}
