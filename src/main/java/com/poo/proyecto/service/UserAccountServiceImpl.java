package com.poo.proyecto.service;

import com.poo.proyecto.entity.UserAccount;
import com.poo.proyecto.entity.base.Email;
import com.poo.proyecto.repository.UserAccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import com.poo.proyecto.util.PasswordEncoder;

@Service
public class UserAccountServiceImpl extends BaseServiceSupport implements UserAccountService {

    private final UserAccountRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserAccountServiceImpl(UserAccountRepository repo,PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserAccount create(com.poo.proyecto.entity.base.Email email, String passwordHash) {
        check(!repo.existsByEmail_Value(email.value()), "Ya existe una cuenta con ese email");
        UserAccount u = new UserAccount();
        u.setEmail(email);
        // encode la contraseña antes de guardarla
        u.setPasswordHash(passwordEncoder.encode(passwordHash));
        return repo.save(u);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount get(Long id) {
        return orNotFound(repo.findById(id), "UserAccount no encontrado");
    }

    @Override
    @Transactional
    public UserAccount updatePassword(Long id, String newPasswordHash) {
        UserAccount u = orNotFound(repo.findById(id), "UserAccount no encontrado");
        u.setPasswordHash(passwordEncoder.encode(newPasswordHash));
        return u;
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        UserAccount u = orNotFound(repo.findById(id), "UserAccount no encontrado");
        u.setDeletedAt(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void activate(Long id) {
        UserAccount u = orNotFound(repo.findById(id), "UserAccount no encontrado");
        u.setDeletedAt(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAccount> list(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
