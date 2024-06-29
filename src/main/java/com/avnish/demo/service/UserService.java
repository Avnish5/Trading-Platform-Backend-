package com.avnish.demo.service;

import com.avnish.demo.config.JwtProvider;
import com.avnish.demo.domain.VerificationType;
import com.avnish.demo.modal.TwofactorAuth;
import com.avnish.demo.modal.User;
import com.avnish.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findUserProfileByJwt(String jwt) throws  Exception
    {
        String email= JwtProvider.getEmailFromToken(jwt);
        User user=userRepository.findByEmail(email);

        if(user==null)
        {
            throw new Exception("User not found");
        }

        return user;
    }

    public User findUserByEmail(String email) throws  Exception
    {

        User user=userRepository.findByEmail(email);

        if(user==null)
        {
            throw new Exception("User not found");
        }

        return user;
    }

    public User findUserByid(Long userId) throws  Exception
    {
        Optional<User> user=userRepository.findById(userId);

        if(user.isEmpty())
        {
            throw new Exception("User not found");
        }

        return user.get();

    }

    public User enableTwoFactorAuthentication(User user, VerificationType verificationType)
    {
        TwofactorAuth twofactorAuth=new TwofactorAuth();
        twofactorAuth.setEnabled(true);
        twofactorAuth.setSendTo(verificationType);

        user.setTwofactorAuth(twofactorAuth);

        return userRepository.save(user);
    }

    public User updatePassword(User user,String password)
    {
        user.setPassword(password);
        return userRepository.save(user);
    }

}
