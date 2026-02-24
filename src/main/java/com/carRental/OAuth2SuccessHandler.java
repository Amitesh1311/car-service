package com.carRental;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.carRental.dto.AuthResponse;
import com.carRental.entity.UserInfo;
import com.carRental.enums.UserRole;
import com.carRental.repository.UserRepository;
import com.carRental.services.JwtService;
import com.carRental.services.UserInfoUserDetailsService;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	
	@Autowired
	public UserRepository userRepository;

	@Autowired
	public UserInfoUserDetailsService userInfoUserDetailsService;
	
	@Autowired
	public JwtService jwtService;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        System.out.println("Login Success!");
        
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
		Optional<UserInfo> userObj = userRepository.findByEmail(email);
		if(userObj.isEmpty()) {
			UserInfo user = new UserInfo();

			user.setEmail(email);
			user.setName(name);
			user.setPassword(null);
			user.setUserRole(UserRole.CUSTOMER);

			UserInfo retUser = userRepository.save(user);
		}
		UserDetails userDetails = userInfoUserDetailsService.loadUserByUsername(email);
		String token = jwtService.generateToken(userDetails);
		
		
		
		Optional<UserInfo> userObj2 = userRepository.findByEmail(email);
        
        new DefaultRedirectStrategy().sendRedirect(request, response, "http://localhost:4200/oauth-success?token=" + token
                + "&id=" + userObj2.get().getId()
                + "&role=" + userObj2.get().getUserRole());
    }
}


