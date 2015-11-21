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

/**
 * Class describes train schedule for a certain station.
 * @author Anton
 * @version 0.0.1
 *
 */
@Entity
@Table(name = "schedule")
public class Schedule {
	
	@Id
	@Column(name = "schedule_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long scheduleId;
	
	@ManyToOne
	@JoinColumn(name = "train_id")
	private Train train;
	
	@ManyToOne
	@JoinColumn(name = "station_id")
	private Station station;
	
	@Column(name = "arrival_time", nullable = true)
	private Timestamp arrivalTime;
	
	@Column(name = "departure_time", nullable = true)
	private Timestamp departureTime;
	
	public Schedule() {
	}

	public long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Timestamp getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Timestamp getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Timestamp departureTime) {
		this.departureTime = departureTime;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	
}
