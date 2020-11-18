package com.cg.dto;

import java.util.Date;
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Saurabh
 *
 */
@Entity
@EntityListeners({ AuditingEntityListener.class })
public class Company {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="company_id")
	private int companyId;
	
	@Column(name="email_Id")
	@Email(message="Enter valid email eg:abc@gmail.com")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="company_address")
	private String companyAddress;
	
	@OneToMany(mappedBy="company",cascade = CascadeType.ALL)
	private List<Job> jobs;
	
	@Column(name="role")
	private String role;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="logo")
	private DatabaseFile file;
	
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

	public Company(int companyId, @Email(message = "Enter valid email eg:abc@gmail.com") String email, String password,
			String companyName, String companyAddress, List<Job> jobs, String role, DatabaseFile file, Date createdDate,
			Date modifiedDate, String createdBy, String modifiedBy) {
		super();
		this.companyId = companyId;
		this.email = email;
		this.password = password;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.jobs = jobs;
		this.role = "Company";
		this.file = file;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
	}

	public Company() {
		super();
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = "Company";
	}

	public DatabaseFile getFile() {
		return file;
	}

	public void setFile(DatabaseFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", email=" + email + ", password=" + password + ", companyName="
				+ companyName + ", companyAddress=" + companyAddress + ", jobs=" + jobs + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + "]";
	}

	
	
	

}
