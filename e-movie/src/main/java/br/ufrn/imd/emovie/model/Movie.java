package br.ufrn.imd.emovie.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Movie
 *
 */
@Entity
@Table(schema = "public", name = "movie")
public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_movie")
	private Integer id;
	private String name;
	
	@Column(length=10000)
	private String synopsis;

	private String advertisement;
	private String image;

	@Column(name = "start_exhibition")
	private Date startExhibition;

	@Column(name = "end_exhibition")
	private Date endExhibition;

	public Movie() {
		super();
	}

	public Movie(String name, String synopsis, String advertisement, String image, Date startExhibition, Date endExhibition) {
		this();
		this.name = name;
		this.synopsis = synopsis;
		this.advertisement = advertisement;
		this.image = image;
		this.startExhibition = startExhibition;
		this.endExhibition = endExhibition;
	}

	public Movie(Integer id, String name, String synopsis, String advertisement, String image, Date startExhibition, Date endExhibition) {
		this(name, synopsis, advertisement, image, startExhibition, endExhibition);
		this.id = id;
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

	public String getSynopsis() {
		return synopsis;
	}

	public String getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(String advertisement) {
		this.advertisement = advertisement;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public Date getEndExhibition() {
		return endExhibition;
	}

	public void setEndExhibition(Date endExhibition) {
		this.endExhibition = endExhibition;
	}

	public Date getStartExhibition() {
		return startExhibition;
	}

	public void setStartExhibition(Date startExhibition) {
		this.startExhibition = startExhibition;
	}

}
