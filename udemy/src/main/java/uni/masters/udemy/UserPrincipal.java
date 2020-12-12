package uni.masters.udemy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import uni.masters.udemy.bean.RoleBean;
import uni.masters.udemy.bean.UserBean;

public class UserPrincipal implements UserDetails{
	
	private UserBean user;
	private Set<GrantedAuthority> authorities;	

	public UserPrincipal(UserBean user, Set<RoleBean> roles) {
		this.user = user;
		authorities = new HashSet<GrantedAuthority>();
		insertRoles(roles);
	}

	private void insertRoles(Set<RoleBean> roles) {
	
		for(RoleBean role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		if(authorities.isEmpty()) {
			authorities.add(new SimpleGrantedAuthority("User"));
		}		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPasswordHash();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}

