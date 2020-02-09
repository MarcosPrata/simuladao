package com.marcosprata.sas.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.marcosprata.sas.models.*;

public interface SimuladosRepository extends JpaRepository<Simulado, Long>{
	
	Simulado findById(long id);

}
