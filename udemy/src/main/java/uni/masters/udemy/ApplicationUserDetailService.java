package uni.masters.udemy;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uni.masters.udemy.bean.RoleBean;
import uni.masters.udemy.bean.UserBean;
import uni.masters.udemy.repo.UserRepo;

@Service
public class ApplicationUserDetailService implements UserDetailsService{
	
	private UserRepo userRepo;
	
	public ApplicationUserDetailService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserBean user = userRepo.findByUsername(username);
		
		if(user == null)
			throw new UsernameNotFoundException(username);
		
		Set<RoleBean> roles = user.getRoles();
		
		return new UserPrincipal(user, roles);		
	}

}
