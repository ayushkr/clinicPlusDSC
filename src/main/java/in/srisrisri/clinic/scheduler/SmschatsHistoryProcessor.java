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
import in.srisrisri.clinic.smsChat.SMSSender;
import in.srisrisri.clinic.smsChat.SMS_STATUS;
import in.srisrisri.clinic.smsChatHistory.SMSChatHistoryRepo;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

/**
 *
 * @author akr2
 */
@Component
public class SmschatsHistoryProcessor {

    private final Logger logger = LoggerFactory.getLogger(
            SMSScheduler.class,
            Frame0.jTextAreaSMS
    );

    private final SMSChatService chatService;
    private final SMSChatHistoryRepo chatHistoryRepo;

    public SmschatsHistoryProcessor(SMSChatService chatService, SMSChatHistoryRepo chatHistoryRepo) {
        this.chatService = chatService;
        this.chatHistoryRepo = chatHistoryRepo;
    }

    public void start() {
        chatHistoryRepo.findAll().forEach(history -> {
            logger.info(" SmschatsHistoryProcessor ->\t{} \t{} ", new Object[]{history, LocalDateTime.now()});

            if (history.getSentStatus() == SMS_STATUS.FRESH) {
                
                System.out.println("history "+history.toString());
                SMSSender s = new SMSSender();
                s.setDoctorName("doc1");
                s.setPatientName(history.getContactName());
                
//        smsm.setPatientId("123");
//        smsm.sendSmsTextLocal("7907996990", message1);
//                String sendResult = s.sendSmsTextLocal(history.getContactNumber(), 
//                        history.getsMSChat().getBody(), 
//                        SMSSender.sendReal);
                
                  String sendResult = s.sendSms_fast2sms(history.getContactNumber(), 
                        history.getsMSChat().getBody(), 
                        SMSSender.sendReal);
                
                
                history.setRemarks(sendResult);
                
                history.setSentStatus(SMS_STATUS.PROCESSING);
chatHistoryRepo.save(history);
            }

        });
    }
}
