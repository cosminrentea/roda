package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.GeoATUBorders;

public interface GeoATUBordersService {

	public abstract List<GeoATUBorders> findAllGeoCitiesBorders();

	public abstract GeoATUBorders findGeoCitiesBorders(Integer id);

}
