package com.marcosprata.sas.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcosprata.sas.models.Aluno;
import com.marcosprata.sas.repository.AlunosRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AlunosControllerTest {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private AlunosRepository alunosRepository;

	ObjectMapper om = new ObjectMapper();

	Aluno alunoQueExiste;
	Aluno alunoQueNaoExiste;

	//Setar ambiente e variaveis de testes -----------------------------
	@Before
	public void set() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		alunoQueExiste = alunosRepository.save(new Aluno().setNome("Aluno de teste 1"));
		alunoQueNaoExiste = new Aluno().setNome("Aluno de teste 2");
	}
	
	@After
	public void tearDown() {
		alunosRepository.delete(alunoQueExiste);
		alunosRepository.delete(alunoQueNaoExiste);
	}
	
	//Adicionar Aluno-----------------------------
	@Test
	public void adicionarAluno_QueExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(post("/api/alunos")
				.content(om.writeValueAsString(alunoQueExiste))
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isBadRequest()).andReturn();
	}
	
	@Test
	public void adicionarAluno_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(post("/api/alunos")
				.content(om.writeValueAsString(alunoQueNaoExiste))
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andReturn();
	}
	
	//Pegar Aluno --------------------------------
	@Test
	public void pegaAluno_QueExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(get("/api/alunos/"+alunoQueExiste.getId()));
		
		response.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().string(om.writeValueAsString(alunoQueExiste))).andReturn();
	}
	
	@Test
	public void pegaAluno_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(get("/api/alunos/" + (-1)));

		response.andExpect(status().isNotFound()).andReturn();
	}
	
	@Test
	public void pegaAlunosTest() throws Exception {
		ResultActions response = mockMvc.perform(get("/api/alunos").contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
	}
	
	//Atualizar Aluno ----------------------------
	@Test
	public void atualizaAluno_QueExiste_Test() throws Exception {
		
		Aluno aluno = alunosRepository.findById(alunoQueExiste.getId());

		aluno.setNome("Aluno com nome Alterado");

		ResultActions response = mockMvc.perform(
				put("/api/alunos").content(om.writeValueAsString(aluno)).contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(om.writeValueAsString(aluno))).andReturn();
	}
	
	@Test
	public void atualizaAluno_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(
				put("/api/alunos").content(om.writeValueAsString(alunoQueNaoExiste)).contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isNotFound()).andReturn();
	}
	
	//Deletar Aluno ------------------------------
	@Test
	public void deletaAluno_QueExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(
				delete("/api/alunos").content(om.writeValueAsString(alunoQueExiste)).contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void deletaAluno_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(
				delete("/api/alunos").content(om.writeValueAsString(alunoQueNaoExiste)).contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isNotFound()).andReturn();
	}

}