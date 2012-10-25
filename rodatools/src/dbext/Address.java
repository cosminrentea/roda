package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the address database table.
 * 
 */
@Entity
@Table(name="address")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=250)
	private String address1;

	@Column(nullable=false, length=250)
	private String address2;

	@Column(name="entity_id", nullable=false)
	private Integer entityId;

	@Column(name="entity_type", nullable=false)
	private Integer entityType;

	@Column(name="postal_code", nullable=false, length=50)
	private String postalCode;

	@Column(nullable=false, length=50)
	private String sector;

	//bi-directional many-to-one association to City
    @ManyToOne
	@JoinColumn(name="city_id", nullable=false)
	private City city;

	//bi-directional many-to-one association to Country
    @ManyToOne
	@JoinColumn(name="country_id", nullable=false)
	private Country country;

	//bi-directional many-to-one association to OrgAddress
	@OneToMany(mappedBy="address")
	private List<OrgAddress> orgAddresses;

	//bi-directional many-to-one association to PersonAddress
	@OneToMany(mappedBy="address")
	private List<PersonAddress> personAddresses;

    public Address() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public Integer getEntityId() {
		return this.entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public Integer getEntityType() {
		return this.entityType;
	}

	public void setEntityType(Integer entityType) {
		this.entityType = entityType;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getSector() {
		return this.sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	public List<OrgAddress> getOrgAddresses() {
		return this.orgAddresses;
	}

	public void setOrgAddresses(List<OrgAddress> orgAddresses) {
		this.orgAddresses = orgAddresses;
	}
	
	public List<PersonAddress> getPersonAddresses() {
		return this.personAddresses;
	}

	public void setPersonAddresses(List<PersonAddress> personAddresses) {
		this.personAddresses = personAddresses;
	}
	
}