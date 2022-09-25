package com.festival.model;
import javax.persistence.*;

@Entity
public class Dispenser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	
	@Column(name = "current_opened", columnDefinition = "boolean")
	private boolean current_opened;
	
	@Column(name = "flow_volume")
	private float flow_volume;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public float getFlow_volume() {
		return flow_volume;
	}
	public void setFlow_volume(float flow_volume) {
		this.flow_volume = flow_volume;
	}
	public boolean isCurrent_opened() {
		return current_opened;
	}
	public void setCurrent_opened(boolean current_opened) {
		this.current_opened = current_opened;
	}
}
