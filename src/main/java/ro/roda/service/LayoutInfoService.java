package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.LayoutInfo;

public interface LayoutInfoService {

	public abstract List<LayoutInfo> findAllLayoutInfos();

	public abstract LayoutInfo findLayoutInfo(Integer id);

}
