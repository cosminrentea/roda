package ro.roda.service;

import java.util.List;

import ro.roda.transformer.LayoutGroupInfo;

public interface LayoutGroupInfoService {

	public abstract List<LayoutGroupInfo> findAllLayoutGroupInfos();

	public abstract LayoutGroupInfo findLayoutGroupInfo(Integer id);

}
