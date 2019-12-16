package in.srisrisri.clinic.dateForOrganisation;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name = "DateForOrganisation")
@Table(name="DateForOrganisation")
public class DateForOrganisationEntity implements Serializable  {

    @Id
    @GeneratedValue
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
   
   
}