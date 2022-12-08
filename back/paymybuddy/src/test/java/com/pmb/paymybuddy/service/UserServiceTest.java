package com.pmb.paymybuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmb.paymybuddy.dto.UserDto;
import com.pmb.paymybuddy.exception.AlreadyFriendException;
import com.pmb.paymybuddy.exception.FriendNotFoundException;
import com.pmb.paymybuddy.exception.SelfFriendshipException;
import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@InjectMocks
	private UserService serviceUnderTest;

	@Mock
	private UserRepository userRepository;
	
	@Test
	void saveTest() {
		// Arrange
		User testUser = new User();
		testUser.setName(RandomString.make(64));
		testUser.setEmail(RandomString.make(64));

		// Act
		serviceUnderTest.save(testUser);

		// Assert
		verify(userRepository, times(1)).save(testUser);

	}

	@Test
	void getUsersTest() {
		serviceUnderTest.getUsers();
		verify(userRepository, times(1)).findAll();
	}

	@Test
	void getUserByIdTest() {
		int id = new Random().nextInt();
		serviceUnderTest.getUserById(id);
		verify(userRepository, times(1)).findById(id);
	}

	@Test
	void getUserByEmailTest() {
		String email = RandomString.make(64);
		serviceUnderTest.getUserByEmail(email);
		verify(userRepository, times(1)).findByEmail(email);
	}

	@Test
	void getUserByNameTest() {
		String name = RandomString.make(64);
		serviceUnderTest.getUserByName(name);
		verify(userRepository, times(1)).findByName(name);
	}
	
	@Test
	void addFriendTest() throws UserNotFoundException, SelfFriendshipException, AlreadyFriendException {
		// Arrange
		User testUser = new User();
		testUser.setName(RandomString.make(64));
		testUser.setEmail(RandomString.make(64));
		
		User friend = new User();
		friend.setName(RandomString.make(64));
		friend.setEmail(RandomString.make(64));
		
		when(userRepository.findByEmail(any())).thenReturn(Optional.of(friend));

		// Act
		serviceUnderTest.addFriend(testUser, friend.getEmail());
		
		// Assert
		verify(userRepository, times(1)).save(any());
	}

	@Test
	void addFriendWithUserNotFoundExceptionTest() {
		// Arrange
		User testUser = new User();
		testUser.setName(RandomString.make(64));
		testUser.setEmail(RandomString.make(64));
		
		String testEmail = RandomString.make(64);

		when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

		// Act + Assert
		assertThrows(UserNotFoundException.class, () -> serviceUnderTest.addFriend(testUser, testEmail));
		
	}
	
	@Test
	void addFriendWithSelfFriendshipException() {
		// Arrange
		User testUser = new User();
		testUser.setName(RandomString.make(64));
		testUser.setEmail(RandomString.make(64));
		
		String testEmail = RandomString.make(64);

		when(userRepository.findByEmail(any())).thenReturn(Optional.of(testUser));

		// Act + Assert
		assertThrows(SelfFriendshipException.class, () -> serviceUnderTest.addFriend(testUser, testEmail));
		
	}
	
	@Test
	void addFriendWithAlreadyFriendException() throws SelfFriendshipException {
		// Arrange
		User testUser = new User();
		testUser.setName(RandomString.make(64));
		testUser.setEmail(RandomString.make(64));
		
		User testFriend = new User();
		testUser.setName(RandomString.make(64));
		testUser.setEmail(RandomString.make(64));
		
		testUser.addFriend(testFriend);

		when(userRepository.findByEmail(any())).thenReturn(Optional.of(testFriend));

		// Act + Assert
		assertThrows(AlreadyFriendException.class, () -> serviceUnderTest.addFriend(testUser, testFriend.getEmail()));
		
	}
	
	
	@Test
	void removeFriendTest() throws UserNotFoundException, FriendNotFoundException, SelfFriendshipException  {
		// Arrange
		User testUser = new User();
		testUser.setName(RandomString.make(64));
		testUser.setEmail(RandomString.make(64));
		
		User friend = new User();
		friend.setName(RandomString.make(64));
		friend.setEmail(RandomString.make(64));
		
		testUser.addFriend(friend);
		
		when(userRepository.findByEmail(any())).thenReturn(Optional.of(friend));

		// Act
		serviceUnderTest.removeFriend(testUser, friend.getEmail());
		
		// Assert
		verify(userRepository, times(1)).save(any());
		
	}
	
	@Test
	void removeFriendWithUserNotFoundExceptionTest() {
		// Arrange
		User testUser = new User();
		testUser.setName(RandomString.make(64));
		testUser.setEmail(RandomString.make(64));
		
		String testEmail = RandomString.make(64);

		when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

		// Act + Assert
		assertThrows(UserNotFoundException.class, () -> serviceUnderTest.removeFriend(testUser, testEmail));
		
	}
	
	@Test
	void getDtoFriendsTest() throws SelfFriendshipException {
		
		// Arrange
		User testUser = new User();
		String testName1 = RandomString.make(64);
		String testName2 = RandomString.make(64);
		User friend1 = new User();
		friend1.setName(testName1);
		User friend2 = new User();
		friend2.setName(testName2);
		testUser.addFriend(friend1);
		testUser.addFriend(friend2);
		
		// Act
		List<UserDto> dtoFriends = serviceUnderTest.getDtoFriends(testUser);
		
		// Arrange
		assertThat(dtoFriends).hasSize(2);
		assertThat(dtoFriends.get(0).getName()).isEqualTo(testName1);
		assertThat(dtoFriends.get(1).getName()).isEqualTo(testName2);
		
		
	}
}
