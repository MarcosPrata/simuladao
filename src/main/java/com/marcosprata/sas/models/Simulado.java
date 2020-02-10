package com.marcosprata.sas.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity 
@Table(name ="TB_SIMULADOS")
public class Simulado implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String titulo;
	@OneToMany(targetEntity = com.marcosprata.sas.models.Questao.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "simulado_fk", referencedColumnName = "id")
	private List<Questao> questoes;
	
	public Simulado() {
		super();
		this.titulo = "";
		this.questoes = new ArrayList<Questao>();
	}

	public long getId() {
		return id;
	}

	public Simulado setId(long id) {
		this.id = id;
		return this;
	}

	public String getTitulo() {
		return titulo;
	}

	public Simulado setTitulo(String titulo) {
		this.titulo = titulo;
		return this;
	}

	public List<Questao> getQuestoes() {
		return questoes;
	}

	public Simulado setQuestoes(List<Questao> questoes) {
		this.questoes = questoes;
		return this;
	}

	
}
