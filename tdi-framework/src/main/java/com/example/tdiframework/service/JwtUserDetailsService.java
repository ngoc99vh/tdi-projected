package com.example.tdiframework.service;

import com.example.tdiframework.domain.User;
import com.example.tdiframework.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByPhone(username);
//        Optional<User> userFromDatabase = userReponsitory.findOneByPhone(username);
        if (user != null && user.getPhone().equals(username)) {
//            return userFromDatabase.map(item -> {
//                List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
//                        .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//                        .collect(Collectors.toList());
//                return new org.springframework.security.core.userdetails.User(username,
//                        user.getPassword(),
//                        grantedAuthorities);
//            }).orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the " +
//                    "database"));
//            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            user.getAuthorities().forEach(authority -> {
//                authorities.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
//            });
//            return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(), authorities);
            return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Phone number: " + username + "not found");
        }
    }
}
