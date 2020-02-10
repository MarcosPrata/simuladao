package com.marcosprata.sas.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcosprata.sas.models.Aluno;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestoesControllerTest{

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;

	ObjectMapper om = new ObjectMapper();

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void adicionarAluno() throws Exception {
		Aluno aluno = new Aluno().setNome("Marcos");
		String jsonRequest = om.writeValueAsString(aluno);
		
		ResultActions response = mockMvc.perform(post("/api/alunos")
				.content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE));
		
		
		response.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}

	
	@Test
	public void pegaAlunos() throws Exception {
		ResultActions response = mockMvc.perform(get("/api/alunos")
				.content(MediaType.APPLICATION_JSON_VALUE));
		
		response.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}

}