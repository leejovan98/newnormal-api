package com.example.accountsservice.user;

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

//    // TODO: finalise the return type JWT?
//    public User authenticate(User user) {
//        Optional<User> search = users.findByEmail(user.getEmail());
//        if (search.isEmpty())
//            throw new LoginFailedException(user.getEmail());
//
//        User actualUser = search.get();
//        if (!actualUser.isEnabled()) {
//            throw new UserNotVerifiedException(user.getEmail());
//        }
//
//        if (!(BCrypt.checkpw(user.getPassword(), actualUser.getPassword()))) {
//            throw new LoginFailedException(user.getEmail());
//        }
//
//        return actualUser;
//        // at this point user is authenticated
//        // what to return?
//        // might just remove this entire method
//    }
//
    public User update(User user) {
        return users.save(user);
    }

    public User createUser(User user) {
        Optional<User> search = users.findByUsername(user.getUsername());
        Optional<User> search2 = users.findByEmail(user.getEmail());

        if (search.isPresent() || search2.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        return users.save(user);
    }

    // TODO -- FIX ERROR MESSAGE
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

}