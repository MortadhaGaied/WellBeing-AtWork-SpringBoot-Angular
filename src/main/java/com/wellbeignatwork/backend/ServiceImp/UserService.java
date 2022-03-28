package com.wellbeignatwork.backend.ServiceImp;
import com.wellbeignatwork.backend.model.User;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface UserService {


	User addUser (User user);

	void updateUser (User u);

	User retrieveUser (Long id);

	List<User> retrieveAllUsers();

	void deleteUser ( Long id);

	User findUserByEmail(String email);

	Optional<User> findUserById(Long id);

	public User getUser(String username, String password);
	public ArrayList<User> getUsers();

}
