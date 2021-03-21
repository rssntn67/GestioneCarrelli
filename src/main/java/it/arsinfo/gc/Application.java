package it.arsinfo.gc;

import it.arsinfo.gc.entity.dao.UserInfoDao;
import it.arsinfo.gc.entity.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class Application extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner loadData(
            @Autowired UserInfoDao userInfoDao,
            @Autowired PasswordEncoder passwordEncoder) {
        return (args) -> {
            UserInfo dash = userInfoDao.findByUsername("dash");
            if (dash == null) {
                dash = new UserInfo("dash", passwordEncoder.encode("Dash009!!"), UserInfo.Role.DASHBOARD);
                userInfoDao.save(dash);
                log.info("creato user dash/Dash009!! ROLE DASHBOARD");
            }

            UserInfo user = userInfoDao.findByUsername("user");
            if (user == null) {
                user = new UserInfo("user", passwordEncoder.encode("User008!!"), UserInfo.Role.USER);
                userInfoDao.save(user);
                log.info("creato user user/User008!! ROLE USER");
            }

            UserInfo administrator = userInfoDao.findByUsername("admin");
            if (administrator == null) {
                administrator = new UserInfo("admin", passwordEncoder.encode("Admin007!!"), UserInfo.Role.ADMIN);
                userInfoDao.save(administrator);
                log.info("creato user admin/Admin007!! ROLE ADMIN");
            }

        };
    }

}
