package uni.masters.udemy.controller;

import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import uni.masters.udemy.UserPrincipal;
import uni.masters.udemy.WebSecurityConfig;
import uni.masters.udemy.bean.UserBean;
import uni.masters.udemy.exceptions.AlreadyExistException;
import uni.masters.udemy.repo.UserRepo;

@RestController
public class HomeController {

	private UserRepo userRepo;
	private WebSecurityConfig webSecurityConfig;
	
	public HomeController(UserRepo userRepo, WebSecurityConfig webSecurityConfig) {
		this.userRepo = userRepo;
		this.webSecurityConfig = webSecurityConfig;
	}
	
	
	@PostMapping(path = "/register")
	public ResponseEntity<UserBean> register(
			@RequestParam(value ="email")String email,
			@RequestParam(value ="firstName")String firstName,
			@RequestParam(value ="lastName")String lastName,
			@RequestParam(value ="imagePath")String imagePath, 
			@RequestParam(value ="username")String username, 
			@RequestParam(value ="password")String password,
			@RequestParam(value ="confirmPass")String confirmPass) {
		
		if(userRepo.findByEmail(email) != null){
			throw new AlreadyExistException(UserBean.class.getSimpleName(), "email", email);
		}
		
		if(password.equals(confirmPass)) {
			UserBean user = new UserBean(username, hashPassword(password), email, firstName, lastName, imagePath);
			user.setCardMoney(100);
			
			userRepo.saveAndFlush(user);

			return new ResponseEntity<>(user,HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<UserBean> login(
			@RequestParam(value = "email")
			String email, 
			@RequestParam(value = "password")
			String password, HttpSession session) {
		
		UserBean user = userRepo.findByEmailAndPasswordHash(email, hashPassword(password));
		
		if(user != null) {
			session.setAttribute("user", user);
			
			try {
				UserDetails userDetails = 
						webSecurityConfig.userDetailsServiceBean().
						loadUserByUsername(user.getUsername());
				
				if(userDetails != null) {
					Authentication auth = new UsernamePasswordAuthenticationToken(
							userDetails.getUsername(),
							userDetails.getPassword(),
							userDetails.getAuthorities());
					
					SecurityContextHolder.getContext().setAuthentication(auth);
					
					ServletRequestAttributes attr = (ServletRequestAttributes)
							RequestContextHolder.currentRequestAttributes();
					
					HttpSession http = attr.getRequest().getSession(true);
					http.setAttribute("SPRING_SECURITY_CONTEXT", 
							SecurityContextHolder.getContext());
				}
				
				return new ResponseEntity<>(user,HttpStatus.OK);
				
			} catch (UsernameNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				
	}
	
	@GetMapping(path = "/getCurrentUser")
	public ResponseEntity<UserBean> getCurrentUser(HttpSession session){
		
		UserBean user = (UserBean)session.getAttribute("user");
		
		if(user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}		
	}
	
	@PostMapping(path = "/logout")
	public ResponseEntity<Boolean> logout(HttpSession session){
		
		UserBean user = (UserBean) session.getAttribute("user");
		
		if(user != null) {
			session.invalidate();
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
	}
	
	
private String hashPassword(String password) {
		
		StringBuilder result = new StringBuilder();
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			md.update(password.getBytes());
			
			byte[] bytes = md.digest();
			
			for(int i = 0; i < bytes.length; i++) {
				result.append((char)bytes[i]);
			}			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}		
	
		return result.toString();
	}
}

