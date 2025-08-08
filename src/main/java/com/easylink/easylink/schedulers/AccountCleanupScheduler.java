package com.easylink.easylink.schedulers;

import com.easylink.easylink.repositories.VibeAccountRepository;
import com.easylink.easylink.entities.VibeAccount;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCleanupScheduler {

    private final VibeAccountRepository vibeAccountRepository;

//    @Scheduled(fixedRate = 10000) //for tests - every 10 sec
    @Scheduled(cron = "0 0 3 * * *") // every day at 03:00
    @Transactional
    public void deleteUnverifiedAccounts() {
        LocalDateTime now = LocalDateTime.now();
        List<VibeAccount> expiredAccounts = vibeAccountRepository
                .findByIsEmailVerifiedFalseAndTokenExpiryBefore(now);

        if (expiredAccounts.isEmpty()) {
            log.info("No expired unverified accounts found");
            return;
        }

        for (VibeAccount account : expiredAccounts) {
            log.info("Deleting unverified account: " + account.getEmail());
            vibeAccountRepository.delete(account);
        }

        log.info("Deleted {} expired unverified accounts", expiredAccounts.size());
    }
}
