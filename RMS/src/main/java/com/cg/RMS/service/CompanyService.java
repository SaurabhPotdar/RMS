/**
 * 
 */
package com.cg.rms.service;

import java.util.List;

import com.cg.rms.dto.Company;
import com.cg.rms.dto.Job;
import com.cg.rms.dto.User;

/**
 * @author Saurabh
 *
 */
public interface CompanyService {

	public boolean registerCompany(Company company);
	
	public List<User> searchUserByQualification(String qualification);
	
	public List<User> searchUserByPosition(String position);
	
	public List<User> searchUserByExperience(int experience);

	public boolean addJob(String email, Job job);
	
	public Object login(String email, String password);
	
	
	
}