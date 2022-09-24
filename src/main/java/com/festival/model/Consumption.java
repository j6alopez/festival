package com.festival.model;

import javax.persistence.*;

@Entity
public class Consumption {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	@Column(name = "id_transaction")
	private int id;
	
	@Column(name = "id_dispenser")
	private int idDispenser;
	
	@Column(name = "tap_insant_opened")
	private String tapInstantOpened;
	
	@Column(name = "tap_insant_closed")
	private String tapInstantClosed;

	@Column(name = "ended",columnDefinition = "boolean")
	
	private boolean ended;

	public Consumption() {}
	
	public Consumption(int idDispenser) {
		this.idDispenser = idDispenser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdDispenser() {
		return idDispenser;
	}

	public void setIdDispenser(int idDispenser) {
		this.idDispenser = idDispenser;
	}

	public String getTapInstantOpened() {
		return tapInstantOpened;
	}

	public void setTapInstantOpened(String tapInstantOpend) {
		this.tapInstantOpened = tapInstantOpend;
	}

	public String getTapInstantClosed() {
		return tapInstantClosed;
	}

	public void setTapInstantClosed(String tapInstantClosed) {
		this.tapInstantClosed = tapInstantClosed;
	}

	public boolean isEnded() {
		return ended;
	}

	public void setEnded(boolean ended) {
		this.ended = ended;
	}
}
