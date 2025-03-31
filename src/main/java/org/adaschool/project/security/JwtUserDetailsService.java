package org.adaschool.project.security;


import org.adaschool.project.model.UserEntity;
import org.adaschool.project.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            List<SimpleGrantedAuthority> authorities = userEntity.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .toList();
            return new User(userEntity.getEmail(), userEntity.getPasswordHash(), authorities);
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}