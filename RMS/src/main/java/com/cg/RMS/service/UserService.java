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
	
	public List<Job> searchJobByLocation(String location);

	public List<Job> searchJobByDesignation(String designation);

	public Job searchJobById(int jobId);

	public User searchUser(long userId);

	public boolean applyForJob(long userId, long jobId);

	public DatabaseFile getFile(String fileId);

	public DatabaseFile storeFile(MultipartFile file, long userId);

	public DatabaseFile downloadFile(long userId);

}
