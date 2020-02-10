package com.marcosprata.sas.models;

import java.io.Serializable;

import javax.persistence.*;

@Entity 
@Table(name ="TB_ALUNOS")
public class Aluno implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String nome;
	
	public Aluno() {}
	
	public Aluno(long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", nome=" + nome + "]";
	}

	public long getId() {
		return id;
	}

	public Aluno setId(long id) {
		this.id = id;
		return this;
	}

	public String getNome() {
		return nome;
	}

	public Aluno setNome(String nome) {
		this.nome = nome;
		return this;
	}	
	
}
