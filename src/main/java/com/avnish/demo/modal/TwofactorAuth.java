package com.avnish.demo.modal;

import com.avnish.demo.domain.VerificationType;
import lombok.Data;

@Data
public class TwofactorAuth {
    private boolean isEnabled = false;
    private VerificationType sendTo;
}
