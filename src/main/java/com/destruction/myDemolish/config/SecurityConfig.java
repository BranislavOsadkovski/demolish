package com.destruction.myDemolish.config;

import com.destruction.myDemolish.domainOne.ApplicationUserPermission;
import com.destruction.myDemolish.domainOne.ApplicationUserRole;
import com.destruction.myDemolish.security.JwtAuthenticationFilter;
import com.destruction.myDemolish.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.destruction.myDemolish.domainOne.ApplicationUserRole.ADMIN;
import static com.destruction.myDemolish.domainOne.ApplicationUserRole.STUDENT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final EncoderUtil encoderUtil;
    private final UserService userService;
    //    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * First configuration
         *             .antMatchers(HttpMethod.POST, "/book/**").hasAuthority(COURSE_WRITE.getPermission())
         *              .antMatchers("/book/**").hasAnyRole(STUDENT.name(), ADMIN.name())
         *
         */

        /** Any Cross-Site Scripting (XSS) can be used to defeat all CSRF mitigation techniques!
         * https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html
         */
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/create").permitAll()
                .antMatchers("/index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/book/all", true)
//                .passwordParameter("password")
//                .usernameParameter("username")
                .and()
                .rememberMe()
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("verysecurekey")
//                .rememberMeParameter("remember-me")
                .and()
                .logout()
                .logoutUrl("/logout")
                //should be using POST if csrf disabled - check doc's
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout-url", "GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/login");


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
//        my part with JWTFilter
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(encoderUtil.passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
//        auth.userDetailsService(userService).passwordEncoder(encoderUtil.passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
