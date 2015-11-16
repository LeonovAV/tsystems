package com.tsystems.rts.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Class represents ticket entity.
 * @author Anton
 * @version 0.0.1
 *
 */
@Entity
@Table(name = "ticket")
public class Ticket {
	
	@Id
	@Column(name = "ticket_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ticketId;
	
	@ManyToOne
	@JoinColumn(name = "train_id")
	@NotNull(message = "Every ticket belongs to train")
	private Train train;
	
	@ManyToOne
	@JoinColumn(name = "passenger_id")
	@NotNull(message = "There is no ticket for train without passenger")
	private Passenger passenger;
	
	@Column(name = "train_arrival_date")
	@NotNull(message = "Train arrival date can not be empty")
	private Timestamp trainArrivalDate;
	
	public Ticket() {
	}
	
	public long getTicketId() {
		return ticketId;
	}
	
	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}
	
	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	
	public Timestamp getTrainArrivalDate() {
		return trainArrivalDate;
	}
	
	public void setTrainArrivalDate(Timestamp trainArrivalDate) {
		this.trainArrivalDate = trainArrivalDate;
	}
	
}
