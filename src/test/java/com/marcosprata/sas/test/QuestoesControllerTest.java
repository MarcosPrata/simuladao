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
import com.marcosprata.sas.models.Questao;
import com.marcosprata.sas.repository.QuestoesRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class QuestoesControllerTest {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private QuestoesRepository questoesRepository;

	ObjectMapper om = new ObjectMapper();

	Questao questaoQueExiste;
	Questao questaoQueNaoExiste;

	// Setar ambiente e variaveis de testes -----------------------------
	@Before
	public void set() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		questaoQueExiste = questoesRepository.save(
			new Questao().setNivel("FACIL").setPergunta("Pergunta 1")
			.setAlternativa_A("A").setAlternativa_B("B").setAlternativa_C("C").setAlternativa_D("D").setAlternativa_E("E")
			.setAlternativaCorreta("A")
		);
		questaoQueNaoExiste = new Questao().setNivel("FACIL").setPergunta("Pergunta 2")
				.setAlternativa_A("A").setAlternativa_B("B").setAlternativa_C("C").setAlternativa_D("D").setAlternativa_E("E")
				.setAlternativaCorreta("A");
	}

	@After
	public void tearDown() {
		questoesRepository.delete(questaoQueExiste);
		questoesRepository.delete(questaoQueNaoExiste);
	}

	// Adicionar Questao -----------------------------
	@Test
	public void adicionarQuestao_QueExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(post("/api/questoes").content(om.writeValueAsString(questaoQueExiste))
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	public void adicionarQuestao_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(post("/api/questoes").content(om.writeValueAsString(questaoQueNaoExiste))
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
	}

	// Pegar Questao --------------------------------
	@Test
	public void pegaQuestao_QueExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(get("/api/questoes/" + questaoQueExiste.getId()));

		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(om.writeValueAsString(questaoQueExiste))).andReturn();
	}

	@Test
	public void pegaQuestao_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(get("/api/questoes/" + (-1)));

		response.andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void pegaQuestaosTest() throws Exception {
		ResultActions response = mockMvc.perform(get("/api/questoes").contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
	}

	// Atualizar Questao ----------------------------
	@Test
	public void atualizaQuestao_QueExiste_Test() throws Exception {

		Questao aluno = questoesRepository.findById(questaoQueExiste.getId());

		aluno.setPergunta("Questao com Pergunta Alterada");

		ResultActions response = mockMvc.perform(
				put("/api/questoes").content(om.writeValueAsString(aluno)).contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(om.writeValueAsString(aluno))).andReturn();
	}

	@Test
	public void atualizaQuestao_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(put("/api/questoes").content(om.writeValueAsString(questaoQueNaoExiste))
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isNotFound()).andReturn();
	}

	// Deletar Questao ------------------------------
	@Test
	public void deletaQuestao_QueExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(delete("/api/questoes").content(om.writeValueAsString(questaoQueExiste))
				.contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void deletaQuestao_QueNaoExiste_Test() throws Exception {
		ResultActions response = mockMvc.perform(delete("/api/questoes")
				.content(om.writeValueAsString(questaoQueNaoExiste)).contentType(MediaType.APPLICATION_JSON));

		response.andExpect(status().isNotFound()).andReturn();
	}

}