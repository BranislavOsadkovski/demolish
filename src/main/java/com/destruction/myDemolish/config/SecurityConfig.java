package com.destruction.myDemolish.config;

import com.destruction.myDemolish.domainOne.ApplicationUserRole;
import com.destruction.myDemolish.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.destruction.myDemolish.domainOne.ApplicationUserRole.ADMIN;
import static com.destruction.myDemolish.domainOne.ApplicationUserRole.STUDENT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final UserDetailsService userDetailsService;
    //    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails user1 = User.builder()
                .username("user1")
                .password(passwordEncoder().encode("password"))
                .roles(STUDENT.name()) //ROLE_STUDENT
                .build();
        UserDetails user2 = User.builder()
                .username("user2")
                .password(passwordEncoder().encode("password"))
                .roles(ADMIN.name())
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * First configuration
         */
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/index", "/css/*", "/js/*").permitAll()
                .antMatchers("/book/**").hasRole(STUDENT.name())
                .antMatchers("/api/**").hasRole(ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        /**
         *
         * Second configuration
         */
//        http.
//        cors().and().csrf().disable()  //cors & csrf
//                .authorizeRequests()
//                .anyRequest()
//                .permitAll()
//                .anyRequest().authenticated() //authenticating!
//                .and();
        //my part with JWTFilter
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
