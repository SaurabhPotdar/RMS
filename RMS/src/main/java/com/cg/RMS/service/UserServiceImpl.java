/**
 * 
 */
package com.cg.rms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cg.rms.dto.Company;
import com.cg.rms.dto.DatabaseFile;
import com.cg.rms.dto.Job;
import com.cg.rms.dto.User;
import com.cg.rms.exception.FileNotFoundException;
import com.cg.rms.exception.FileStorageException;
import com.cg.rms.exception.RmsException;
import com.cg.rms.repository.CompanyRepository;
import com.cg.rms.repository.DatabaseFileRepository;
import com.cg.rms.repository.JobRepository;
import com.cg.rms.repository.UserRepository;


/**
 * @author Saurabh
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
    private DatabaseFileRepository dbFileRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	/**
	 * To register a user
	 */
	@Override
	public boolean registerUser(User user) {
		//Converting to uppercase so that there is no problem in finding using Spring Data.
		String position = user.getPosition().toUpperCase();
		user.setPosition(position);
		
		Company tempCompany = companyRepository.findByEmail(user.getEmail());
		User tempUser = userRepository.findByEmail(user.getEmail());
		if(tempUser!=null || tempCompany!=null) {
			throw new RmsException("Email already exists");
		}
		else {
			userRepository.save(user);
			logger.trace("Registering user in service");
			return true;
		}
	}
	

	/**
	 * 
	 */
	@Override
	public List<Job> searchJobByQualification() {
		// TODO Auto-generated method stub
		return null;
	}



	/**
	 * 
	 */
	@Override
	public List<Job> searchJobByDesignation(String designation) {
		List<Job> jobList = jobRepository.findByDesignation(designation);
		if(jobList.size()!=0) {
			logger.trace("Getting jobList");
			return jobList;
		}
		else {
			logger.error("No jobs found");
			throw new RmsException("No jobs found");
		}
	}



	/**
	 * 
	 */
	@Override
	public List<Job> searchJobByExperience(int experience) {
		List<Job> jobList = jobRepository.findByExperienceLessThanEqual(experience);
		if(jobList.size()!=0) {
			logger.trace("Getting jobList");
			return jobList;
		}
		else {
			logger.error("No jobs found");
			throw new RmsException("No jobs found");
		}
	}



	/**
	 * 
	 */
	@Override
	public List<Job> searchJobByLocation(String location, int userId) {
		List<Job> jobList = jobRepository.findByLocation(location);
		List<Job> newJobList = new ArrayList<>();  //To store all job for which user has not applied yet
		if(jobList.size()!=0) {
			User user = searchUser(userId);
			Set<Job> appliedJob = user.getJobs();
			for(Job job:jobList) {
				//Check is user has applied for job, if not add it to list to display to user.
				if(!appliedJob.contains(job)) {
					newJobList.add(job);  //Return as a list to display to user
				}
			}
			return newJobList;
		}
		else {
			logger.error("No jobs found");
			throw new RmsException("No jobs found");
		}
	}
	
	/**
	 * 
	 */
	@Override
	public Job searchJobById(int jobId) {
		Job job = jobRepository.findById(jobId).orElse(null);
		if(job!=null) {
			logger.trace("Job found with Id: "+jobId);
			return job;
		}
		else {
			logger.error("No jobs found");
			throw new RmsException("No jobs found");
		}
	}
	
	/**
	 * 
	 */
	@Override
	public User searchUser(int userId) {
		User user = userRepository.findByUserId(userId);
		if(user!=null) {
			logger.trace("User found with Id: "+userId);
			return user;
		}
		else {
			logger.error("No user found");
			throw new RmsException("No user found with Id: "+userId);
		}
	}
	
	/**
	 * Description: We check if user has already applied for job, if not we add it to Set of appliedJob
	 */
	@Override
	public boolean applyForJob(int userId, int jobId) {
		User user = searchUser(userId);
		Job job = searchJobById(jobId);
		Set<Job> appliedJob = user.getJobs();
		Set<User> usersApplied = job.getUsersApplied();
		//Check is user has already applied for the job
		if(appliedJob.contains(job)) {
			throw new RmsException("Already applied for this job");
		}
		//If not, add it to the set.
		//This set is used so when we display jobs to the user, we don't show jobs he/she has already applied for.
		
		//Update user list
		appliedJob.add(job);
		user.setJobs(appliedJob); 
		
		//Update job list
		usersApplied.add(user);
		job.setUsersApplied(usersApplied);
		
		userRepository.save(user);  //Save user. (User cascades job)
		
//		usersApplied.put(userId, user);
//		job.setUsersApplied(usersApplied);  //Update job list
//		jobRepository.save(job);
		
		logger.trace("Applying for job in service");
		return true;	
	}
	
	/**
	 * 
	 */
	@Override
	public DatabaseFile storeFile(MultipartFile file, int userId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
            	logger.error("Invalid path sequence exception in Service");
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes());

            User user = searchUser(userId);
            user.setFile(dbFile);
            userRepository.save(user);
            dbFileRepository.save(dbFile);
            logger.trace("File added to database");
            return dbFile;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    /**
     * 
     */
	@Override
	public DatabaseFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }
    
    /**
     * 
     */
	@Override
    public DatabaseFile downloadFile(int userId) {
    	User user = searchUser(userId);
    	DatabaseFile file = user.getFile();
    	if(file==null) {
    		logger.error("No file found");
    		throw new RmsException("No file uploaded by user");
    	}
    	return file;
    }

}
