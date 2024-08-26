package com.alibou.library.services;

import com.alibou.library.Repository.UserRepository;
import com.alibou.library.modal.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    private String resetPasswordUrl;
    public void sendPasswordRestEmail(User user) {

    }
}
