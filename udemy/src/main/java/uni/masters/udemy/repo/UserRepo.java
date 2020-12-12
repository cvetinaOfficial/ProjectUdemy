package uni.masters.udemy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import uni.masters.udemy.bean.UserBean;

@EnableJpaRepositories
@Repository
public interface UserRepo extends JpaRepository<UserBean, Integer>{

	UserBean findUserByUsernameAndPasswordHash(String username, String passwordHash);
	
	UserBean findByEmailAndPasswordHash(String email, String passwordHash);
	
	UserBean findByUsername(String username);
	
	UserBean findByEmail(String email);
}
