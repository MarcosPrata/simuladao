package com.marcosprata.sas.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcosprata.sas.models.Aluno;
import com.marcosprata.sas.models.Prova;
import com.marcosprata.sas.models.Questao;
import com.marcosprata.sas.models.Simulado;
import com.marcosprata.sas.repository.AlunosRepository;
import com.marcosprata.sas.repository.ProvasRepository;
import com.marcosprata.sas.repository.QuestoesRepository;
import com.marcosprata.sas.repository.SimuladosRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
public class IniciarDados {
	@Autowired
	AlunosRepository alunosRepository;
	@Autowired
	ProvasRepository provasRepository;
	@Autowired
	QuestoesRepository questoesRepository;
	@Autowired
	SimuladosRepository simuladosRepository;

	@GetMapping("/iniciar")
	@ApiOperation(value = "Este metodo insere alguns dados aleatorios como provas, alunos, questoes e simulados.")
	public String iniciar(){
		// ALUNOS -------------
		List<Aluno> alunos = new ArrayList<Aluno>();
		for (int i = 1; i <= 20; i++) {
			Aluno aluno = new Aluno();
			aluno.setNome((i % 2 == 0) ? ("Joaozinho " + i) : ("Mariazinha " + i));
			alunos.add(aluno);
		}
		alunosRepository.saveAll(alunos);

		// QUESTOES -----------
		String data = new Date().toString();
		List<Questao> questoes = new ArrayList<Questao>();
		for (int i = 1; i <= 10; i++) {
			Questao questao = new Questao();
			questao.setPergunta("Pergunta Aleatória número " + i + " criada em " + data);

			questao.setNivel((i <= 3) ? "FACIL" : ((i > 3) && (i <= 7)) ? "MEDIO" : "DIFICIL");

			questao.setAlternativa_A("Alternativa A");
			questao.setAlternativa_B("Alternativa B");
			questao.setAlternativa_C("Alternativa C");
			questao.setAlternativa_D("Alternativa D");
			questao.setAlternativa_E("Alternativa E");

			int min = 1;
			int max = 6;
			int r = (int) (Math.random() * ((max - min) + 1)) + min;

			questao.setAlternativaCorreta((r == 1) ? "A" : (r == 2) ? "B" : (r == 3) ? "C" : (r == 4) ? "D" : "E");

			questoes.add(questao);
		}
		questoesRepository.saveAll(questoes);

		// SIMULADOS ---------
		Simulado simulado = new Simulado();
		simulado.setTitulo("Simulado criado em " + new Date().toString());
		simulado.setQuestoes(questoes);

		// PROVAS ------------
		for (int i = 0; i < 10; i++) {
			Prova prova = new Prova();

			Random rand = new Random();
			prova.setAluno(alunos.get(rand.nextInt(alunos.size())));
			prova.setSimulado(simulado);
			List<String> gabarito = new ArrayList<String>();
			for (int j = 1; j <= 10; j++) {
				int min = 1;
				int max = 6;
				int r = (int) (Math.random() * ((max - min) + 1)) + min;
				gabarito.add((r == 1) ? "A" : (r == 2) ? "B" : (r == 3) ? "C" : (r == 4) ? "D" : "E");
			}

			prova.setGabarito(gabarito);
			provasRepository.save(prova);
		}
		return "Dados Inseridos...";
	}
}
