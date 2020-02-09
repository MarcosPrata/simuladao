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

import com.marcosprata.sas.repository.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.marcosprata.sas.models.*;

@RestController
@RequestMapping(value="/api")
@Api(value = "Gerenciamento e CRUD dos Alunos")
public class AlunosController {

	@Autowired
	AlunosRepository alunosRepository;
	@Autowired
	ProvasRepository provasRepository;
	
	
	@PostMapping("/alunos")
	@ApiOperation(value = "Adiciona um aluno na base.")
	public Aluno salvaAluno(@RequestBody Aluno aluno) {
		return alunosRepository.save(aluno);
	}
	
	@GetMapping("alunos/{id_aluno}")
	@ApiOperation(value = "Pega um aluno pelo seu ID")
	public Aluno pegaAluno(@PathVariable(value="id_aluno") long id){
		return alunosRepository.findById(id);
	}
	
	@GetMapping("/alunos")
	@ApiOperation(value = "Lista todos os Alunos")
	public List<Aluno> listaAlunos(){
		return alunosRepository.findAll();
	}
		
	@PutMapping("/alunos")
	@ApiOperation(value = "Altera um Aluno")
	public Aluno atualizaAluno(@RequestBody Aluno aluno) {
		return alunosRepository.save(aluno);
	}
	
	@DeleteMapping("alunos")
	@ApiOperation(value = "Deleta um aluno da base.")
	public void deletaAluno(@RequestBody Aluno aluno) {
		alunosRepository.delete(aluno);
	}
	
	@GetMapping("alunos/{id_aluno}/provas")
	@ApiOperation(value = "Pega todas as provas realizadas por um Aluno")
	public List<Prova> pegaProvas(@PathVariable(value="id_aluno") long id){
		Aluno aluno = alunosRepository.findById(id);
		List<Prova> provas = provasRepository.findByAluno(aluno);
		return provas;
	}
	
}
