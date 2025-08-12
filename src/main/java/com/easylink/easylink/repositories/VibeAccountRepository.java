package com.easylink.easylink.repositories;

import com.easylink.easylink.entities.AssociativeEntry;
import com.easylink.easylink.entities.VibeAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VibeAccountRepository extends JpaRepository<VibeAccount,UUID> {


    //Optional<VibeAccount> findByEmail(String email);
    Optional<VibeAccount> findByEmailVerificationToken(String token);
    List<VibeAccount> findByIsEmailVerifiedFalseAndTokenExpiryBefore(LocalDateTime time);

    List<VibeAccount> findByEmail(String email);
    boolean existsByEmail(String email);


}
