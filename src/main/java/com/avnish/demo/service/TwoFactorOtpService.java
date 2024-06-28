package com.avnish.demo.service;

import com.avnish.demo.modal.TwoFactorOtp;
import com.avnish.demo.modal.User;
import com.avnish.demo.repository.TwoFactorOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFactorOtpService {

    @Autowired
    TwoFactorOtpRepository twoFactorOtpRepository;

    public TwoFactorOtp createTwoFactorOtp(User user,String otp,String jwt) {

        UUID uuid= UUID.randomUUID();

        String id = uuid.toString();

        TwoFactorOtp twoFactorOtp = new TwoFactorOtp();
        twoFactorOtp.setOtp(otp);
        twoFactorOtp.setJwt(jwt);
        twoFactorOtp.setId(id);
        twoFactorOtp.setUser(user);

       return  twoFactorOtpRepository.save(twoFactorOtp);
    }

    public TwoFactorOtp findByUser(User user) {
        return  twoFactorOtpRepository.findByUserId(user.getId());
    }

    public TwoFactorOtp findById(String id) {

        Optional<TwoFactorOtp> twoFactorOtp = twoFactorOtpRepository.findById(id);
        return twoFactorOtp.orElse(null);
    }

    public boolean verifyTwoFactorOtp(TwoFactorOtp twoFactorOtp,String otp) {
        return twoFactorOtp.getOtp().equals(otp);
    }

    public void deleteTwoFactorOtp(TwoFactorOtp twoFactorOtp) {
        twoFactorOtpRepository.delete(twoFactorOtp);
    }




}
