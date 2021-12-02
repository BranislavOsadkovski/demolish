package com.destruction.myDemolish.services;

import com.destruction.myDemolish.config.EncoderUtil;
import com.destruction.myDemolish.domain.AppUser;
import com.destruction.myDemolish.domain.UserAccount;
import com.destruction.myDemolish.mysql.repos.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final EncoderUtil encoderUtil;
    //    private final RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("Username %s not found", username))
                );

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getApplicationUserRole().getGrantedAuthorities().forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority(permission.getAuthority()));
        });


        return new UserAccount(
                appUser.getUsername(),
                appUser.getPassword(),
                authorities,
                appUser.isEnabled(),
                appUser.isEnabled(),
                appUser.isEnabled(),
                appUser.isEnabled()
        );
    }


    public AppUser saveAppUser(AppUser appUser) {
        appUser.setPassword(encoderUtil.passwordEncoder().encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

//    public Role saveRole(Role role) {
//        return roleRepository.save(role);
//    }

//    public void addRoleToUser(String username, String roleName) {
//        AppUser user = appUserRepository.findByUsername(username);
//        Role role = roleRepository.findByName(roleName);
//        user.getRoles().add(role);
//    }

    public AppUser getAppUser(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("Username %s not found", username))
                );
    }

    public List<AppUser> getAllAppUsers() {
        return appUserRepository.findAll();
    }


}
