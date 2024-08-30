package com.alibou.library.services;

import com.alibou.library.Repository.UserRepository;
import com.alibou.library.Request.ChangePasswordRequest;
import com.alibou.library.handler.BusinessErrorCodes;
import com.alibou.library.handler.BusinessException;
import com.alibou.library.modal.User;
import com.alibou.library.response.ActivationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import javax.naming.AuthenticationException;
import java.security.Principal;
=======
>>>>>>> 1adf21fd66cf9839e5f5bc4bcb53c388060ba5df

@Service
@RequiredArgsConstructor
public class ChangePasswordService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

<<<<<<< HEAD

    public void changePassword(ChangePasswordRequest request, String userId)  throws BusinessException {
      try   {
          User user = userRepository.findById(userId)  .orElseThrow(() ->new BusinessException(BusinessErrorCodes.USER_NOT_FOUND));
=======
//    @Transactional
    public ActivationResponse changePassword(@Valid ChangePasswordRequest request, String id)throws BusinessException {
        User user = userRepository.findById(Integer.valueOf(id)).orElseThrow(() -> new BusinessException(BusinessErrorCodes.USER_NOT_FOUND));

        //check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        //Checking if the confirmation password is equal to new password
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("New password must match with confirm password");
        }
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as the current password");
        }
>>>>>>> 1adf21fd66cf9839e5f5bc4bcb53c388060ba5df


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


        return ActivationResponse.builder()
                .message("password changed successfully!")
                .statusCode(200)
                .build();

    }
}
