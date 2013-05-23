package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.Region;


public interface RegionService {

	public abstract long countAllRegions();


	public abstract void deleteRegion(Region region);


	public abstract Region findRegion(Integer id);


	public abstract List<Region> findAllRegions();


	public abstract List<Region> findRegionEntries(int firstResult, int maxResults);


	public abstract void saveRegion(Region region);


	public abstract Region updateRegion(Region region);

}
