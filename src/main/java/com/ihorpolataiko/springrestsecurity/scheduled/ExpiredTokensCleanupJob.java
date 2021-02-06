package com.ihorpolataiko.springrestsecurity.scheduled;

import com.ihorpolataiko.springrestsecurity.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Component
public class ExpiredTokensCleanupJob {

    private static final int TOKEN_INACTIVITY_EXPIRATION_MINUTES = 30;

    private TokenRepository tokenRepository;

    @Autowired
    public ExpiredTokensCleanupJob(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0/10 * * * *", zone = "Europe/Kiev")
    public void cleanUpExpiredTokens() {

        log.info("Expired tokens clean up job started");

        tokenRepository.deleteByLastActivityTimeIsBefore(
                LocalDateTime.now().minusMinutes(TOKEN_INACTIVITY_EXPIRATION_MINUTES));
    }

}
