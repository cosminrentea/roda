package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the demo_slices database table.
 * 
 */
@Entity
@Table(name="demo_slices")
public class DemoSlice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="age_max", nullable=false)
	private Integer ageMax;

	@Column(name="age_min", nullable=false)
	private Integer ageMin;

	@Column(nullable=false)
	private Integer sex;

	//bi-directional many-to-one association to DemoScope
    @ManyToOne
	@JoinColumn(name="demoscope_id", nullable=false)
	private DemoScope demoScope;

	//bi-directional many-to-one association to GeoScope
    @ManyToOne
	@JoinColumn(name="geo_scope_id", nullable=false)
	private GeoScope geoScope;

    public DemoSlice() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAgeMax() {
		return this.ageMax;
	}

	public void setAgeMax(Integer ageMax) {
		this.ageMax = ageMax;
	}

	public Integer getAgeMin() {
		return this.ageMin;
	}

	public void setAgeMin(Integer ageMin) {
		this.ageMin = ageMin;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public DemoScope getDemoScope() {
		return this.demoScope;
	}

	public void setDemoScope(DemoScope demoScope) {
		this.demoScope = demoScope;
	}
	
	public GeoScope getGeoScope() {
		return this.geoScope;
	}

	public void setGeoScope(GeoScope geoScope) {
		this.geoScope = geoScope;
	}
	
}