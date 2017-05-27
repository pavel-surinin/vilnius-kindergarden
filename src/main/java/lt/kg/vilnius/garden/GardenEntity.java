package lt.kg.vilnius.garden;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Pavel on 2017-05-27.
 */
@Entity
public class GardenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String label;
    @Column(nullable = false)
    private String address;
    private String phone;
    @Column(nullable = false)
    private Long schoolNo;
    private String www;
    private String email;
    @Column(nullable = false)
    private String schoolType;
    @Column(nullable = false)
    private Date buildDate;
    @Column(nullable = false)
    private String elderate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(Long schoolNo) {
        this.schoolNo = schoolNo;
    }

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    public String getElderate() {
        return elderate;
    }

    public void setElderate(String elderate) {
        this.elderate = elderate;
    }

    @Override
    public String toString() {
        return "GardenEntity{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", schoolNo=" + schoolNo +
                ", www='" + www + '\'' +
                ", email='" + email + '\'' +
                ", schoolType='" + schoolType + '\'' +
                ", buildDate=" + buildDate +
                ", elderate='" + elderate + '\'' +
                '}';
    }
}
