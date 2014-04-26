package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.UserInfo;

public interface UserInfoService {

	public abstract List<UserInfo> findAllUserInfos();

	public abstract UserInfo findUserInfo(Integer id);

}
