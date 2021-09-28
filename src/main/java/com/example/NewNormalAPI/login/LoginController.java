package com.example.NewNormalAPI.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.NewNormalAPI.user.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.NewNormalAPI.jwt.util.JwtUtil;
import com.example.NewNormalAPI.jwt.models.AuthenticationResponse;
import com.example.NewNormalAPI.jwt.models.AuthenticationRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

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
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword());
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    // TODO Remove this method: testing jwt token receiving
    @RequestMapping({ "/hello" })
    public String firstPage() {
        return "Hello World";
    }

}