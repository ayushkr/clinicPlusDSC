package in.srisrisri.clinic.medicineBrandName;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Entity(name = "MedicineBrandName")
@Table(name="MedicineBrandName")
@TableGenerator(name="tab1", initialValue=1, allocationSize=50)
public class MedicineBrandNameEntity  {

    @Id
//   @GeneratedValue(strategy=GenerationType.TABLE, generator="tab1")
     @GeneratedValue
    private long id;
    private String brandName;
    private String company;
    private String genericName;
    private String usedFor;
    private String type;
    private long groupid;
    private String description;
    String HSN;
    private String other;
  
   
    @Column(name="lastTouched", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date lastTouched; 

    public String getHSN() {
        return HSN;
    }

    public void setHSN(String HSN) {
        this.HSN = HSN;
    }

   
    
    
    
    
    public long getId() {
        return this.id;
    }

   

    /**
     * @return the usedFor
     */
    public String getUsedFor() {
        return usedFor;
    }

    /**
     * @param usedFor the usedFor to set
     */
    public void setUsedFor(String usedFor) {
        this.usedFor = usedFor;
    }

    /**
     * @return the brandName
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * @param brandName the brandName to set
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * @return the other
     */
    public String getOther() {
        return other;
    }

    /**
     * @param other the other to set
     */
    public void setOther(String other) {
        this.other = other;
    }

    

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the groupid
     */
    public long getGroupid() {
        return groupid;
    }

    /**
     * @param groupid the groupid to set
     */
    public void setGroupid(long groupid) {
        this.groupid = groupid;
    }

    public void setId(long id) {
        this.id = id;
    }
    


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    @Override
    public String toString() {
        return "MedicineBrandNameEntity{" + "id=" + id + ", brandName=" + brandName + ", company=" + company + ", genericName=" + genericName + ", usedFor=" + usedFor + ", type=" + type + ", groupid=" + groupid + ", description=" + description + ", HSN=" + HSN + ", other=" + other + ", lastTouched=" + lastTouched + '}';
    }

    
}