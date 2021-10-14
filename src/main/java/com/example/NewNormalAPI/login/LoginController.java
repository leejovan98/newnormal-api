package com.example.NewNormalAPI.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.NewNormalAPI.jwt.models.AuthenticationRequest;
import com.example.NewNormalAPI.jwt.util.JwtUtil;
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
     * User login authentication
     * 
     * @return true if user logs in successfully
     */
    
    // TODO Might need to remove user authenticate method from
    // UserDetailsServiceImpl
    // @GetMapping("/login")
    // public User userLogin(@RequestBody User user) {
    // return userService.authenticate(user);
    // }

    // TODO POTENTIAL ISSUE: Unverified people can log in
//    @RequestMapping(value = "/accounts/login", method = RequestMethod.POST)
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
//    	try {
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                    authenticationRequest.getUsername(), authenticationRequest.getPassword());
//            authenticationManager.authenticate(authenticationToken);
//        } catch (BadCredentialsException e) {
////            throw new Exception("Incorrect username or password", e);
//        	throw new LoginFailedException();
//        }
//
//        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
//        if(!userDetails.isEnabled()) {
//        	throw new UserNotVerifiedException();
//        }
//        final String jwt = jwtTokenUtil.generateToken(userDetails);
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));
//    }
    
    
    // TODO: remove lated -- for cookie testing only
    @RequestMapping(value = "/accounts/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,  HttpServletResponse response){
    	try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword());
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
//            throw new Exception("Incorrect username or password", e);
        	throw new LoginFailedException();
        }

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        if(!userDetails.isEnabled()) {
        	throw new UserNotVerifiedException();
        }
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        
        Cookie cookie = new Cookie("jwt", jwt);
		// expires in 7 days
//	    cookie.setMaxAge(7 * 24 * 60 * 60);
//	    cookie.setPath("/");
	    response.addCookie(cookie);

		return new ResponseEntity<>(HttpStatus.OK);
    }

}
