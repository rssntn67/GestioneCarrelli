package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.model.UserInfo;

import java.util.List;

public interface UserInfoService extends EntityService<UserInfo> {

    List<UserInfo> findAll(String searchText, UserInfo.Role role);
    UserInfo findByUsername(String name);
}
