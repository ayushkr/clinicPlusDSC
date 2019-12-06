/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.scheduler;

import in.srisrisri.clinic.smsChat.SMSChatService;
import in.srisrisri.clinic.smsChat.SMSChatRepo;
import in.srisrisri.clinic.smsChat.SMS_STATUS;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author akr2
 */
@Component
public class SMSScheduler {

    public static boolean paused = false;
    String label = "SMSScheduler";
    private final Logger logger = LoggerFactory.getLogger(SMSScheduler.class);

    @Autowired
    private final SMSChatService chatService;

    @Autowired
    private final SMSChatRepo smschatRepo;

    public SMSScheduler(SMSChatService chatService, SMSChatRepo smschatRepo) {
        this.chatService = chatService;
        this.smschatRepo = smschatRepo;
    }

    @Scheduled(fixedRate = 5000)
    public void r2() {

        if (paused) {
            logger.info("SMSScheduler r2  paused", new Object[]{1, LocalDateTime.now()});
        } else {
            logger.info("SMSScheduler r2  running ", new Object[]{1, LocalDateTime.now()});
            smschatRepo.findAll().forEach(smschat -> {
                if (!smschat.isDraft()) {
                    logger.info("is not draft ={} {} ", new Object[]{smschat, LocalDateTime.now()});

                    if (smschat.getStatus() != SMS_STATUS.PROCESSING) {
                        chatService.process(smschat);
                    }

                } else {
                    logger.info("is draft ={} {} ", new Object[]{smschat, LocalDateTime.now()});

                }
            });
        }
    }
}
