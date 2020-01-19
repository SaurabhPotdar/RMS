package com.cg.rms.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.rms.dto.User;
import com.cg.rms.service.CompanyServiceImpl;

/**
 * @author Saurabh
 *
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	private CompanyServiceImpl companyService;
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	/**
	 * Checks for valid credentials and sends a role string for company or user.
	 * @param email
	 * @param password
	 * @return
	 */
	@PostMapping(value="/login")
	public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password) {
		try {
			logger.trace("Checking if credentials are valid");
			Object object = companyService.login(email, password);
			if(object instanceof User) {
				return new ResponseEntity<Object>(object,HttpStatus.OK);
			}
			//If no user found then service method will throw an exception
			//So no need to check for instance of Company
			else{
				return new ResponseEntity<Object>(object,HttpStatus.OK);
			}
			
		}
		catch (Exception exception) {
			logger.error("Invalid credentials");
			return new ResponseEntity<String>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	

}
