/**
 * 
 */
package com.cg.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cg.dto.Company;
import com.cg.dto.DatabaseFile;
import com.cg.dto.Job;
import com.cg.dto.User;
import com.cg.exception.FileNotFoundException;
import com.cg.exception.FileStorageException;
import com.cg.exception.RmsException;
import com.cg.repository.CompanyRepository;
import com.cg.repository.DatabaseFileRepository;
import com.cg.repository.JobRepository;
import com.cg.repository.UserRepository;

import lombok.extern.log4j.Log4j2;


/**
 * @author Saurabh
 *
 */
@Service
@Transactional
@Log4j2
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
    private DatabaseFileRepository dbFileRepository;
	
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
			log.trace("Registering user in service");
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
	public List<Job> searchJobByDesignation(String designation, int userId) {
		List<Job> jobList = jobRepository.findByDesignation(designation);  //Contains all jobs based on search parameter.
		List<Job> newJobList = new ArrayList<>();  //To store all job for which user has not applied yet.
		//Iterate over jobList and check if user has already applied for a job, if not add it to newJobList.
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
			log.error("No jobs found");
			throw new RmsException("No jobs found");
		}
		
	}
	
	@Override
	public List<Job> searchJobByLocationAndDesignation(String location, String designation, int userId){
		List<Job> jobList = jobRepository.findByLocationAndDesignation(location, designation);  //Contains all jobs based on search parameter.
		List<Job> newJobList = new ArrayList<>();  //To store all job for which user has not applied yet.
		//Iterate over jobList and check if user has already applied for a job, if not add it to newJobList.
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
			log.error("No jobs found");
			throw new RmsException("No jobs found");
		}
	}


	/**
	 * Used to get when user does not specify any designation or location.
	 */
	@Override
	public List<Job> searchJob(int userId){
		List<Job> jobList = jobRepository.findAll();  //Contains all jobs based on search parameter.
		List<Job> newJobList = new ArrayList<>();  //To store all job for which user has not applied yet.
		//Iterate over jobList and check if user has already applied for a job, if not add it to newJobList.
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
			log.error("No jobs found");
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
			log.trace("Getting jobList");
			return jobList;
		}
		else {
			log.error("No jobs found");
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
			log.error("No jobs found");
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
			log.trace("Job found with Id: "+jobId);
			return job;
		}
		else {
			log.error("No jobs found");
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
			log.trace("User found with Id: "+userId);
			return user;
		}
		else {
			log.error("No user found");
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
		Set<Job> appliedJob = user.getJobs();  //Jobs list in user(For adding the job to the list of applied jobs)
		Set<User> usersApplied = job.getUsersApplied();  //Users list in job
		//Check is user has already applied for the job
		if(appliedJob.contains(job)) {
			throw new RmsException("Already applied for this job");
		}
		//If not, add it to the set.
		//This set is used so when we display jobs to the user, we don't show jobs he/she has already applied for.
		
		//Update user list
		appliedJob.add(job);
		user.setJobs(appliedJob);
		log.trace("Updated user list in service");
		
		//Update job list
		usersApplied.add(user);
		job.setUsersApplied(usersApplied);
		log.trace("Updated job list in service");
		
		userRepository.save(user);  //Save user. (User cascades job)
		log.trace("Saved user in service");
		
		
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
            	log.error("Invalid path sequence exception in Service");
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes());

            User user = searchUser(userId);
            user.setFile(dbFile);
            userRepository.save(user);
            dbFileRepository.save(dbFile);
            log.trace("File added to database");
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
    		log.error("No file found");
    		throw new RmsException("No file uploaded by user");
    	}
    	return file;
    }
	
	@Override
	public List<Job> jobsApplied(int userId){
		User user = searchUser(userId);
		Set<Job> jobs = user.getJobs();
		if(jobs.size()==0)
			throw new RmsException("No jobs found");
		return new ArrayList<>(jobs);
	}

}
