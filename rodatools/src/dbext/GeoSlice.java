package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the geo_slices database table.
 * 
 */
@Entity
@Table(name = "geo_slices")
public class GeoSlice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false)
	private Integer city;

	@Column(nullable = false)
	private Integer country;

	// bi-directional many-to-one association to GeoScope
	@ManyToOne
	@JoinColumn(name = "geoscope_id", nullable = false)
	private GeoScope geoScope;

	public GeoSlice() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCity() {
		return this.city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getCountry() {
		return this.country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public GeoScope getGeoScope() {
		return this.geoScope;
	}

	public void setGeoScope(GeoScope geoScope) {
		this.geoScope = geoScope;
	}

}