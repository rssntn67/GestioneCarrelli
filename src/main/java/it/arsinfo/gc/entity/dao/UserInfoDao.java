package it.arsinfo.gc.entity.dao;

import java.util.List;

import it.arsinfo.gc.entity.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserInfoDao extends JpaRepository<UserInfo, Long>{
    UserInfo findByUsername(String username);
    List<UserInfo> findByUsernameContainingIgnoreCase(String username);
    List<UserInfo> findByRole(UserInfo.Role role);
    List<UserInfo> findByUsernameContainingIgnoreCaseAndRole(String username, UserInfo.Role role);
}
