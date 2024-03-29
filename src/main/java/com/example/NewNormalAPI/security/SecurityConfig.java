package com.example.NewNormalAPI.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.NewNormalAPI.jwt.filter.JwtRequestFilter;

@EnableWebSecurity
@Configuration
@ConfigurationProperties(prefix="server.servlet")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(UserDetailsService userSvc) {
        this.userDetailsService = userSvc;
    }

    /**
     * Attach the user details and password encoder.
     * 
     * @param auth
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    /**
     * Add roles to specify permissions for each enpoint User role: can create
     * account. Admin role:
     * 
     * Note: '*' matches zero or more characters, e.g., /books/* matches /books/20
     * '**' matches zero or more 'directories' in a path, e.g., /books/** matches
     * /books/1/reviews
     * 
     * @param configure
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and() 
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/accounts/verify/*").permitAll() // allows users to verify their accounts
                .antMatchers(HttpMethod.POST,"/accounts/user").permitAll() // allows users to create an account
                .antMatchers(HttpMethod.POST,"/accounts/login").permitAll() // allows users to log in 
                .antMatchers(HttpMethod.POST,"/events/create").hasAuthority("faculty") // Only allow faculty to create events
                .antMatchers(HttpMethod.PUT,"/accounts/admin").hasAuthority("admin") // Only allow admin to promote user from student to faculty
                .antMatchers(HttpMethod.POST,"/accounts/admin").hasAuthority("admin") // Only allow admin to update capacity
                .antMatchers(HttpMethod.GET,"/vaccination/**").permitAll()
                .antMatchers(HttpMethod.POST,"/vaccination/**").permitAll()
                .antMatchers(HttpMethod.GET,"/admin/**").hasAuthority("admin")
                .antMatchers(HttpMethod.POST,"/admin/**").hasAuthority("admin")
                .antMatchers(HttpMethod.GET,"/events/venues").hasAuthority("faculty")
                .anyRequest().authenticated() // any remaining requests will need authentication
                .and().csrf().disable() // CSRF protection is needed only for browser based attacks
                .formLogin().disable() // Disables default login page
                .headers().disable() // Disable the security headers, as we do not return HTML in our service

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Spring security dont
                                                                                             // create sessions
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * @Bean annotation is used to declare a PasswordEncoder bean in the Spring
     *       application context. Any calls to encoder() will then be intercepted to
     *       return the bean instance.
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        // auto-generate a random salt internally
        return new BCryptPasswordEncoder();
    }

    /**
     * Interface for authentication
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
