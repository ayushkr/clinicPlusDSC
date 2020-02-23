/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.smsChat;

import in.srisrisri.clinic.entities.DoctorEntity;
import in.srisrisri.clinic.doctor.DoctorRepo;
import in.srisrisri.clinic.entities.PatientEntity;
import in.srisrisri.clinic.patient.PatientRepo;
import in.srisrisri.clinic.smsChatHistory.SMSChatHistory;
import in.srisrisri.clinic.smsChatHistory.SMSChatHistoryRepo;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.srisrisri.clinic.scheduler.Contactable;

/**
 *
 * @author akr2
 */
@Service
public class SMSChatService {

    private final Logger logger = LoggerFactory.getLogger(SMSChatService.class);

    @Autowired
    private final PatientRepo patientRepo;
    @Autowired
    private final DoctorRepo doctorRepo;

    @Autowired
    private final SMSChatRepo sMSChatRepo;

    @Autowired
    private final SMSChatHistoryRepo sMSChatHistoryRepo;

    public SMSChatService(PatientRepo patientRepo, DoctorRepo doctorRepo, SMSChatRepo sMSChatRepo, SMSChatHistoryRepo sMSChatHistoryRepo) {
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
        this.sMSChatRepo = sMSChatRepo;
        this.sMSChatHistoryRepo = sMSChatHistoryRepo;
    }

    SMSChat chat;
    List<String> listOfOneline = new LinkedList<>();

/////////////////////
//    process
////////////
    public void process(SMSChat chat) {
        this.chat = chat;
        if (chat.getStatus() == SMS_STATUS.FRESH) {
            logger.info("is fresh ={}", chat);
            chat.setStatus(SMS_STATUS.PROCESSING);
            sMSChatRepo.save(chat);

            int[] groupNumbers = chat.getToPhoneNumbers();
            
            for (int groupNumber : groupNumbers) {
                
                if (groupNumber == SMSChat.PATIENTS) {
                    List<PatientEntity> listOfPatients = patientRepo.findAll();
                    listOfPatients.forEach(item -> {
                        
                        sMSChatHistoryRepo.save(copyBasicContactInfo(chat,item));
                    });

                }

                if (groupNumber == SMSChat.DOCTORS) {
                    List<DoctorEntity> list = doctorRepo.findAll();
                    list.forEach(item -> {
                        sMSChatHistoryRepo.save(copyBasicContactInfo(chat,item));
                    });

                }
                
                 if (groupNumber == SMSChat.OTHERS) {
                   
                    PatientEntity item = new PatientEntity();
                    item.setContactPhone("7907996990");
                    item.setName("Ayush K R");
                        sMSChatHistoryRepo.save(copyBasicContactInfo(chat,item));
                  

                }

            }

            chat.setProcessed(true);
            sMSChatRepo.save(chat);

        } else {
// not fresh
            logger.info("Not fresh : {}", chat);
        }

    }

    public SMSChatHistory copyBasicContactInfo(SMSChat chat,Contactable contact) {

        SMSChatHistory history = new SMSChatHistory();
        history.setsMSChat(chat);
        history.setDateOfSending(chat.getDateOfSending());
        history.setSentStatus(SMS_STATUS.FRESH);
        history.setContactPersonType(contact.getModule());
        history.setContactName(contact.getName());
        history.setContactNumber(contact.getContactPhone());
       

        return history;
    }

}
