package com.avnish.demo.repository;

import com.avnish.demo.modal.TwoFactorOtp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOtp, String> {

    TwoFactorOtp findByUserId(Long userId);

}
