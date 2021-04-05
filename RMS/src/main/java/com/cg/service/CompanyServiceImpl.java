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
import com.cg.dto.Login;
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
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private DatabaseFileRepository dbFileRepository;
	
	/**
	 * Description: To register a company
	 */
	@Override
	public boolean registerCompany(Company company) {
		//Check if email is unique. We need to check for both user and company
		Company tempCompany = companyRepository.findByEmail(company.getEmail());
		User tempUser = userRepository.findByEmail(company.getEmail());
		if(tempUser!=null || tempCompany!=null)
			throw new RmsException("Email already exists");
		else {
			companyRepository.save(company);
			log.trace("Registering company");
			return true;
		}
	}

	/**
	 * Description: To add job. We store the email and password of company when logging in, and use this to then
	 * find the company and add job.
	 */
	@Override
	public boolean addJob(int userId, Job job) {
		//First find the company which is logged in.
		Company company = companyRepository.findById(userId).orElse(null);
		if(company!=null) {
			log.trace("Found Company with Id: "+company.getCompanyId());
			job.setLocation(job.getLocation().toUpperCase());
			job.setDesignation(job.getDesignation().toUpperCase());
			job.setCompany(company);
			job.setCompanyName(company.getCompanyName());
			List<Job> jobList = company.getJobs();
			jobList.add(job);
			companyRepository.save(company);
			log.trace("Adding job");
			return true;
		}
		else {
			log.error("Company not found with id: "+userId);
			throw new RmsException("Company not found");
		}
	}


	/**
	 * Description: Search for user by qualification.
	 */
	@Override
	public List<User> searchUserByQualification(String qualification) {
		return null;
	}



	/**
	 * Search a user by designation. eg:Software Developer
	 */
	@Override
	public List<User> searchUserByPosition(String position) {
		List<User> userList;
		//Return all users if designation is All
		if(position.equalsIgnoreCase("All"))
			userList = userRepository.findAll();
		else
			userList = userRepository.findByPosition(position);
		if(userList.size()!=0) {
			log.trace("Getting userList");
			return userList;
		}
		else {
			log.error("No user found with position: "+position);
			throw new RmsException("No user found");
		}
	}


	/**
	 * Search a user by experience.
	 */
	@Override
	public List<User> searchUserByExperience(int experience) {
		List<User> userList = userRepository.findByExperienceGreaterThanEqual(experience);
		if(userList.size()!=0) {
			log.trace("Getting userList");
			return userList;
		}
		else {
			log.error("No user found with experience: "+experience);
			throw new RmsException("No user found");
		}
	}

	/**
	 * Check user credentials and return appropriate object User or Company.
	 */
	@Override
	public Object login(String email, String unencryptedPassword) {
		User user = userRepository.findByEmail(email);
		if(user!=null) {
			if(user.getPassword().equals(unencryptedPassword))
				return new Login("user",user.getUserId());
		}
		Company company = companyRepository.findByEmail(email);
		if(company!=null) {
			if(company.getPassword().equals(unencryptedPassword))
				return new Login("company",company.getCompanyId());
		}
		throw new RmsException("Invalid credentials");
	}
	
	
	/**
	 * Get jobs posted by company, for selecting jobId in frontend.
	 * Once jobId is selected we pass it to usersAppliedForJob() to get list of users
	 * @param companyId
	 * @return
	 */
	public List<Job> getJobsPostedByCompany(int companyId) {
		Company company = searchCompany(companyId);
		List<Job> jobs = company.getJobs();
		if(jobs.size()==0)
			throw new RmsException("No jobs found");
		return jobs;
	}
	
	/**
	 * Returns all users who have applied for a job.
	 * @param jobId - To return a list of users
	 */
	@Override
	public List<User> usersAppliedForJob(int jobId){
		Job job = jobRepository.findById(jobId).orElse(null);
		if(job==null)
			throw new RmsException("Job not found");
		Set<User> users = job.getUsersApplied();
		if(users.size()==0)
			throw new RmsException("No users have applied");
		return new ArrayList<User>(users);
	}
	
	
	/**
	 * Utility function for searching company
	 * @param companyId
	 * @return
	 */
	public Company searchCompany(int companyId) {
		Company company = companyRepository.findById(companyId).orElse(null);
		if(company!=null)
			return company;
		throw new RmsException("No company found");
	}
	
	/**
	 * Upload file to database
	 */
	@Override
	public DatabaseFile storeFile(MultipartFile file, int companyId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
            	log.error("Invalid path sequence exception in Service");
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes());

            Company company = searchCompany(companyId);
            company.setFile(dbFile);
            companyRepository.save(company);
            dbFileRepository.save(dbFile);
            log.trace("File added to database");
            return dbFile;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
	
	/**
     * Utility function for downloading file from database.
     */
	@Override
	public DatabaseFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }
    
    /**
     * Download file from database.
     */
	@Override
    public DatabaseFile downloadFile(int companyId) {
    	Company company = searchCompany(companyId);
    	DatabaseFile file = company.getFile();
    	if(file==null) {
    		log.error("No file found");
    		throw new RmsException("No file uploaded by user");
    	}
    	return file;
    }

	
	

}
