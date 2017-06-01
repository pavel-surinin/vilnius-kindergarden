package lt.kg.vilnius.garden;

import javax.persistence.*;

/**
 * Created by Pavel on 2017-05-28.
 */
@Entity
@Table(name = "MISSED_DAYS")
public class MissedDaysEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MISSED_DAYS_INFO_ID", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private Long amountOfKids;

    @Column(nullable = false)
    private Long sickMissedDays;

    @Column(nullable = false)
    private Long otherApprovedMissedDays;

    @Column(nullable = false)
    private Long norApprovedMissedDays;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmountOfKids() {
        return amountOfKids;
    }

    public void setAmountOfKids(Long amountOfKids) {
        this.amountOfKids = amountOfKids;
    }

    public Long getSickMissedDays() {
        return sickMissedDays;
    }

    public void setSickMissedDays(Long sickMissedDays) {
        this.sickMissedDays = sickMissedDays;
    }

    public Long getOtherApprovedMissedDays() {
        return otherApprovedMissedDays;
    }

    public void setOtherApprovedMissedDays(Long otherApprovedMissedDays) {
        this.otherApprovedMissedDays = otherApprovedMissedDays;
    }

    public Long getNorApprovedMissedDays() {
        return norApprovedMissedDays;
    }

    public void setNorApprovedMissedDays(Long norApprovedMissedDays) {
        this.norApprovedMissedDays = norApprovedMissedDays;
    }
}

