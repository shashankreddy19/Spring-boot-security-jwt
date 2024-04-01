package com.shanks.springbootsecurityjwt.security.jwt.service;

import com.shanks.springbootsecurityjwt.model.User;
import com.shanks.springbootsecurityjwt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
@org.springframework.stereotype.Service
public class Service {
    @Autowired
    UserRepo repo;

    @Transactional
    public List<User> del(String email)
    {
        return repo.deleteByEmail(email);
    }

//    public List<User> add(User user)
//    {
//        repo.save(user);
////        return repo.findAllByName(user.getName());
//    }
    public List<User> showAll()
    {
        return repo.findAll();
    }

}
