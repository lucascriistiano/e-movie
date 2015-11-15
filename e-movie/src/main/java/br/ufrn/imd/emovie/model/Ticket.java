package br.ufrn.imd.emovie.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Ticket
 *
 */
@Entity
@Table(schema = "public", name = "ticket")
public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_ticket")
	private Integer id;
	private String token;
	
	@OneToOne
	@JoinColumn(name = "id_exhibition")
	private Exhibition exhibition;
	
	@Column(name = "chair_num")
	private String chairNumber;
	
	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;
	
	@Column(name = "purchase_location")
	@Enumerated(EnumType.ORDINAL)
	private PurchaseLocation purchaseLocation;
	
	@Column(name = "created_at")
	private Date createdAt;

	public Ticket() {
		super();
	}
	
	public Ticket(Exhibition exhibition, String chairNumber, User user, PurchaseLocation purchaseLocation, Date createdAt) {
		this();
		this.exhibition = exhibition;
		this.chairNumber = chairNumber;
		this.user = user;
		this.purchaseLocation = purchaseLocation;
		this.createdAt = createdAt;
	}
	
	public Ticket(Integer id, Exhibition exhibition, String chairNumber, User user, PurchaseLocation purchaseLocation, Date createdAt) {
		this(exhibition, chairNumber, user, purchaseLocation, createdAt);
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public Exhibition getExhibition() {
		return exhibition;
	}

	public void setExhibition(Exhibition exhibition) {
		this.exhibition = exhibition;
	}
	
	public String getChairNumber() {
		return chairNumber;
	}

	public void setChairNumber(String chairNumber) {
		this.chairNumber = chairNumber;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public PurchaseLocation getPurchaseLocation() {
		return purchaseLocation;
	}

	public void setPurchaseLocation(PurchaseLocation purchaseLocation) {
		this.purchaseLocation = purchaseLocation;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}