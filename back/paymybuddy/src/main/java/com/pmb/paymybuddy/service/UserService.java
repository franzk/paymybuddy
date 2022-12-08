package com.pmb.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmb.paymybuddy.dto.UserDto;
import com.pmb.paymybuddy.exception.AlreadyFriendException;
import com.pmb.paymybuddy.exception.FriendNotFoundException;
import com.pmb.paymybuddy.exception.SelfFriendshipException;
import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User save(User user) {
		return userRepository.save(user);
	}

	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(Integer id) {
		return userRepository.findById(id);
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> getUserByName(String name) {
		return userRepository.findByName(name);
	}

	public User addFriend(User user, String friendEmail) throws UserNotFoundException, SelfFriendshipException, AlreadyFriendException {
		Optional<User> optionalFriend = userRepository.findByEmail(friendEmail);
		if (optionalFriend.isEmpty()) {
			throw new UserNotFoundException();
		}
		User friend = optionalFriend.get();
		if (user.getFriends().contains(friend)) {
			throw new AlreadyFriendException();
		} else {
			user.addFriend(friend);
		}
		return userRepository.save(user);
	}

	public User removeFriend(User user, String email) throws UserNotFoundException, FriendNotFoundException {
		Optional<User> friend = userRepository.findByEmail(email);
		if (friend.isEmpty()) {
			throw new UserNotFoundException();
		}

		user.removeFriend(friend.get());

		return userRepository.save(user);

	}

	public List<UserDto> getDtoFriends(User user) {
		return user.getFriends().stream().map(friend -> {
			UserDto userDto = new UserDto();
			userDto.setName(friend.getName());
			userDto.setEmail(friend.getEmail());
			return userDto;
		}).toList();
	}
	
	public void changeUserName(User user, String newName) {
		user.setName(newName);
		this.save(user);
	}

}
