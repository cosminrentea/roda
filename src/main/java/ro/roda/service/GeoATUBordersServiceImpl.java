package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.GeoATUBorders;

@Service
@Transactional
public class GeoATUBordersServiceImpl implements GeoATUBordersService {

	public List<GeoATUBorders> findAllGeoCitiesBorders() {
		return GeoATUBorders.findAllGeoCitiesBorders();
	}

	public GeoATUBorders findGeoCitiesBorders(Integer id) {
		return GeoATUBorders.findGeoCityBorder(id);
	}
}
