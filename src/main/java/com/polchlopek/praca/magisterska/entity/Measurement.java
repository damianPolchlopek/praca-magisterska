package com.polchlopek.praca.magisterska.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="measurement")
public class Measurement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="date_measurement")
	private Date dateMeasurement;

	@Column(name="description")
	private String description;

	@ManyToOne(cascade= {CascadeType.ALL})
	@JoinColumn(name="category_id")
	private MeasurementCategory category;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
						 CascadeType.DETACH, CascadeType.REFRESH,})
	@JoinColumn(name="user_id")
	private User userID;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade= {CascadeType.ALL})
	@JoinColumn(name="id_meas")
	private List<MeasurementData> nodes;


	public Measurement() {
	
	}

	public Measurement(Date dateMeasurement, String description) {
		this.dateMeasurement = dateMeasurement;
		this.description = description;
	}


	public List<MeasurementData> getNodes() {
		return nodes;
	}

	public void setNodes(List<MeasurementData> nodes) {
		this.nodes = nodes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateMeasurement() {
		return dateMeasurement;
	}

	public void setDateMeasurement(Date dateMeasurement) {
		this.dateMeasurement = dateMeasurement;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MeasurementCategory getCategory() {
		return category;
	}

	public void setCategory(MeasurementCategory category) {
		this.category = category;
	}

	public User getUserID() {
		return userID;
	}

	public void setUserID(User userID) {
		this.userID = userID;
	}

	public void addNode(MeasurementData theNode) {

		if (nodes == null) {
			nodes = new ArrayList<>();
		}

		nodes.add(theNode);
	}
	
	public String toString() {
		return "Meas [id=" + id + ", dateMeasurement=" + dateMeasurement + ", description= " + description +
				", userID=" + userID + ", node: " + nodes + "]";
	}

}
