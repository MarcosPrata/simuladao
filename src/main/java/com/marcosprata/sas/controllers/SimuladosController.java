package com.marcosprata.sas.controllers;

import java.util.Collections;
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

import com.marcosprata.sas.repository.ProvasRepository;
import com.marcosprata.sas.repository.SimuladosRepository;

import io.swagger.annotations.ApiOperation;

import com.marcosprata.sas.models.*;

@RestController
@RequestMapping(value = "/api")
public class SimuladosController {

	@Autowired
	SimuladosRepository simuladosRepository;
	@Autowired
	ProvasRepository provasRepository;

	
	@PostMapping("/simulados")
	@ApiOperation(value = "Adiciona um simulado na base.")
	public ResponseEntity<?> salvaQuestao(@RequestBody Simulado simuladoRecebido) {
		Simulado simuladoVerificado = simuladosRepository.findById(simuladoRecebido.getId());
		if (simuladoVerificado != null) {
			return new ResponseEntity<>("Simulado ja cadastrado.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(simuladosRepository.save(simuladoVerificado), HttpStatus.OK);
	}
	
	@GetMapping("/simulados/{id_simulado}")
	@ApiOperation(value = "Pega um simulado pelo seu ID.")
	public ResponseEntity<?> pegaSimulado(@PathVariable(value = "id_simulado") long id) {
		Simulado simulado = simuladosRepository.findById(id);
		if (simulado == null) {
			return new ResponseEntity<>("Simulado nao cadastrado.", HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(simulado, HttpStatus.OK);
		}
	}
	
	@GetMapping("/simulados")
	@ApiOperation(value = "Lista todos os simulados.")
	public List<Simulado> listaSimulados() {
		return simuladosRepository.findAll();
	}

	@PutMapping("/simulados")
	@ApiOperation(value = "Altera um simulado.")
	public ResponseEntity<?> atualizaSimulado(@RequestBody Simulado simuladoRecebido) {
		Simulado simuladoVerificado = simuladosRepository.findById(simuladoRecebido.getId());
		if (simuladoVerificado == null) {
			return new ResponseEntity<>("Simulado nao cadastrado.", HttpStatus.NOT_FOUND);
		}else 
			return new ResponseEntity<>(simuladosRepository.save(simuladoRecebido), HttpStatus.OK);
	}
	
	@DeleteMapping("/simulados")
	@ApiOperation(value = "Deleta um simulado da base.")
	public ResponseEntity<?> deletaSimulado(@RequestBody Simulado simuladoRecebido) {
		Simulado simuladoVerificado = simuladosRepository.findById(simuladoRecebido.getId());
		if (simuladoVerificado == null) {
			return new ResponseEntity<>("Simulado nao cadastrado.", HttpStatus.NOT_FOUND);
		}
		simuladosRepository.delete(simuladoVerificado);
		return new ResponseEntity<>(simuladoVerificado.getTitulo() + " foi removido(a).", HttpStatus.OK);
	}

	@GetMapping("/simulados/{id_simulado}/ranking")
	@ApiOperation(value = "Traz uma ranking com as 5 provas de melhores resultados em um simulado.")
	public List<Prova> ranking(@PathVariable(value = "id_simulado") long id) {
		Simulado simulado = simuladosRepository.findById(id);
		List<Prova> provas = provasRepository.findBySimulado(simulado);
		for (Prova prova : provas) { prova.calculaNota(); }
		Collections.sort(provas);
		return provas.subList(0, 5);
	}

}
