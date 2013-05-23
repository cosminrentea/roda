package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Region;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {

	public long countAllRegions() {
		return Region.countRegions();
	}

	public void deleteRegion(Region region) {
		region.remove();
	}

	public Region findRegion(Integer id) {
		return Region.findRegion(id);
	}

	public List<Region> findAllRegions() {
		return Region.findAllRegions();
	}

	public List<Region> findRegionEntries(int firstResult, int maxResults) {
		return Region.findRegionEntries(firstResult, maxResults);
	}

	public void saveRegion(Region region) {
		region.persist();
	}

	public Region updateRegion(Region region) {
		return region.merge();
	}
}
