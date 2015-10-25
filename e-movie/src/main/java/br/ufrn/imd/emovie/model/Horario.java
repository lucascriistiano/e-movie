package br.ufrn.imd.emovie.model;

import java.io.Serializable;
import java.lang.Integer;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Horario
 *
 */
@Entity
@Table(schema="public", name="horario")
public class Horario implements Serializable {

	@Id
	@GeneratedValue
	@Column(name="id_horario")
	private Integer id;
	private Integer dayWeek;
	private Integer hour;
	@ManyToMany(mappedBy="horarios", fetch=FetchType.LAZY)
	private List<Movie> movies;
	private static final long serialVersionUID = 1L;

	public Horario() {
		super();
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
	public List<Movie> getMovies() {
		return movies;
	}
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
   
}
