package mariana.springbackend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.SignatureAlgorithm;
import mariana.springbackend.SpringApplicationContext;
import mariana.springbackend.models.requests.UserLoginRequestModel;
import io.jsonwebtoken.Jwts;
import mariana.springbackend.services.UserServiceInterface;
import mariana.springbackend.share.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginRequestModel userModel = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getEmail(),
                    userModel.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException, ServletException {
        String username = ((User) authentication.getPrincipal()).getUsername();
        // Create token
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
        // Add header with public ID
        UserServiceInterface userService = (UserServiceInterface) SpringApplicationContext.getBean("userService"); // if the class is called UserServices, the bean will be called userService
        // Create an interface method for the header
        UserDto userDto = userService.getUser(username);

        // Let know we are going to have other headers
        response.addHeader("Access-Control-Expose-headers", "Authorization, userId");
        response.addHeader("UserId", userDto.getUserId());
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        LOGGER.info("AuthenticationFilter - successfulAuthentication" + response);
    }
}
