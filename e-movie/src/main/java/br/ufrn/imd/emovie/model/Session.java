package br.ufrn.imd.emovie.model;

import java.io.Serializable;
import java.util.Date;

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
	private String dayWeek;
	private Date hour;

	public Session() {
		super();
	}
	
	public Session(String dayWeek, Date hour) {
		this();
		this.dayWeek = dayWeek;
		this.hour = hour;
	}
	
	public Session(Integer id, String dayWeek, Date hour) {
		this(dayWeek, hour);
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDayWeek() {
		return this.dayWeek;
	}

	public void setDayWeek(String dayWeek) {
		this.dayWeek = dayWeek;
	}

	public Date getHour() {
		return this.hour;
	}

	public void setHour(Date hour) {
		this.hour = hour;
	}

}
