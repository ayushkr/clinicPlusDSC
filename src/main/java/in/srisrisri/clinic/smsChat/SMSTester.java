/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.smsChat;


/**
 *
 * @author akr2
 */
public class SMSTester {

    public static void main(String[] args) {
        String message1 = "Thank you $patientName to register at Doctors Speciality Centre. Your ID is $id.  Get well soon. ";
        String message2 = "Thank you $patientName, your appointment to meet $doctorName is booked. Your ID is $id & please wait @ our patient’s launch. Get well soon";
        String message3 = "Thank you $patientName to visit DOCTORS SPECIALITY CENTRE. Hope the consultation & our services were good. Please rate us by visiting our official Facebook page.";
        SMSSender s = new SMSSender();
        s.setDoctorName("doc1");
        s.setPatientName("pat1");
//        smsm.setPatientId("123");
//        smsm.sendSmsTextLocal("7907996990", message1);
        s.sendSmsTextLocal("7907996990",s.getPreparedMessage(message1), SMSSender.sendMock);
    }
}
