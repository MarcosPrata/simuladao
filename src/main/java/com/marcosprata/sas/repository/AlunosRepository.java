package com.marcosprata.sas.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.marcosprata.sas.models.*;

public interface AlunosRepository extends JpaRepository<Aluno, Long>{
	
	Aluno findById(long id);

}
