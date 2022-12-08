package com.pmb.paymybuddy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.DynamicUpdate;

import com.pmb.paymybuddy.exception.FriendNotFoundException;
import com.pmb.paymybuddy.exception.SelfFriendshipException;

import lombok.Data;
import net.bytebuddy.utility.RandomString;

@Entity
@Data
@DynamicUpdate
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column(nullable=false, unique=true)
	@NotEmpty(message = "L'email est obligatoire.\n")
	@Email(message="Impossible de créer le compte. L'adresse email est incorrecte.")
	private String email;

	@Column(nullable=false)
	@NotEmpty(message = "Le nom est obligatoire.\n")
	private String name;

	@Column(nullable=false)
	private String password = RandomString.make(64);

	@ManyToMany(fetch = FetchType.LAZY, 
				cascade = { 
						CascadeType.PERSIST, 
						CascadeType.MERGE })
	@JoinTable(name = "friends", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "friend_id"),
				uniqueConstraints = {@UniqueConstraint(
						columnNames = {"user_id", "friend_id"})})
	private List<User> friends = new ArrayList<>();
	
	private double balance;

	public void addFriend(User friend) throws SelfFriendshipException {
		if (friend.equals(this)) {
			throw new SelfFriendshipException();
		}
		friends.add(friend);
	}

	public void removeFriend(User friend) throws FriendNotFoundException {
		if (!friends.remove(friend)) {
			throw new FriendNotFoundException();
		}
	}
	
}
