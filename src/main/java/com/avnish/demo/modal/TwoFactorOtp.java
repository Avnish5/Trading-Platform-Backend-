package com.avnish.demo.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class TwoFactorOtp {

    @Id
    private String id;
    private String otp;

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String jwt;
}
