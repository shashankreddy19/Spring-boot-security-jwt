package com.shanks.springbootsecurityjwt.security.jwt.service;

import com.shanks.springbootsecurityjwt.model.User;
import com.shanks.springbootsecurityjwt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepo repo;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=repo.findByEmail(email);
        if(user==null) throw new UsernameNotFoundException("Email not found");
        return UserDetailsImpl.build(user);
    }
}
