package com.pmb.paymybuddy.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmb.paymybuddy.exception.FriendNotFoundException;
import com.pmb.paymybuddy.exception.SelfFriendshipException;

@ExtendWith(MockitoExtension.class)
class UserTest {
	
	@InjectMocks
	private User modelUnderTest;
	
	@Test
	void addFriendTestWithSelfFriendshipException() {
		
		assertThrows(SelfFriendshipException.class, () -> modelUnderTest.addFriend(this.modelUnderTest));
	}

	@Test
	void removeFriendWithFriendNotFoundException() {
		
		assertThrows(FriendNotFoundException.class, () -> modelUnderTest.removeFriend(new User()));
	}
	
}
