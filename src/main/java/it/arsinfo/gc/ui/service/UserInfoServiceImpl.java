package it.arsinfo.gc.ui.service;

import it.arsinfo.gc.entity.dao.UserInfoDao;
import it.arsinfo.gc.entity.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserInfoDao dao;

    @Override
    public UserInfo save(UserInfo entity)  {
        if (entity == null) {
            log.error("save: null entity cannot be saved");
            return null;
        }
        return dao.save(entity);
    }

    @Override
    public void delete(UserInfo entity) { dao.delete(entity); }

    @Override
    public UserInfo findById(Long id)  {
        return dao.findById(id).orElse(null);
    }

    @Override
    public List<UserInfo> findAll() {
        return dao.findAll();
    }

    @Override
    public long count() {
        return dao.count();
    }

    @Override
    public UserInfo add() {  return new UserInfo(); }

    public List<UserInfo> findAll(String searchText, UserInfo.Role role) {
        if (searchText == null && role == null) {
            return findAll();
        }
        if (searchText == null) {
            return dao.findByRole(role);
        }
        if (role == null ) {
            return dao.findByUsernameContainingIgnoreCase(searchText);
        }
        return dao.findByUsernameContainingIgnoreCaseAndRole(searchText, role);
    }

    @Override
    public UserInfo findByUsername(String name) {
        return dao.findByUsername(name);
    }

}
