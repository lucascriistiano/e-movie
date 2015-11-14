package br.ufrn.imd.emovie.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Room
 *
 */
@Entity
@Table(schema = "public", name = "room")
public class Room implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "id_room")
	private Integer id;
	private Integer rows;

	public Room() {
		super();
	}
	
	public Room(Integer rows) {
		this();
		this.rows = rows;
	}
	
	public Room(Integer id, Integer rows) {
		this();
		this.id = id;
		this.rows = rows;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRows() {
		return rows;
	}

	public void setCapacity(Integer rows) {
		this.rows = rows;
	}

}
