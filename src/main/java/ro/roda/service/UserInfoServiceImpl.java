package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.UserInfo;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

	public List<UserInfo> findAllUserInfos() {
		return UserInfo.findAllUserInfos();
	}

	public UserInfo findUserInfo(Integer id) {
		return UserInfo.findUserInfo(id);
	}
}
