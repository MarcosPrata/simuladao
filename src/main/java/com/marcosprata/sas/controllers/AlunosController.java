package com.marcosprata.sas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(value = "/api")
@Api(value = "Gerenciamento e CRUD dos Alunos")
public class AlunosController {

	@Autowired
	AlunosRepository alunosRepository;
	@Autowired
	ProvasRepository provasRepository;

	@PostMapping("/alunos")
	@ApiOperation(value = "Adiciona um aluno na base.")
	public ResponseEntity<?> salvaAluno(@RequestBody Aluno alunoRecebido) {
		Aluno alunoVerificado = alunosRepository.findById(alunoRecebido.getId());
		if (alunoVerificado != null) {
			return new ResponseEntity<>("Aluno ja cadastrado.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(alunosRepository.save(alunoRecebido), HttpStatus.OK);
	}

	@GetMapping("alunos/{id_aluno}")
	@ApiOperation(value = "Pega um aluno pelo seu ID")
	public ResponseEntity<?> pegaAluno(@PathVariable(value = "id_aluno") long id) {
		Aluno aluno = alunosRepository.findById(id);
		if (aluno == null) {
			return new ResponseEntity<>("Aluno nao cadastrado.", HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(aluno, HttpStatus.OK);
		}
	}

	@GetMapping("/alunos")
	@ApiOperation(value = "Lista todos os Alunos")
	public List<Aluno> listaAlunos() {
		return alunosRepository.findAll();
	}

	
	@ApiOperation(value = "Atualiza um Aluno")
	@PutMapping(value = "/alunos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> atualizaAluno(@RequestBody Aluno alunoRecebido) {
		Aluno alunoVerificado = alunosRepository.findById(alunoRecebido.getId());
		if (alunoVerificado == null) {
			return new ResponseEntity<>("Aluno nao cadastrado.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(alunosRepository.save(alunoRecebido), HttpStatus.OK);
	}

	@DeleteMapping("alunos")
	@ApiOperation(value = "Deleta um aluno da base.")
	public ResponseEntity<?> deletaAluno(@RequestBody Aluno alunoRecebido) {
		Aluno alunoVerificado = alunosRepository.findById(alunoRecebido.getId());
		if (alunoVerificado == null) {
			return new ResponseEntity<>("Aluno nao cadastrado.", HttpStatus.NOT_FOUND);
		}
		alunosRepository.delete(alunoVerificado);
		return new ResponseEntity<>(alunoVerificado.getNome() + " foi removido(a).", HttpStatus.OK);
	}

	@GetMapping("alunos/{id_aluno}/provas")
	@ApiOperation(value = "Pega todas as provas realizadas por um Aluno")
	public List<Prova> pegaProvas(@PathVariable(value = "id_aluno") long id) {
		Aluno aluno = alunosRepository.findById(id);
		List<Prova> provas = provasRepository.findByAluno(aluno);
		return provas;
	}

}
