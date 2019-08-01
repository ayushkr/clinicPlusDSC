package in.srisrisri.clinic.user;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "user")
@Table(name = "user")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;
     private String name;
     
       private String type;
        private String refId;
         private String fixedId;
         private String occupation;
         private String position;
         private String password;
         private String phoneNumber1;
         private String phoneNumber2;
         private String addressLine1;
          private String addressLine2;
           private String addressLine3;
            private String place;
             private String district;
              private String country;
         private String pincode;
         private String email;
         
     

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

}
