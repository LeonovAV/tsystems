package com.tsystems.rts.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "worker", uniqueConstraints = @UniqueConstraint(columnNames = "login"))
public class Worker {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "worker_id")
	private long workerId;
	
	@Column(name = "login")
	@NotNull(message = "Login can not be empty")
	@Size(min = 1, max = 255)
	private String login;
	
	@Column(name = "password")
	@NotNull(message = "Password can not be empty")
	@Size(min = 1, max = 255)
	private String password;

	public Worker() {
	}
	
	public long getWorkerId() {
		return workerId;
	}

	public void setWorkerId(long workerId) {
		this.workerId = workerId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Worker [workerId=" + workerId + ", login=" + login + "]";
	}
	
}
