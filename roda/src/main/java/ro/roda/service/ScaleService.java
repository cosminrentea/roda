package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.Scale;


public interface ScaleService {

	public abstract long countAllScales();


	public abstract void deleteScale(Scale scale);


	public abstract Scale findScale(Long id);


	public abstract List<Scale> findAllScales();


	public abstract List<Scale> findScaleEntries(int firstResult, int maxResults);


	public abstract void saveScale(Scale scale);


	public abstract Scale updateScale(Scale scale);

}
