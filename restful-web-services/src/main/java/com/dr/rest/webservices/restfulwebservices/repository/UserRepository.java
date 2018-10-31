package com.dr.rest.webservices.restfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dr.rest.webservices.restfulwebservices.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

}
