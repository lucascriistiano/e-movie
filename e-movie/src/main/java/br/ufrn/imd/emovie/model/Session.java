package br.ufrn.imd.emovie.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Session
 *
 */
@Entity
@Table(schema = "public", name = "session")
public class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_session")
	private Integer id;
	
	@Column(name = "day_week")
	private Integer dayWeek;
	private Integer hour;

	public Session() {
		super();
	}
	
	public Session(Integer dayWeek, Integer hour) {
		this();
		this.dayWeek = dayWeek;
		this.hour = hour;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDayWeek() {
		return this.dayWeek;
	}

	public void setDayWeek(Integer dayWeek) {
		this.dayWeek = dayWeek;
	}

	public Integer getHour() {
		return this.hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

}
