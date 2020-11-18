package com.cg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.cg.dto.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {
	
	 public List<Job> findByLocation(@Param("location") String location);
	 
	 public List<Job> findByDesignation(@Param("designation") String designation);
	 
	 public List<Job> findByExperienceLessThanEqual(@Param("experience") int experience);
	 
	 public List<Job> findByLocationAndDesignation(@Param("location") String location, @Param("designation") String designation);
	 

}
