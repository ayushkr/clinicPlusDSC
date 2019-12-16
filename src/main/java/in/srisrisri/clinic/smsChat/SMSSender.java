/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.smsChat;

import in.srisrisri.clinic.patient.PatientEntity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author akr2
 */
public class SMSSender {
       private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final int sendMock=1,sendReal=2;
  
    static final String PH_patient_id = "$id";
    static final String PH_patientName = "$patientName";
    static final String PH_doctorName = "$doctorName";

    String patientName = null;
    String doctorName = null;
    String patientId = null;
    
    
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPreparedMessage(String messageBody) {
        if (getDoctorName() != null) {
            messageBody = messageBody.replace(PH_doctorName, getDoctorName());
        }
        if (getPatientName() != null) {
            messageBody = messageBody.replace(PH_patientName, getPatientName());
        }

        if (getPatientId() != null) {
            messageBody = messageBody.replace(PH_patient_id, getPatientId());
        }

        return messageBody;
    }

    public String getPreparedMessage(String messageBody, PatientEntity patientEntity) {

        messageBody = messageBody.replace(PH_patient_id, patientEntity.getId() + "");
        String nameWithoutSir = patientEntity.getName();
        String name = "";
        int age =0;
        
        try{
        age=Integer.parseInt(patientEntity.getAge());
        }catch(Exception e){
        logger.warn("sms manager  age={} name={}", new Object[]{age,patientEntity.getName()});
        }

        if ("M".equals(patientEntity.getSex())) {
            name = "Mr." + nameWithoutSir;
        }
        if (age > 18) {
            if ("F".equals(patientEntity.getSex())) {
                name = "Ms." + nameWithoutSir;
            }
        } else {
            if ("F".equals(patientEntity.getSex())) {
                name = "" + nameWithoutSir;
            }
        }

        messageBody = messageBody.replace(PH_patientName, name);
//  messageBody = messageBody.replace(PH_patientName, patientEntity.getName());

        return messageBody;
    }

    public String sendSms(String phoneNumber, String messageBody, int mockOrReal) {
        try {
            // Construct data
            String apiKey = "apikey=" + "fpt5fwbak7s-ZE6wBcovVs2TFPuDh11kgzzDUrddkX";

            String message = "&message=" + messageBody;
            String sender = "&sender=" + "TXTLCL";
//    String numbers = "&numbers=" + "917907996990";

            if (phoneNumber.length() == 10) {
                phoneNumber = "91" + phoneNumber;
            } else {

            }

            String numbers = "&numbers=" + phoneNumber;

            if (mockOrReal==sendMock) {
                // mock
//                String s = "<div>"
//                        + "<span style='float:left'> Number=" + phoneNumber + "</span>"
//                        + "<span style='float:right'> Message=" + messageBody+"</span>"
//                        + "</div>";
                
                 String s = 
                         
                        "<td> " + phoneNumber + "</td>"
                        + "<td>" + messageBody+"</td>"
                        ;

                logger.warn("sms manager mock=", new Object[]{s});
                return s;
            } else {
                // Send data text local
                HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
                String data = apiKey + numbers + message + sender;
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                conn.getOutputStream().write(data.getBytes("UTF-8"));
                final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                final StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    stringBuffer.append(line);
                }
                rd.close();
                logger.warn("sms manager  real =", new Object[]{stringBuffer.toString()});
                return stringBuffer.toString();
            }

        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }
    }

   

}
