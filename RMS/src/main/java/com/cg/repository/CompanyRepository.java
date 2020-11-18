package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.cg.dto.Company;

//Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	//@Query("SELECT FROM Company c where t.email = :email") 
    public Company findByEmail(@Param("email") String email);
	
}
