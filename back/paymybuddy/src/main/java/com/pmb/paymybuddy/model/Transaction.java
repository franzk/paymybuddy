package com.pmb.paymybuddy.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
@Table(name = "transaction")
@SqlResultSetMappings({
@SqlResultSetMapping(
	    name="userTransactionsResult",
	    classes={
	      @ConstructorResult(
	        targetClass=com.pmb.paymybuddy.dto.TransactionDto.class,
	        columns={
	          @ColumnResult(name="date", type=LocalDateTime.class),
	          @ColumnResult(name="amount", type=Double.class),
	          @ColumnResult(name="type", type=String.class),
	          @ColumnResult(name="senderEmail", type=String.class),
	          @ColumnResult(name="senderName", type=String.class),
	          @ColumnResult(name="recipientEmail", type=String.class),
	          @ColumnResult(name="recipientName", type=String.class)
	          })}),
@SqlResultSetMapping(name="userTransactionsResult.count", columns=@ColumnResult(name="cnt"))})
@NamedNativeQueries({
@NamedNativeQuery(
		name = "Transaction.getUserTransactions", 
		query= "SELECT date, "
					+ " IF((sender_id=?1 && amount_sent>0), -amount_sent, amount_received) AS amount, type, "
					+ " sender_id AS senderId, s.email AS senderEmail, s.name AS senderName, "
					+ " recipient_id AS recipientId, r.email AS recipientEmail, r.name AS recipientName "
			+ " FROM transaction AS t "
				+ " INNER JOIN user as s ON s.user_id = t.sender_id "
				+ " INNER JOIN user as r ON r.user_id = t.recipient_id "
			+ " WHERE t.sender_id = ?1 OR t.recipient_id = ?1 "
			+ " ORDER BY t.date DESC ",
		resultSetMapping = "userTransactionsResult"),
@NamedNativeQuery(
		name = "Transaction.getUserTransactions.count", 
		query = "SELECT COUNT(*) AS cnt FROM transaction as t WHERE t.sender_id = ?1 OR t.recipient_id = ?1 ",
		resultSetMapping = "userTransactionsResult.count")})
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Integer transactionId;
	
	@ManyToOne(
			cascade = { 
					CascadeType.PERSIST, 
					CascadeType.MERGE 
					}
			)
	@JoinColumn(name="sender_id", nullable=false)
	private User sender;
	
	@ManyToOne(
			cascade = { 
					CascadeType.PERSIST, 
					CascadeType.MERGE 
					}
			)
	@JoinColumn(name="recipient_id", nullable=false)
	private User recipient;
	
	@Column(nullable=false)
	private LocalDateTime date;

	@Column(name="amount_received", nullable=false)
	double amountReceived;	
	
	@Column(name="amount_sent", nullable=false)
	double amountSent;	

	@Column(nullable=false)
	String type;	   /// ENUM AVEC STRING
	
}
