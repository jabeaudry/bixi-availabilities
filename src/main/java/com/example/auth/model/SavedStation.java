package com.example.auth.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "saved")
public class SavedStation {
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private int id;

//	@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name="user_id", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	@JsonBackReference
	private AppUser user;
	
	
	  private long bikeNumber;

	  public SavedStation() {
	  }
	  
	  public SavedStation(int id, long bikeNumber) {
		  this.id = id;
		  this.bikeNumber = bikeNumber;
	  }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getBikeNumber() {
		return bikeNumber;
	}

	public void setBikeNumber(long bikeNumber) {
		this.bikeNumber = bikeNumber;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}
	public String getEmail() {
		return user.getEmail();
	};
	  
	
}
