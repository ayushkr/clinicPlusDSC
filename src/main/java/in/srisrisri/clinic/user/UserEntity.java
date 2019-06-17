package in.srisrisri.clinic.user;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "user")
@Table(name = "user_account")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    // private String brandNameId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    

}
