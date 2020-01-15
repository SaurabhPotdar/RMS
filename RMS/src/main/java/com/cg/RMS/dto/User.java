/**
 * 
 */
package com.cg.rms.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Saurabh
 *
 */
@Entity
@EntityListeners({ AuditingEntityListener.class })
public class User {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private int userId;
	@Column(name="first_name")
	private String firstName;
	@Column(name="email_Id")
	@Email(message="Enter valid email eg:abc@gmail.com")
	private String email;
	@Column(name="password")
	private String password;
	@Column(name="position")
	private String position;
	@Column(name="years_of_experience")
	private int experience;
	@Column(name="phone_number")
	private String phoneNumber;
	@Column(name="role")
	private String role;  //For user, admin and company
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private List<Qualification> qualification;
	@OneToMany
	@JoinColumn(name="job_id")
	private Map<Integer,Job> appliedJobs = new HashMap<>();
	@OneToOne
	@JoinColumn(name="file_id")
	private DatabaseFile file;
//	@OneToOne
//	@JoinColumn(name="file_id")
	
	//Designation---------------------------
	
	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	@Column(name = "modified_date")
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	@Column(name = "created_by")
	@CreatedBy
	private String createdBy;
	@Column(name = "modified_by")
	@LastModifiedBy
	private String modifiedBy;
	
	public User(int userId, String firstName, @Email(message = "Enter valid email eg:abc@gmail.com") String email,
			String password, String position, int experience, String phoneNumber, String role,
			List<Qualification> qualification, Map<Integer, Job> appliedJobs, DatabaseFile file, Date createdDate,
			Date modifiedDate, String createdBy, String modifiedBy) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.position = position;
		this.experience = experience;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.qualification = qualification;
		this.appliedJobs = appliedJobs;
		this.file = file;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	}



	public User() {
		super();
	}



	public int getUserId() {
		return userId;
	}



	public String getFirstName() {
		return firstName;
	}



	public String getEmail() {
		return email;
	}



	public String getPassword() {
		return password;
	}



	public String getPosition() {
		return position;
	}



	public int getExperience() {
		return experience;
	}



	public String getPhoneNumber() {
		return phoneNumber;
	}



	public String getRole() {
		return role;
	}



	public List<Qualification> getQualification() {
		return qualification;
	}



	public Map<Integer, Job> getAppliedJobs() {
		return appliedJobs;
	}



	public DatabaseFile getFile() {
		return file;
	}



	public Date getCreatedDate() {
		return createdDate;
	}



	public Date getModifiedDate() {
		return modifiedDate;
	}



	public String getCreatedBy() {
		return createdBy;
	}



	public String getModifiedBy() {
		return modifiedBy;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public void setPosition(String position) {
		this.position = position;
	}



	public void setExperience(int experience) {
		this.experience = experience;
	}



	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public void setQualification(List<Qualification> qualification) {
		this.qualification = qualification;
	}



	public void setAppliedJobs(Map<Integer, Job> appliedJobs) {
		this.appliedJobs = appliedJobs;
	}



	public void setFile(DatabaseFile file) {
		this.file = file;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}



	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	
	
		

}
