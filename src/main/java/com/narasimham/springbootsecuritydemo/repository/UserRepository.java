package com.narasimham.springbootsecuritydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.narasimham.springbootsecuritydemo.entity.User;

//@Transactional(propagation = Propagation.MANDATORY)
public interface UserRepository extends JpaRepository<User, Integer> {

	//User findByEmail(String email);
	
	@Query("select u from User u where u.email = :email")
	User getUserByUserName(@Param("email") String email);
	
	@Query("select u from User u where u.mobile = :mobile")
	User getUserByMobile(@Param("mobile") String mobile);
	
}
