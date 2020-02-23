/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.scheduler;

import in.srisrisri.clinic.Frame0;
import in.srisrisri.clinic.ayushLogger.Logger;
import in.srisrisri.clinic.ayushLogger.LoggerFactory;
import in.srisrisri.clinic.smsChat.SMSChatRepo;
import in.srisrisri.clinic.smsChat.SMSChatService;
import in.srisrisri.clinic.smsChat.SMS_STATUS;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

/**
 *
 * @author akr2
 */

@Component
public class SmschatsProcessor {

    private final Logger logger = LoggerFactory.getLogger(
            SMSScheduler.class,
            Frame0.jTextAreaSMS
    );

    private final SMSChatService chatService;
    private final SMSChatRepo smschatRepo;

    public SmschatsProcessor(SMSChatService chatService, SMSChatRepo smschatRepo) {
        this.chatService = chatService;
        this.smschatRepo = smschatRepo;
    }

    public void start() {

        smschatRepo.findAll().forEach(smschat -> {
            if (!smschat.isDraft()) {
                logger.info("draft False ->\t{} \t{} ", new Object[]{smschat, LocalDateTime.now()});

                if (smschat.getStatus() != SMS_STATUS.PROCESSING) {
                    chatService.process(smschat);
                }

            } else {
                logger.info("draft True ->\t{} \t{} ", new Object[]{smschat, LocalDateTime.now()});

            }
        });

    }

}
