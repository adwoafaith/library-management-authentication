package com.alibou.library.Controller;


import com.alibou.library.Repository.ForgotPasswordRepository;
import com.alibou.library.Repository.UserRepository;
import com.alibou.library.modal.ForgotPassword;
import com.alibou.library.modal.User;
import com.alibou.library.services.EmailService;
import com.alibou.library.services.EmailTempelate;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication")
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;


    /*
    /* over here we are searching for the email the user provided and we are generating an otp and sending it to them
     */
    @PostMapping("/user/resetPassword")
    public  ResponseEntity<String> verifyEmail(@RequestParam String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        int otp = otpGenerator();
        String subject = "OTP for Forgot password request";
        String text = "This is the OTP for your Forgot Password reset Request" +otp;
        try {
            emailService.sendEmail(
                    email,
                    subject,
                    user.getUsername(),
                    EmailTempelate.FORGOT_PASSWORD,
                    null,
                    null,
                    text
            );
        }
        catch (MessagingException e){
            return ResponseEntity.status(500).body("Failed to send email");

        }
        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70*1000))
                .user(user)
                .build();
        forgotPasswordRepository.save(forgotPassword);
        return ResponseEntity.ok("Email successfully sent.");
    }

    //over here lets verify the otp that the user will send to use
    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable String otp, @PathVariable String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email does not exist"));

        //check if the user exist with the otp
        ForgotPassword forgotPassword =forgotPasswordRepository .findByOtpAndUser(Integer.valueOf(otp),user).orElseThrow(() -> new RuntimeException("Invalid otp for email" +email));
        if (forgotPassword.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(forgotPassword.getForgotPasswordId());
            return new ResponseEntity<>("OTP has expired!", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("OTP successfully verified");
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePasswordHandler(@PathVariable String email, @RequestParam String newPassword, @RequestParam String confirmPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email does not exist"));

        // Check if new password matches confirm password
        if (!newPassword.equals(confirmPassword)) {
            return new ResponseEntity<>("New password must match with confirm password", HttpStatus.BAD_REQUEST);
        }

        // Encode the new password
        String encodedPassword = passwordEncoder.encode(newPassword);

        // Update the user's password
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return ResponseEntity.ok("Password successfully changed");
    }

    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}