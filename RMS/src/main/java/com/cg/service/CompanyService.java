/**
 * 
 */
package com.cg.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cg.dto.Company;
import com.cg.dto.DatabaseFile;
import com.cg.dto.Job;
import com.cg.dto.User;

/**
 * @author Saurabh
 *
 */
public interface CompanyService {

	public boolean registerCompany(Company company);
	
	public List<User> searchUserByQualification(String qualification);
	
	public List<User> searchUserByPosition(String position);
	
	public List<User> searchUserByExperience(int experience);

	public Object login(String email, String password);

	public boolean addJob(int id, Job job);

	public List<User> usersAppliedForJob(int jobId);

	public DatabaseFile storeFile(MultipartFile file, int userId);

	public DatabaseFile downloadFile(int companyId);

	public DatabaseFile getFile(String fileId);	
	
}
