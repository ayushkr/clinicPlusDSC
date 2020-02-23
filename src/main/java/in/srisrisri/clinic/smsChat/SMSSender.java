/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.smsChat;

import in.srisrisri.clinic.entities.PatientEntity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author akr2
 */
public class SMSSender {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final int sendMock = 1, sendReal = 2;

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

        try {
            if (getDoctorName() != null) {
                messageBody = messageBody.replace(PH_doctorName, getDoctorName());
            }
            if (getPatientName() != null) {
                messageBody = messageBody.replace(PH_patientName, getPatientName());
            }

            if (getPatientId() != null) {
                messageBody = messageBody.replace(PH_patient_id, getPatientId());
            }
        } catch (Exception e) {
            return messageBody;
        }

        return messageBody;
    }

    public String getPreparedMessage(String messageBody, PatientEntity patientEntity) {

        messageBody = messageBody.replace(PH_patient_id, patientEntity.getId() + "");
        String nameWithoutSir = patientEntity.getName();
        String name = "";
        int age = 0;

        try {
            age = Integer.parseInt(patientEntity.getAge());
        } catch (Exception e) {
            logger.warn("sms manager  age={} name={}", new Object[]{age, patientEntity.getName()});
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

    public String sendSmsTextLocal(String phoneNumber, String messageBody, int mockOrReal) {
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

            if (mockOrReal == sendMock) {
                logger.warn("sendSms mock= ", new Object[]{messageBody});
                System.out.println("sending...  SMS=" + messageBody);
                return messageBody;
            } else {

                try {
                    // Send data text local
                    HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
                    String data = apiKey + numbers + message + sender;
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                    conn.getOutputStream().write(data.getBytes("UTF-8"));
                    final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    final StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                    reader.close();
                    logger.warn("sendSms  real =", new Object[]{stringBuffer.toString()});
                    return stringBuffer.toString();
                } catch (Exception e) {
                    return e.toString();
                }
            }

        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }
    }

    public String sendSms_fast2sms(String phoneNumber, String messageBody, int mockOrReal) {

        if (mockOrReal == sendReal) {
            HttpResponse response = Unirest.post("https://www.fast2sms.com/dev/bulk")
                    .header("authorization", "mSEkKgbH20Nwzvy6QIGXYDp4hCAlu8s51PitOWFMrUT93jnaBJM5qeQvXgtmUC2w9VipTAblnPDzLdE1")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body("sender_id=FSTSMS&message=" + messageBody + "&language=english&route=p&numbers=" + phoneNumber)
                    .asString();
            return response.getStatusText()+response.getBody().toString();

        } else {
            return "mock ";
        }

    }

}
