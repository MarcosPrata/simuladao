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
import org.springframework.web.bind.annotation.RestController;

import com.marcosprata.sas.repository.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.marcosprata.sas.models.*;

@RestController
@RequestMapping(value="/api")
@Api(value = "Gerenciamento e CRUD das Questoes")
public class QuestoesController {

	@Autowired
	QuestoesRepository questaoRepository;
	
	@PostMapping("/questoes")
	@ApiOperation(value = "Adiciona uma questao na base.")
	public ResponseEntity<?> salvaQuestao(@RequestBody Questao questaoRecebida) {
		Questao questaoVerificada = questaoRepository.findById(questaoRecebida.getId());
		if (questaoVerificada != null) {
			return new ResponseEntity<>("Questao ja cadastrada.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(questaoRepository.save(questaoRecebida), HttpStatus.OK);
	}
	
	@GetMapping("/questoes/{id_questao}")
	@ApiOperation(value = "Pega uma questao pelo seu ID.")
	public ResponseEntity<?> pegaQuestao(@PathVariable(value="id_questao") long id){
		Questao questao = questaoRepository.findById(id);
		if (questao == null) {
			return new ResponseEntity<>("Questao nao cadastrada.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(questaoRepository.findById(id), HttpStatus.OK);
	}
	
	@GetMapping("/questoes")
	@ApiOperation(value = "Lista todas as questoes.")
	public List<Questao> listaQuestoes(){
		return questaoRepository.findAll();
	}
	
	@PutMapping("/questoes")
	@ApiOperation(value = "Atualiza uma questao.")
	public ResponseEntity<?> atualizaQuestao(@RequestBody Questao questaoRecebida) {
		Questao questaoVerificada = questaoRepository.findById(questaoRecebida.getId());
		if (questaoVerificada == null) {
			return new ResponseEntity<>("Questao nao cadastrada.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(questaoRepository.save(questaoVerificada), HttpStatus.OK);
	}
	
	@DeleteMapping("/questoes")
	@ApiOperation(value = "Deleta uma questao da base.")
	public ResponseEntity<?> deletaQuestao(@RequestBody Questao questaoRecebida) {
		Questao questaoVerificada = questaoRepository.findById(questaoRecebida.getId());
		if (questaoVerificada == null) {
			return new ResponseEntity<>("Questao nao cadastrada.", HttpStatus.NOT_FOUND);
		}
		questaoRepository.delete(questaoVerificada);
		return new ResponseEntity<>("Questao: " + questaoVerificada.getPergunta() + " foi removida.", HttpStatus.OK);
	}	
	
}
