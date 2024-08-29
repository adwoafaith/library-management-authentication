package com.alibou.library.services;


import com.alibou.library.Repository.UserRepository;
import com.alibou.library.Request.ChangePasswordRequest;
import com.alibou.library.handler.BusinessErrorCodes;
import com.alibou.library.handler.BusinessException;
import com.alibou.library.modal.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public void changePassword(ChangePasswordRequest request, String userId)  throws BusinessException {
      try   {
          User user = userRepository.findById(userId)  .orElseThrow(() ->new BusinessException(BusinessErrorCodes.USER_NOT_FOUND));


          //check if the current password is correct
          if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
              throw new IllegalArgumentException("Wrong password");
          }
          //Checking If confirm password is equal to new password
          if (!request.getNewPassword().equals(request.getConfirmPassword())) {
              throw new IllegalArgumentException("New password must match with confirm password");
          }
          if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
              throw new IllegalArgumentException("New password cannot be the same as the old password");
          }



          //save the new password
          user.setPassword(passwordEncoder.encode(request.getNewPassword()));
          userRepository.save(user);
      }
      catch (Exception e) {
          e.getMessage();
      }


    }
}
