package com.marcosprata.sas.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity 
@Table(name ="TB_PROVAS")
public class Prova implements Serializable, Comparable<Prova>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_anulo", referencedColumnName = "id")
	private Aluno aluno;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_simulado", referencedColumnName = "id")
	private Simulado simulado;
	
	@ElementCollection(targetClass=String.class)
	private List<String> gabarito;
	
	private int nota;
	
	
	public Prova() {
		super();
		this.nota = 0;
		this.aluno = new Aluno();
		this.simulado = new Simulado();
		this.gabarito = new ArrayList<String>();
	}
	
	public List<String> getGabarito() {
		return gabarito;
	}
	public void setGabarito(List<String> gabarito) {
		this.gabarito = gabarito;
	}
	public long getId() {
		return id;
	}
	public Prova setId(long id) {
		this.id = id;
		return this;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public Prova setAluno(Aluno aluno) {
		this.aluno = aluno;
		return this;
	}
	public Simulado getSimulado() {
		return simulado;
	}
	public Prova setSimulado(Simulado simulado) {
		this.simulado = simulado;
		return this;
	}
	public int getNota() {
		return nota;
	}
	public Prova calculaNota() {
		List<String> gabarito = this.gabarito;
		List<Questao> questoes = this.simulado.getQuestoes();
		int QF = 0, QM = 0, QD = 0;
		for (int i = 0; i < questoes.size(); i++) {
			if (questoes.get(i).getAlternativaCorreta().trim().equals(gabarito.get(i).trim())) {
				if (questoes.get(i).getNivel() == "FACIL")QF++;
				if (questoes.get(i).getNivel() == "MEDIO")QM++;
				if (questoes.get(i).getNivel() == "DIFICIL")QD++;
			}	
		}
		this.nota = (QF*15)+(QM*12)+(QD*8)+600;
		return this;
	}
	
	@Override
	public int compareTo(Prova prova) {
		return prova.getNota() - this.getNota();
	}	
	
}
