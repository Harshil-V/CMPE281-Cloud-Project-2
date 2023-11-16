package com.sjsu.cloud.travelapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sjsu.cloud.travelapp.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String>{
	UserEntity findByUserEmail(String userEmail);

}
