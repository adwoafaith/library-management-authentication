package com.alibou.library.Repository;

import com.alibou.library.modal.ForgotPassword;
import com.alibou.library.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    //lets fine user by otp

    @Query("select forgotPassword from ForgotPassword forgotPassword where forgotPassword.otp = ?1 and forgotPassword.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);

}
