package com.polchlopek.praca.magisterska.entity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name="login")
public class Login {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="date")
	private Date dateLog;
	
	@Column(name="time")
	private Time timeLog;
	
	@Column(name="location")
	private String locationLog;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
						 CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	private User userID;
	
	public Login() {

	}

	public Login(Date dateLog, Time timeLog, String locationLog, User userID) {
		this.dateLog = dateLog;
		this.timeLog = timeLog;
		this.locationLog = locationLog;
		this.userID = userID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateLog() {
		return dateLog;
	}

	public void setDateLog(Date dateLog) {
		this.dateLog = dateLog;
	}
	
	public Time getTimeLog() {
		return timeLog;
	}

	public void setTimeLog(Time timeLog) {
		this.timeLog = timeLog;
	}

	public String getLocationLog() {
		return locationLog;
	}

	public void setLocationLog(String locationLog) {
		this.locationLog = locationLog;
	}

	public User getUserID() {
		return userID;
	}

	public void setUserID(User userID) {
		this.userID = userID;
	}
	
	public String toString() {
		return "Login [id=" + id + ", dateLog=" + dateLog + ", locationLog=" + locationLog +
				", userID=" + userID + "]";
	}

}
