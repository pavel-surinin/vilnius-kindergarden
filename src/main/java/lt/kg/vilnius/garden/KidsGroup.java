package lt.kg.vilnius.garden;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lt.kg.vilnius.garden.GardenEntity;
import lt.kg.vilnius.garden.GroupTypes;

import javax.persistence.*;

/**
 * Created by Pavel on 2017-07-01.
 */
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
@Entity
@Table(name = "KIDS_GROUP")
public class KidsGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GROUP_ID")
    private long id;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private Long childrenCount;

    @Enumerated(EnumType.STRING)
    private GroupTypes type;

    @Column(nullable = false)
    private Float ageFrom;

    @Column(nullable = false)
    private Float ageTo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "GARDEN_ID")
    private GardenEntity garden;

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

    public Long getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(Long childrenCount) {
        this.childrenCount = childrenCount;
    }

    public GroupTypes getType() {
        return type;
    }

    public void setType(GroupTypes type) {
        this.type = type;
    }

    public Float getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(Float ageFrom) {
        this.ageFrom = ageFrom;
    }

    public Float getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(Float ageTo) {
        this.ageTo = ageTo;
    }

    public GardenEntity getGarden() {
        return garden;
    }

    public void setGarden(GardenEntity garden) {
        this.garden = garden;
    }
}
