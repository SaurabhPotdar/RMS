package com.cg.rms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.rms.dto.Company;
import com.cg.rms.dto.Job;
import com.cg.rms.dto.Login;
import com.cg.rms.dto.User;
import com.cg.rms.exception.RmsException;
import com.cg.rms.repository.CompanyRepository;
import com.cg.rms.repository.JobRepository;
import com.cg.rms.repository.UserRepository;

/**
 * @author Saurabh
 *
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JobRepository jobRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

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
			logger.trace("Registering company");
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
			logger.trace("Found Company with Id: "+company.getCompanyId());
			job.setLocation(job.getLocation().toUpperCase());
			job.setDesignation(job.getDesignation().toUpperCase());
			job.setCompany(company);
			job.setCompanyName(company.getCompanyName());
			List<Job> jobList = company.getJobs();
			jobList.add(job);
			companyRepository.save(company);
			logger.trace("Adding job");
			return true;
		}
		else {
			logger.error("Company not found with id: "+userId);
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



	@Override
	public List<User> searchUserByPosition(String position) {
		List<User> userList = userRepository.findByPosition(position);
		if(userList.size()!=0) {
			logger.trace("Getting userList");
			return userList;
		}
		else {
			logger.error("No user found with position: "+position);
			throw new RmsException("No user found");
		}
	}



	@Override
	public List<User> searchUserByExperience(int experience) {
		List<User> userList = userRepository.findByExperienceGreaterThanEqual(experience);
		if(userList.size()!=0) {
			logger.trace("Getting userList");
			return userList;
		}
		else {
			logger.error("No user found with experience: "+experience);
			throw new RmsException("No user found");
		}
	}

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
	
	

}
