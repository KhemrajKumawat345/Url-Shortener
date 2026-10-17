package com.url.shortener.Service;

import com.url.shortener.DTOs.LoginRequest;
import com.url.shortener.Model.User;
import com.url.shortener.Repository.UserRepository;
import com.url.shortener.Security.JWT.JWTUtils;
import com.url.shortener.Security.JWT.JwtAuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;    // used to process and authentication request.
    private JWTUtils jwtUtils;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    // for login user
    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = null;
        if (userDetails != null) {
            jwt = jwtUtils.generateToken(userDetails);
        }
        return new JwtAuthenticationResponse(jwt);
    }

    public User findByUsername(String name) {
        return userRepo.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + name));
    }
}
