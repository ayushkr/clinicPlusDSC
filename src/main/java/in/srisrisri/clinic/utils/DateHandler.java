/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.text.DateFormat;

/**
 *
 * @author akr
 */
public class DateHandler extends StdDeserializer<Date> {

    public DateHandler() {
        this(null);
    }

    public DateHandler(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        String dateStr = p.getText();
        System.out.println("deserialize="+dateStr);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
             System.out.println("sdf="+sdf.toString());
            java.util.Date dateJava = sdf.parse(dateStr);
             System.out.println("dateJava="+dateJava.toString());
           
             
              Date sqlDate =new Date(dateJava.getTime());
//            Date sqlDate = new Date(dateJava.getYear(), dateJava.getMonth(), dateJava.getDay());
            System.out.println("sqlDate="+sqlDate.toString());
            return sqlDate;
        } catch (Exception e) {
            System.out.println("error deserialize() sdf "+e.toString());
            return null;
        }

    }

}
