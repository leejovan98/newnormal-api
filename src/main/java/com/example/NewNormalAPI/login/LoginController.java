package com.example.NewNormalAPI.login;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.NewNormalAPI.jwt.models.AuthenticationRequest;
import com.example.NewNormalAPI.jwt.util.JwtUtil;
import com.example.NewNormalAPI.user.User;
import com.example.NewNormalAPI.user.UserDetailsServiceImpl;
import com.example.NewNormalAPI.user.UserNotVerifiedException;

@RestController
public class LoginController {
    private UserDetailsServiceImpl userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    public LoginController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * Creates authentication token
     * 
     * @param authenticationRequest
     * @param response
     * @return login DTO
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/accounts/login", method = RequestMethod.POST)
    public LoginDto createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,  HttpServletResponse response){
    	try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword());
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
        	throw new LoginFailedException();
        }

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        if(!userDetails.isEnabled()) {
        	throw new UserNotVerifiedException();
        }
        String jwt = jwtTokenUtil.generateToken(userDetails);
        User user = userService.loadUserEntityByUsername(authenticationRequest.getUsername());
        
        return new LoginDto(jwt, user);
        
       
    }

}
