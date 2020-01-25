package com.cg.rms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cg.rms.dto.Company;
import com.cg.rms.dto.DatabaseFile;
import com.cg.rms.dto.Job;
import com.cg.rms.dto.Response;
import com.cg.rms.dto.User;
import com.cg.rms.service.CompanyServiceImpl;

/**
 * @author Saurabh
 *
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	private CompanyServiceImpl companyService;

	
	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

	/**
	 * Regestering a company
	 * @param company
	 * @return
	 */
	@PostMapping(value="/register")
	public ResponseEntity<?> registerCompany(@RequestBody Company company) {
		try {
			companyService.registerCompany(company);
			logger.trace("Registered company");
			return new ResponseEntity<Company>(company,HttpStatus.OK);
		}
		catch(Exception exception) {
			logger.error("Caught validation exception in company/register Controller");
			return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	//List<User> searchUserByQualification()
	@GetMapping(value="/searchbyqualification")
	public ResponseEntity<?> searchByQualification(@RequestBody Company company) {
		return null;
	}
	
	/**
	 * 
	 * @param userId - Id of company logged in
	 * @param job
	 * @return
	 */
	@PostMapping(value="/addjob")
	public ResponseEntity<?> addJob(@RequestParam("userId") int userId,@RequestBody Job job) {
		try {
			companyService.addJob(userId, job);
			logger.trace("Added job");
			return new ResponseEntity<Job>(job,HttpStatus.OK);
		}
		catch(Exception exception) {
			logger.error("Caught exception in company/addjob Controller");
			return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	@GetMapping(value="/searchbyposition")
	public ResponseEntity<?> searchByPosition(@RequestParam("position") String position) {
		try {
			position = position.toUpperCase();
			List<User> userList = companyService.searchUserByPosition(position);
			return new ResponseEntity<List<User>>(userList,HttpStatus.OK);
		}
		catch(Exception exception) {
			logger.error("Exception in company/searchbyposition");
			return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * 
	 * @param experience
	 * @return
	 */
	@GetMapping(value="/searchbyexperience")
	public ResponseEntity<?> searchByExperience(@RequestParam("experience") int experience) {
		try {
			List<User> userList = companyService.searchUserByExperience(experience);
			return new ResponseEntity<List<User>>(userList,HttpStatus.OK);
		}
		catch(Exception exception) {
			logger.error("Exception in company/searchbyposition");
			return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value="/usersapplied")
	public ResponseEntity<?> usersAppliedForJob(@RequestParam("jobId") int jobId){
		try {
			return new ResponseEntity<List<User>>(companyService.usersAppliedForJob(jobId),HttpStatus.OK);
		}
		catch(Exception exception) {
			return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Upload file to database
	 * @param file
	 * @return
	 */
	@PostMapping("/uploadFile")
    public Response uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("companyId") int companyId) {
		DatabaseFile fileName = companyService.storeFile(file,companyId);
    	String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName.getFileName())
                .toUriString();

        return new Response(fileName.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }
	
}
