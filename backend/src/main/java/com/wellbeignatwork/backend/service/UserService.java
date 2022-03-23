package com.wellbeignatwork.backend.service;

import com.wellbeignatwork.backend.entity.User;
import com.wellbeignatwork.backend.exceptions.ResourceNotFoundException;
import com.wellbeignatwork.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepository repository;
@Transactional
    public void saveFirebaseToken(Long userId,String token){
       User user= repository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user with id : "+userId+ " not found"));
       user.setFireBaseToken(token);
    }
}
