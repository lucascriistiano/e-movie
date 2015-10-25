package br.ufrn.imd.emovie.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Movie
 *
 */
@Entity
@Table(schema="public", name="movie")
public class Movie implements Serializable {

	@Id
	@GeneratedValue
	@Column(name="id_movie")
	private Integer id;
	private String name;
	private Date startExibition;
	private Date endExibition;
	@ManyToMany
    @JoinTable(name = "horario_movie", joinColumns = @JoinColumn(name = "id_movie"), inverseJoinColumns = @JoinColumn(name = "id_horario"))
	private List<Horario> horarios;
	private static final long serialVersionUID = 1L;

	public Movie() {
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
	public Date getEndExibition() {
		return endExibition;
	}
	public void setEndExibition(Date endExibition) {
		this.endExibition = endExibition;
	}
	public Date getStartExibition() {
		return startExibition;
	}
	public void setStartExibition(Date startExibition) {
		this.startExibition = startExibition;
	}
	public List<Horario> getHorarios() {
		return horarios;
	}
	public void setHorarios(List<Horario> horarios) {
		this.horarios = horarios;
	}
   
}
