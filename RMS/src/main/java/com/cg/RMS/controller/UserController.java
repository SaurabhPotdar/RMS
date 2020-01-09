package com.cg.rms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cg.rms.dto.DatabaseFile;
import com.cg.rms.dto.Job;
import com.cg.rms.dto.Response;
import com.cg.rms.dto.User;
import com.cg.rms.service.UserServiceImpl;


/**
 * @author Saurabh
 *
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	private UserServiceImpl userService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**
	 * Register a user
	 * @param user
	 * @return
	 */
	@PostMapping(value="/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		try {
			//Set role as user, we can add admin directly from database
			user.setRole("User");
			userService.registerUser(user);
			logger.trace("Registering company");
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		catch(Exception exception) {
			logger.error("Caught validation exception in company/register Controller");
			return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * 
	 * @param designation
	 * @return
	 */
	@GetMapping(value="/searchbydesignation")
	public ResponseEntity<?> searchByDesignation(@RequestParam("designation") String designation) {
		try {
			//Converting it to uppercase to avoid problems while searching with Spring Data
			designation = designation.toUpperCase();
			List<Job> jobList = userService.searchJobByDesignation(designation);
			return new ResponseEntity<List<Job>>(jobList,HttpStatus.OK);
		}
		catch(Exception exception) {
			logger.error("Exception in user/searchbydesignation");
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
			List<Job> jobList = userService.searchJobByExperience(experience);
			return new ResponseEntity<List<Job>>(jobList,HttpStatus.OK);
		}
		catch(Exception exception) {
			logger.error("Exception in company/searchbyexperience");
			return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * 
	 * @param location
	 * @return
	 */
	@GetMapping(value="/searchbylocation")
	public ResponseEntity<?> searchByLocation(@RequestParam("location") String location) {
		try {
			//Converting it to uppercase to avoid problems while searching with Spring Data
			location = location.toUpperCase();
			List<Job> jobList = userService.searchJobByLocation(location);
			return new ResponseEntity<List<Job>>(jobList,HttpStatus.OK);
		}
		catch(Exception exception) {
			logger.error("Exception in company/searchbylocation");
			return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Apply for a job. User will be displayed job and when he clicks on apply button job id is passed.
	 * @param jobId
	 * @return
	 */
	@PutMapping(value="/applyforjob")
	public ResponseEntity<?> applyForJob(@RequestParam("jobId") int jobId, @RequestParam("userId") long userId){
		try {
			userService.applyForJob(userId, jobId);
			logger.trace("User: "+userId+" successfully applied for job: "+jobId);
			return new ResponseEntity<String>("User: "+userId+" successfully applied for job: "+jobId,HttpStatus.OK);
		}
		catch (Exception exception) {
			logger.error("Error in applyForJob in User Controller");
			return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Upload file to database
	 * @param file
	 * @return
	 */
	@PostMapping("/uploadFile")
    public Response uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") long userId) {
    	DatabaseFile fileName = userService.storeFile(file,userId);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName.getFileName())
                .toUriString();

        return new Response(fileName.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    /**
     * Upload multiple files to database
     * @param files
     * @return
     */
	@PostMapping("/uploadMultipleFiles")
    public List<Response> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("userId") long userId) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file,userId))
                .collect(Collectors.toList());
    }
	
	/**
	 * Download file from the database
	 * @param userId
	 * @return
	 */
	@GetMapping("/downloadFile")
	public ResponseEntity<?> downloadFile(@RequestParam("userId") long userId){
		try {
			DatabaseFile databaseFile = userService.downloadFile(userId);
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