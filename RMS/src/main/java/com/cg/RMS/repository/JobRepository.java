package com.cg.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.cg.rms.dto.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
	
	 public List<Job> findByLocation(@Param("location") String location);
	 
	 public List<Job> findByDesignation(@Param("designation") String designation);
	 
	 public List<Job> findByExperienceLessThanEqual(@Param("experience") int experience);
	 

}
