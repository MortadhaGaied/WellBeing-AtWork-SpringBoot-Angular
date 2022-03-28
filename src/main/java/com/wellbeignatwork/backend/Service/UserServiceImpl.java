package com.wellbeignatwork.backend.Service;

import com.wellbeignatwork.backend.Repository.UserRepository;
import com.wellbeignatwork.backend.ServiceImp.UserService;
import com.wellbeignatwork.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {
	ArrayList<User> users = new ArrayList<User>();
	@Autowired
	private UserRepository userRepository;

	@Override
	public User addUser(User user) {

		return userRepository.save(user);
	}

	@Override
	public void updateUser(User u) {

		userRepository.save(u);
	}

	@Override
	public User retrieveUser(Long id) {
		User u = userRepository.findById(id).orElse(null);
		return u;

	}

	@Override
	public List<User> retrieveAllUsers() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(user -> {
			users.add(user);
		});
		return users;
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);

	}

	@Override
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}
	@Override
	public User findUserByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User getUser(String username, String password) {
		for(int i=0;i<users.size();i++) {
			if(users.get(i).getName()==username && users.get(i).getPassword()==password) {
				return users.get(i);
			}
		}
		System.out.println("User Not Found");

		return null;
	}

	@Override
	public ArrayList<User> getUsers() {

		return users;
	}
}
