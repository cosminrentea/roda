package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the geo_scope database table.
 * 
 */
@Entity
@Table(name = "geo_scope")
public class GeoScope implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false)
	private String description;

	@Column(name = "geo_slice_id", nullable = false)
	private Integer geoSliceId;

	// bi-directional many-to-one association to DemoSlice
	@OneToMany(mappedBy = "geoScope")
	private List<DemoSlice> demoSlices;

	// bi-directional many-to-one association to GeoSlice
	@OneToMany(mappedBy = "geoScope")
	private List<GeoSlice> geoSlices;

	public GeoScope() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getGeoSliceId() {
		return this.geoSliceId;
	}

	public void setGeoSliceId(Integer geoSliceId) {
		this.geoSliceId = geoSliceId;
	}

	public List<DemoSlice> getDemoSlices() {
		return this.demoSlices;
	}

	public void setDemoSlices(List<DemoSlice> demoSlices) {
		this.demoSlices = demoSlices;
	}

	public List<GeoSlice> getGeoSlices() {
		return this.geoSlices;
	}

	public void setGeoSlices(List<GeoSlice> geoSlices) {
		this.geoSlices = geoSlices;
	}

}