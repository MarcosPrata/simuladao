package com.marcosprata.sas.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcosprata.sas.models.*;

public interface ProvasRepository extends JpaRepository<Prova, Long>{
	
	Prova findById(long id);
	List<Prova> findBySimulado(Simulado simulado);
	List<Prova> findByAluno(Aluno aluno);
}
