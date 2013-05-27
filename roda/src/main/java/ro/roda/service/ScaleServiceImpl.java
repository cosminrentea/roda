package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Scale;

@Service
@Transactional
public class ScaleServiceImpl implements ScaleService {

	public long countAllScales() {
		return Scale.countScales();
	}

	public void deleteScale(Scale scale) {
		scale.remove();
	}

	public Scale findScale(Long id) {
		return Scale.findScale(id);
	}

	public List<Scale> findAllScales() {
		return Scale.findAllScales();
	}

	public List<Scale> findScaleEntries(int firstResult, int maxResults) {
		return Scale.findScaleEntries(firstResult, maxResults);
	}

	public void saveScale(Scale scale) {
		scale.persist();
	}

	public Scale updateScale(Scale scale) {
		return scale.merge();
	}
}
