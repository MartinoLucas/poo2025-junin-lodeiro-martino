package com.poo.proyecto.service;

import com.poo.proyecto.entity.UserAccount;
import com.poo.proyecto.entity.base.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserAccountService {
    UserAccount create(Email email, String passwordHash);
    UserAccount get(Long id);
    UserAccount updatePassword(Long id, String newPasswordHash);
    void deactivate(Long id);
    void activate(Long id);
    Page<UserAccount> list(Pageable pageable);
}

