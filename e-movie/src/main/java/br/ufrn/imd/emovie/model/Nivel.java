package br.ufrn.imd.emovie.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Nivel
 *
 */
@Entity
@Table(schema="public", name="nivel")
public class Nivel implements Serializable {

	@Id
	@GeneratedValue
	@Column(name="id_nivel")
	private Integer id;
	private String name;
	private static final long serialVersionUID = 1L;

	public Nivel() {
		super();
	}   
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
}
