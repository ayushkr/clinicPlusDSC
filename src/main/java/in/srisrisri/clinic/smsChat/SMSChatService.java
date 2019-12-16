/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.smsChat;

import in.srisrisri.clinic.doctor.DoctorEntity;
import in.srisrisri.clinic.doctor.DoctorRepo;
import in.srisrisri.clinic.patient.PatientEntity;
import in.srisrisri.clinic.patient.PatientRepo;
import in.srisrisri.clinic.smsChatHistory.SMSChatHistory;
import in.srisrisri.clinic.smsChatHistory.SMSChatHistoryRepo;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    SMSChat smsChat;
    List<String> listOfOneline = new LinkedList<>();

/////////////////////
//    process
////////////
    public void process(SMSChat smsChat) {
        this.smsChat = smsChat;
        if (smsChat.getStatus() == SMS_STATUS.FRESH) {
            logger.info("is fresh ={}", smsChat);
            smsChat.setStatus(SMS_STATUS.PROCESSING);
            sMSChatRepo.save(smsChat);

            int[] groupNumbers = smsChat.getToPhoneNumbers();
            for (int groupNumber : groupNumbers) {
                if (groupNumber == SMSChat.PATIENTS) {
                    List<PatientEntity> listOfPatients = patientRepo.findAll();
                    listOfPatients.forEach(patient -> {
                        long id = patient.getId();
                        String name = patient.getName();
                        String contactPhone = patient.getContactPhone();
                        String oneline = name + "@" + id + "@" + contactPhone;
                        logger.info("oneline={}", oneline);
                        listOfOneline.add(oneline);
                    });

                }

                if (groupNumber == SMSChat.DOCTORS) {
                    List<DoctorEntity> list = doctorRepo.findAll();
                    list.forEach(item -> {
                        long id = item.getId();
                        String name = item.getName();
                        String contactPhone = item.getContactPhone();
                        String oneline = name + "@" + id + "@" + contactPhone;
                        logger.info("oneline={}", oneline);
                        listOfOneline.add(oneline);
                    });

                }

            }

            listOfOneline.forEach(oneline -> {
                SMSChatHistory smsChatHistory = new SMSChatHistory();
                smsChatHistory.setSentStatus(SMS_STATUS.PROCESSING);
                 smsChatHistory.setTitle(oneline);
                smsChatHistory.setBody(smsChat.getBody());
                smsChatHistory.setDateOfSending(Date.valueOf(LocalDate.now()));

            sMSChatHistoryRepo.save(smsChatHistory);
            });

            smsChat.setProcessed(true);
            sMSChatRepo.save(smsChat);

        } else {
// not fresh
            logger.info("Not fresh : {}", smsChat);
        }

    }

}
