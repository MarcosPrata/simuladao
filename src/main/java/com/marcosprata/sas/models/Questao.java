package com.marcosprata.sas.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "TB_QUESTOES")
public class Questao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String nivel;
	private String pergunta;

	private String alternativa_A;
	private String alternativa_B;
	private String alternativa_C;
	private String alternativa_D;
	private String alternativa_E;

	private String alternativaCorreta;

	

	public Questao() {
		super();
	}

	public long getId() {
		return id;
	}

	public Questao setId(long id) {
		this.id = id;
		return this;
	}

	public String getNivel() {
		return nivel;
	}

	public Questao setNivel(String nivel) {
		this.nivel = nivel;
		return this;
	}

	public String getPergunta() {
		return pergunta;
	}

	public Questao setPergunta(String pergunta) {
		this.pergunta = pergunta;
		return this;
	}

	public String getAlternativa_A() {
		return alternativa_A;
	}

	public Questao setAlternativa_A(String alternativa_A) {
		this.alternativa_A = alternativa_A;
		return this;
	}

	public String getAlternativa_B() {
		return alternativa_B;
	}

	public Questao setAlternativa_B(String alternativa_B) {
		this.alternativa_B = alternativa_B;
		return this;
	}

	public String getAlternativa_C() {
		return alternativa_C;
	}

	public Questao setAlternativa_C(String alternativa_C) {
		this.alternativa_C = alternativa_C;
		return this;
	}

	public String getAlternativa_D() {
		return alternativa_D;
	}

	public Questao setAlternativa_D(String alternativa_D) {
		this.alternativa_D = alternativa_D;
		return this;
	}

	public String getAlternativa_E() {
		return alternativa_E;
	}

	public Questao setAlternativa_E(String alternativa_E) {
		this.alternativa_E = alternativa_E;
		return this;
	}

	public String getAlternativaCorreta() {
		return alternativaCorreta;
	}

	public Questao setAlternativaCorreta(String alternativaCorreta) {
		this.alternativaCorreta = alternativaCorreta;
		return this;
	}

}
