package com.example.NewNormalAPI.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
    private UserRepository users;
    
    @Autowired
    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }
    
    @Override
    public User loadUserByUsername(String username)  throws UsernameNotFoundException {
        return users.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
    }
    
    public User loadUserByEmail(String email) throws AccNotFoundException {
        return users.findByEmail(email)
                .orElseThrow(() -> new AccNotFoundException());
    }

    // TODO: finalise the return type JWT?
    public User authenticate(User user){
        User actualUser = loadUserByEmail(user.getEmail());
        System.out.println(actualUser.toString());
        if(!actualUser.getVerified().equals("Y")){
            throw new LoginFailedException("Account not verified");
        }
        
        if(!(BCrypt.checkpw(user.getPassword(), actualUser.getPassword()))){
            System.out.println("WRONG PASSWORD");
            throw new LoginFailedException("Wrong password");
        }

        return actualUser;
        // at this point user is authenticated
        // what to return?
    }
    
    public User update(User user) {
    	return users.save(user);
    }

    public User createUser(User user) {
        Optional<User> search = users.findByUsername(user.getUsername());
        Optional<User> search2 = users.findByEmail(user.getEmail());
  
        if (search.isPresent() || search2.isPresent()) {
        	throw new UserExistsException();
        }
        
        return users.save(user);
       }
    }