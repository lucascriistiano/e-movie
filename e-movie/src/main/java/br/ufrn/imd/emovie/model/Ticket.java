package br.ufrn.imd.emovie.model;

import br.ufrn.imd.emovie.model.Movie;
import br.ufrn.imd.emovie.model.User;
import java.io.Serializable;
import java.lang.Integer;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Ticket
 *
 */
@Entity
@Table(schema="public", name="ticket")
public class Ticket implements Serializable {

	@Id
	@GeneratedValue
	@Column(name="id_ticket")
	private Integer id;
	private Integer quantity;
	private Date date;
	private Date createdAt;
	@ManyToOne
    @JoinColumn(name = "id_movie")
	private Movie movie;
	@ManyToOne
    @JoinColumn(name = "id_user")
	private User user;
	private static final long serialVersionUID = 1L;

	public Ticket() {
		super();
	}   
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}   
	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}   
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}   
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}   
	public Movie getMovie() {
		return this.movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}   
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
   
}
