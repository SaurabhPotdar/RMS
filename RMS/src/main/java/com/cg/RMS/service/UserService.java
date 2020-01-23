/**
 * 
 */
package com.cg.rms.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cg.rms.dto.DatabaseFile;
import com.cg.rms.dto.Job;
import com.cg.rms.dto.User;

/**
 * @author Saurabh
 *
 */
public interface UserService {
	
	public boolean registerUser(User user);
	
	public List<Job> searchJobByQualification();
	
	public List<Job> searchJobByExperience(int experience);
	
	public List<Job> searchJobByDesignation(String designation, int userId);

	public Job searchJobById(int jobId);

	public User searchUser(int userId);

	public boolean applyForJob(int userId, int jobId);

	public DatabaseFile getFile(String fileId);

	public DatabaseFile storeFile(MultipartFile file, int userId);

	public DatabaseFile downloadFile(int userId);

	public List<Job> searchJobByLocation(String location, int userId);

	public List<Job> searchJobByLocationAndDesignation(String location, String designation, int userId);

	public List<Job> searchJob(int userId);

}
