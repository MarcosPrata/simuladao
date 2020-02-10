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
import com.marcosprata.sas.models.Prova;
import com.marcosprata.sas.repository.ProvasRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProvasControllerTest {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private ProvasRepository provasRepository;

	ObjectMapper om = new ObjectMapper();

	Prova provaQueExiste;
	Prova provaQueNaoExiste;

	// Setar ambiente e variaveis de testes -----------------------------
	@Before
	public void set() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		provaQueExiste = provasRepository.save(	new Prova()	);
		provaQueNaoExiste = new Prova();
	}

	@After
	public void tearDown() {
		provasRepository.delete(provaQueExiste);
		provasRepository.delete(provaQueNaoExiste);
	}

	// Adicionar Prova -----------------------------
	@Test
	public void adicionarProva_QueExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(post("/api/provas").content(om.writeValueAsString(provaQueExiste))
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	public void adicionarProva_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(post("/api/provas").content(om.writeValueAsString(provaQueNaoExiste))
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
	}

	// Pegar Prova --------------------------------
	@Test
	public void pegaProva_QueExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(get("/api/provas/" + provaQueExiste.getId()));

		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(om.writeValueAsString(provaQueExiste))).andReturn();
	}

	@Test
	public void pegaProva_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(get("/api/provas/" + (-1)));

		response.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void pegaProvasTest() throws Exception {
		ResultActions response = mockMvc.perform(get("/api/provas").contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
	}

	// Atualizar Prova ----------------------------
	@Test
	public void atualizaProva_QueExiste_Test() throws Exception {

		Prova prova = provasRepository.findById(provaQueExiste.getId());

		prova.setAluno(new Aluno().setNome("Joaozinho"));

		ResultActions response = mockMvc.perform(
				put("/api/provas").content(om.writeValueAsString(prova)).contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(om.writeValueAsString(prova))).andReturn();
	}

	@Test
	public void atualizaProva_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(put("/api/provas").content(om.writeValueAsString(provaQueNaoExiste))
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isNotFound()).andReturn();
	}

	// Deletar Prova ------------------------------
	@Test
	public void deletaProva_QueExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(delete("/api/provas").content(om.writeValueAsString(provaQueExiste))
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void deletaProva_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(delete("/api/provas")
				.content(om.writeValueAsString(provaQueNaoExiste)).contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isNotFound()).andReturn();
	}

}