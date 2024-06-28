package com.avnish.demo.controller;

import com.avnish.demo.config.JwtProvider;
import com.avnish.demo.modal.User;
import com.avnish.demo.repository.UserRepository;
import com.avnish.demo.response.AuthResponse;
import com.avnish.demo.service.CustomUserDetailsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody User user) throws  Exception{

        User isEmailExist=userRepository.findByEmail(user.getEmail());
        if(isEmailExist!=null)
        {
            throw new Exception("Email is alreday exist");
        }

        User newUser =new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());
        User savedUser = userRepository.save(newUser);

        Authentication auth=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt= JwtProvider.generateToken(auth);

        AuthResponse response=new AuthResponse();
        response.setJwt(jwt);
        response.setStatus(true);
        response.setMessage("Successfully registered");
        return ResponseEntity.ok(response);


    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws  Exception{

        String userName=user.getEmail();
        String password=user.getPassword();

        Authentication auth=auhtenticate(userName,password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt= JwtProvider.generateToken(auth);

        AuthResponse response=new AuthResponse();
        response.setJwt(jwt);
        response.setStatus(true);
        response.setMessage("Login successful");
        return ResponseEntity.ok(response);


    }

    private Authentication auhtenticate(String userName, String password) {
        UserDetails userDetails=customUserDetailsService.loadUserByUsername(userName);

        if(userDetails==null)
        {
            throw new BadCredentialsException("User not found");
        }

        if(!password.equals(userDetails.getPassword()))
        {
            throw new BadCredentialsException("Wrong password");
        }
        return new UsernamePasswordAuthenticationToken(userName, password, userDetails.getAuthorities());
    }
}
