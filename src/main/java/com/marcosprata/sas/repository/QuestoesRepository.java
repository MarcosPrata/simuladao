package com.marcosprata.sas.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.marcosprata.sas.models.*;

public interface QuestoesRepository extends JpaRepository<Questao, Long>{
	
	Questao findById(long id);

}
