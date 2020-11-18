package com.cg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByUserId(@Param("userId") int userId);
	
	public User findByEmail(@Param("email") String email);
	
	public List<User> findByPosition(@Param("position") String position);
	
	public List<User> findByExperienceGreaterThanEqual(@Param("experience") int experience);

}
