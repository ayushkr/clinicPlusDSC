/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.scheduler;

import in.srisrisri.clinic.Frame0;
import in.srisrisri.clinic.ayushLogger.Logger;
import in.srisrisri.clinic.ayushLogger.LoggerFactory;
import in.srisrisri.clinic.smsChat.SMSChatService;
import in.srisrisri.clinic.smsChat.SMSChatRepo;
import in.srisrisri.clinic.smsChat.SMS_STATUS;
import in.srisrisri.clinic.smsChatHistory.SMSChatHistoryRepo;
import java.time.LocalDateTime;

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

    private final Logger logger = LoggerFactory.getLogger(
            SMSScheduler.class,
            Frame0.jTextAreaSMS
    );

    @Autowired
    private final SMSChatService chatService;

    @Autowired
    private final SMSChatRepo smschatRepo;

    @Autowired
    private final SMSChatHistoryRepo chatHistoryRepo;

    @Autowired
    final SmschatsProcessor smschatsProcessor;

    @Autowired
    final SmschatsHistoryProcessor smschatsHistoryProcessor;

    public SMSScheduler(SMSChatService chatService, SMSChatRepo smschatRepo, SMSChatHistoryRepo chatHistoryRepo, SmschatsProcessor smschatsProcessor, SmschatsHistoryProcessor smschatsHistoryProcessor) {
        this.chatService = chatService;
        this.smschatRepo = smschatRepo;
        this.chatHistoryRepo = chatHistoryRepo;
        this.smschatsProcessor = smschatsProcessor;
        this.smschatsHistoryProcessor = smschatsHistoryProcessor;
    }

    

    @Scheduled(fixedRate = 5000)
    public void smschats() {
        smschatsProcessor.start();

    }

    @Scheduled(fixedRate = 5000)
    public void smschatHistory() {
       smschatsHistoryProcessor.start();

    }
}
