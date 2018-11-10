package com.example.twitter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo=userRepo;
    }
    public User findByEmail(String email){
        return userRepo.findByEmail(email);
    }
    public Long countByEmail(String email){

        return userRepo.countByEmail(email);

    }

    public void saveUser(User user){

        user.setRoles(Arrays.asList(roleRepo.findByRole("USER")));
        user.setEnabled(true);
        userRepo.save(user);

    }
    public void saveAdmin(User user){

        user.setRoles(Arrays.asList(roleRepo.findByRole("ADMIN")));
        user.setEnabled(true);
        userRepo.save(user);


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if( user==null){
            throw new UsernameNotFoundException("No user name");
        }
        return user;
    }
    private Set<GrantedAuthority> getAuthorities(User User){
        Set<GrantedAuthority>authorities = new HashSet<GrantedAuthority>();
        for(Role role:User.getRoles()){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }
        System.out.println("User authorities are "+ authorities.toString());

        return authorities;
    }

}
