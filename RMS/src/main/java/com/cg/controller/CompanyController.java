package com.cg.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.cg.dto.Company;
import com.cg.dto.DatabaseFile;
import com.cg.dto.Job;
import com.cg.dto.Response;
import com.cg.dto.User;
import com.cg.service.CompanyServiceImpl;

import lombok.extern.log4j.Log4j2;

/**
 * @author Saurabh
 *
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/company")
@Log4j2
public class CompanyController {
	
	@Autowired
	HttpSession session;
	
	//Autowire the CompanyService interface, instead of implemented class.
	//If there is more than one implementatation of same interface use @Qualifier.
	@Autowired
	private CompanyServiceImpl companyService;

	
	/**
	 * Regestering a company
	 * @param company
	 * @return
	 */
	@PostMapping(value="/register")
	public ResponseEntity<?> registerCompany(@RequestBody Company company) {
		try {
			companyService.registerCompany(company);
			log.trace("Registered company");
			return new ResponseEntity<Company>(company,HttpStatus.CREATED);
		}
		catch(Exception exception) {
			log.error("Caught validation exception in company/register Controller");
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
			log.trace("Added job");
			return new ResponseEntity<Job>(job,HttpStatus.CREATED);
		}
		catch(Exception exception) {
			log.error("Caught exception in company/addjob Controller");
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
			log.error("Exception in company/searchbyposition");
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
			log.error("Exception in company/searchbyposition");
			return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * First we return all jobs posted by company, so user can select jobId.
	 * Then based on jobId selected we use usersAppliedForJob() to return list of users applied for job.
	 * @param companyId
	 * @return
	 */
	@GetMapping(value="/getJobId")
	public ResponseEntity<?> getJobsPostedByCompany(@RequestParam("companyId") int companyId){
		try {
			return new ResponseEntity<List<Job>>(companyService.getJobsPostedByCompany(companyId),HttpStatus.OK);
		}
		catch (Exception exception) {
			return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * 
	 * @param jobId
	 * @return
	 */
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
		//Return the download uri after uploading the file.
    	String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()  //localhost:4200
                .path("/downloadFile/")                                                //append /downloadFile/ to localhost:4200 
                .path(fileName.getFileName())                                          //append fileName to localhost:4200/downloadFile/
                .toUriString();                                                        //localhost:4200/downloadFile/fileName

        return new Response(fileName.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }
	
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	@GetMapping("/downloadFile")
	public ResponseEntity<?> downloadFile(@RequestParam("companyId") int companyId){
		try {
			DatabaseFile databaseFile = companyService.downloadFile(companyId);
			return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(databaseFile.getFileType()))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + databaseFile.getFileName() + "\"")
	                .body(new ByteArrayResource(databaseFile.getData()));
		}
		catch (Exception exception) {
			return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
}
