package com.example.NewNormalAPI.user;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository users;

    @Autowired
    public UserDetailsServiceImpl(UserRepository users) {
        this.users = users;
    }

    /**
     * Updates user
     * 
     * @param user
     * @return
     */
    public User update(User user) {
        return users.save(user);
    }

    /**
     * Creates a new user
     * 
     * @param user
     * @return newly created user
     */
    public User createUser(User user) {
        Optional<User> search = users.findByUsername(user.getUsername());
        Optional<User> search2 = users.findByEmail(user.getEmail());

        if (search.isPresent() || search2.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        return users.save(user);
    }
    
    /**
     * Loads user entity by username
     * 
     * @param username
     * @return loaded user
     * @throws UsernameNotFoundException
     * @throws UserNotVerifiedException
     */
    public User loadUserEntityByUsername(String username) throws UsernameNotFoundException, UserNotVerifiedException{
    	Optional<User> search = users.findByUsername(username);
        User user;
        try {
            user = search.get();
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException(username);
        }
        
        if(!user.getVerified().equals("Y")) throw new UserNotVerifiedException();
        return user;
    }
    

    /**
     * Loads users by their username
     * 
     * @param username
     * @return loaded user
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> search = users.findByUsername(username);
        User user = search.get();
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        if(!user.getVerified().equals("Y")) throw new UserNotVerifiedException();

        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
    
    /**
     * Updates user's vaccination status
     * 
     * @param username of user
     * @param date of effectiveness of vaccination
     */
    public void updateVaccinationStatus(String username, Date date) {
    	User user = loadUserEntityByUsername(username);
    	user.setVaccinated("Y");
    	user.setVaccinationDate(date);
    	users.save(user);
    }
    
    /**
     * Loads all users in the system
     * 
     * @return list of all users loaded
     */
    public List<User> loadAllUsers(){
    	return users.findAll();
    }

}