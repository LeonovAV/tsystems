package com.tsystems.rts.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;

/**
 * Class represents train entity.
 * @author Anton
 * @version 0.0.1
 *
 */
@Entity
@Table(name = "train", uniqueConstraints = @UniqueConstraint(columnNames = "train_no"))
public class Train {
	
	@Id
	@Column(name = "train_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long trainId;
	
	@Column(name = "train_no")
	@NotNull(message = "Train must have number")
	private long trainNo;
	
	@Column(name = "seats_number")
	@NotNull(message = "Seats number can not be empty")
	@Size(min = 1)
	private int seatsNumber;
	
	@Column(name = "starting_date")
	@NotNull
	private Timestamp startingDate;
	
	@Column(name = "period")
	@NotNull
	private int period;
	
	@OneToMany(mappedBy = "train")
	private List<Ticket> trainTickets = new ArrayList<Ticket>();
	
	@ManyToMany
	@JoinTable(name = "route", joinColumns = { @JoinColumn(name = "train_id", nullable = false) },
	   inverseJoinColumns = { @JoinColumn(name = "station_id", nullable = false) })
	private List<Station> route = new ArrayList<Station>();
	
	@OneToMany(mappedBy = "train")
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<Schedule> schedules = new ArrayList<Schedule>();
	
	public Train() {
	}

	public long getTrainId() {
		return trainId;
	}

	public void setTrainId(long trainId) {
		this.trainId = trainId;
	}

	public long getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(long trainNo) {
		this.trainNo = trainNo;
	}

	public int getSeatsNumber() {
		return seatsNumber;
	}

	public void setSeatsNumber(int seatsNumber) {
		this.seatsNumber = seatsNumber;
	}

	public Timestamp getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Timestamp startingDate) {
		this.startingDate = startingDate;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public List<Ticket> getTrainTickets() {
		return trainTickets;
	}

	public void setTrainTickets(List<Ticket> trainTickets) {
		this.trainTickets = trainTickets;
	}

	public List<Station> getRoute() {
		return route;
	}

	public void setRoute(List<Station> route) {
		this.route = route;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	@Override
	public String toString() {
		return "Train [trainId=" + trainId + ", trainNo=" + trainNo + ", startingDate=" + startingDate + ", period="
				+ period + "]";
	}
	
}
