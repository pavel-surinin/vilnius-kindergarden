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
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private GardenEntity garden;

    @Column(nullable = false)
    private Long ammountOfKids;

    @Column(nullable = false)
    private Long sickMissedDays;

    @Column(nullable = false)
    private Long otherApprovedMissedDays;

    @Column(nullable = false)
    private Long norApprovedMissedDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GardenEntity getGarden() {
        return garden;
    }

    public void setGarden(GardenEntity garden) {
        this.garden = garden;
    }

    public Long getAmmountOfKids() {
        return ammountOfKids;
    }

    public void setAmmountOfKids(Long ammountOfKids) {
        this.ammountOfKids = ammountOfKids;
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

