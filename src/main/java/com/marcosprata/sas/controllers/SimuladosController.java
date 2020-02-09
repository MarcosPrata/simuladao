package com.marcosprata.sas.controllers;

import java.util.Collections;
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
	public Simulado salvaQuestao(@RequestBody Simulado simulado) {
		return simuladosRepository.save(simulado);
	}
	
	@GetMapping("/simulados/{id_simulado}")
	@ApiOperation(value = "Pega um simulado pelo seu ID.")
	public Simulado pegaSimulado(@PathVariable(value = "id_simulado") long id) {
		return simuladosRepository.findById(id);
	}
	
	@GetMapping("/simulados")
	@ApiOperation(value = "Lista todos os simulados.")
	public List<Simulado> listaSimulados() {
		return simuladosRepository.findAll();
	}

	@PutMapping("/simulados")
	@ApiOperation(value = "Altera um simulado.")
	public Simulado atualizaSimulado(@RequestBody Simulado simulado) {
		return simuladosRepository.save(simulado);
	}
	
	@DeleteMapping("/simulados")
	@ApiOperation(value = "Deleta um simulado da base.")
	public void deletaSimulado(@RequestBody Simulado simulado) {
		simuladosRepository.delete(simulado);
	}

	@GetMapping("/simulados/{id_simulado}/ranking")
	@ApiOperation(value = "Traz uma lista com as provas realizadas nesse simulado com ordem crescente"
			+ "com base na nota obtida pelo Aluno.")
	public List<Prova> ranking(@PathVariable(value = "id_simulado") long id) {
		Simulado simulado = simuladosRepository.findById(id);
		List<Prova> provas = provasRepository.findBySimulado(simulado);
		for (Prova prova : provas) { prova.calculaNota(); }
		Collections.sort(provas);
		return provas;
	}

}
