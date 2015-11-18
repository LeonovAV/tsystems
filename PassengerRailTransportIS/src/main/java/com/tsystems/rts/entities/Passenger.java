package com.tsystems.rts.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

/**
 * Class represents passenger entity.
 * @author Anton
 * @version 0.0.1
 * 
 */
@Entity
@Table(name = "passenger")
public class Passenger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "passenger_id")
	private long passengerId;
	
	@Column(name = "first_name")
	@NotNull(message = "First name can not be empty")
	@Size(min = 1, max = 255)
	private String firstName;
	
	@Column(name = "last_name")
	@NotNull(message = "Last name can not be empty")
	@Size(min = 1, max = 255)
	private String lastName;
	
	@Column(name = "birthdate")
	@Type(type = "date")
	private Date birthdate;
	
	@OneToMany(mappedBy = "passenger")
	private List<Ticket> purchasedTickets = new ArrayList<Ticket>();
	
	public Passenger() {
	}
	
	public long getPassengerId() {
		return passengerId;
	}
	
	public void setPassengerId(long id) {
		this.passengerId = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Date getBirthdate() {
		return birthdate;
	}
	
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public List<Ticket> getPurchasedTickets() {
		return purchasedTickets;
	}

	public void setPurchasedTickets(List<Ticket> purchasedTickets) {
		this.purchasedTickets = purchasedTickets;
	}

}
