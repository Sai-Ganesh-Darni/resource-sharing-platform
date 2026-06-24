package com.example.resourcesharingsystem.filter;

import com.example.resourcesharingsystem.entity.User;
import com.example.resourcesharingsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException(email);
        }

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .authorities(
                        user.getRoles()
                                .stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName()))
                                .toList()
                )
                .build();

    }
}
