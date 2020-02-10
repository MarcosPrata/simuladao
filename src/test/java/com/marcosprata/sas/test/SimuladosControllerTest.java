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
import com.marcosprata.sas.models.Simulado;
import com.marcosprata.sas.repository.SimuladosRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SimuladosControllerTest {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private SimuladosRepository simuladosRepository;

	ObjectMapper om = new ObjectMapper();

	Simulado simuladoQueExiste;
	Simulado simuladoQueNaoExiste;

	// Setar ambiente e variaveis de testes -----------------------------
	@Before
	public void set() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		simuladoQueExiste = simuladosRepository.save(
			new Simulado().setTitulo("Simulado 1")
		);
		simuladoQueNaoExiste = new Simulado().setTitulo("Simulado 2");
	}

	@After
	public void tearDown() {
		simuladosRepository.delete(simuladoQueExiste);
		simuladosRepository.delete(simuladoQueNaoExiste);
	}

	// Adicionar Simulado -----------------------------
	@Test
	public void adicionarSimulado_QueExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(post("/api/simulados").content(om.writeValueAsString(simuladoQueExiste))
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	public void adicionarSimulado_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(post("/api/simulados").content(om.writeValueAsString(simuladoQueNaoExiste))
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
	}

	// Pegar Simulado --------------------------------
	@Test
	public void pegaSimulado_QueExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(get("/api/simulados/" + simuladoQueExiste.getId()));

		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(om.writeValueAsString(simuladoQueExiste))).andReturn();
	}

	@Test
	public void pegaSimulado_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(get("/api/simulados/" + (-1)));

		response.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void pegaSimuladosTest() throws Exception {
		ResultActions response = mockMvc.perform(get("/api/simulados").contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
	}

	// Atualizar Simulado ----------------------------
	@Test
	public void atualizaSimulado_QueExiste_Test() throws Exception {

		Simulado aluno = simuladosRepository.findById(simuladoQueExiste.getId());

		aluno.setTitulo("Simulado 1");

		ResultActions response = mockMvc.perform(
				put("/api/simulados").content(om.writeValueAsString(aluno)).contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(om.writeValueAsString(aluno))).andReturn();
	}

	@Test
	public void atualizaSimulado_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(put("/api/simulados").content(om.writeValueAsString(simuladoQueNaoExiste))
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isNotFound()).andReturn();
	}

	// Deletar Simulado ------------------------------
	@Test
	public void deletaSimulado_QueExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(delete("/api/simulados").content(om.writeValueAsString(simuladoQueExiste))
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void deletaSimulado_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(delete("/api/simulados")
				.content(om.writeValueAsString(simuladoQueNaoExiste)).contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isNotFound()).andReturn();
	}

}