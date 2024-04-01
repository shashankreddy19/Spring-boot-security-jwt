package com.shanks.springbootsecurityjwt.controllers;

import com.shanks.springbootsecurityjwt.model.User;
import com.shanks.springbootsecurityjwt.payload.JwtResponse;
import com.shanks.springbootsecurityjwt.payload.LoginRequest;
import com.shanks.springbootsecurityjwt.payload.MessageResponse;
import com.shanks.springbootsecurityjwt.payload.RegisterRequest;
import com.shanks.springbootsecurityjwt.repository.UserRepo;
import com.shanks.springbootsecurityjwt.security.jwt.JwtOps;
import com.shanks.springbootsecurityjwt.security.jwt.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class controllers {
    @Autowired
    JwtOps jwtOps;
    @Autowired
    UserRepo repo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest)
    {
        if(repo.existsByEmail(registerRequest.getEmail()))
        {
            return ResponseEntity.badRequest().body(new MessageResponse("User already registered "));
        }
        User user=new User(registerRequest.getName(),registerRequest.getEmail(),passwordEncoder.encode(registerRequest.getPassword()));
        repo.save(user);
        return ResponseEntity.ok().body(new MessageResponse("New user registered Successfully!"));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
    {
        System.out.println(loginRequest.getEmail()+" "+loginRequest.getPassword());
        //here authenticationManager has many AuthenticationProviders but uses a specific provider for the particular authentication
        //"authenticate()" will be called from the manager to providers(DAO,In-memory,custom)
        //"authenticate()" takes Authentication object so we use UsernamePasswordAuthenticationToken which extends Authentication
        //for converting userDetails into Authentication Object
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),loginRequest.getPassword()
        ));
//        System.out.println("A"+authentication.getPrincipal());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails =(UserDetailsImpl) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());
        String accessToken=jwtOps.createToken(userDetails.getEmail());
        return ResponseEntity.ok(new JwtResponse(accessToken,userDetails.getId(), userDetails.getUsername(),userDetails.getEmail()));
    }


}
