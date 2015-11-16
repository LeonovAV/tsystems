package com.tsystems.rts.entities;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Class describes station entity.
 * @author Anton
 * @version 0.0.1
 *
 */
@Entity
@Table(name = "station", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Station {
	
	@Id
	@Column(name = "station_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long stationId;
	
	@Column(name = "name")
	@NotNull(message = "Each station must have name")
	private String name;
	
	@ManyToMany
	@JoinTable(name = "route", joinColumns = { @JoinColumn(name = "station_id", nullable = false) },
			   inverseJoinColumns = { @JoinColumn(name = "train_id", nullable = false) })
	private List<Train> trains = new ArrayList<Train>();
	
	@OneToMany(mappedBy = "station")
	private List<Schedule> schedules = new ArrayList<Schedule>();
	
	public Station() {
	}
	
	public long getStationId() {
		return stationId;
	}
	
	public void setStationId(long stationId) {
		this.stationId = stationId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<Train> getTrains() {
		return trains;
	}

	public void setTrains(List<Train> trains) {
		this.trains = trains;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
	
}
