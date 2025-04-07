package com.easylink.easylink.repositories;

import com.easylink.easylink.entities.VibeAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VibeAccountRepository extends JpaRepository<VibeAccount,UUID> {

}
