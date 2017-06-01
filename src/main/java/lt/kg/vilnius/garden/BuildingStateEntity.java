package lt.kg.vilnius.garden;

import javax.persistence.*;

/**
 * Created by Pavel on 2017-05-30.
 */
@Entity
@Table(name = "BUILDING_STATE")
public class BuildingStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private GardenEntity garden;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private Float outsideState;

    @Column(nullable = false)
    private Float insideState;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Float getOutsideState() {
        return outsideState;
    }

    public void setOutsideState(Float outsideState) {
        this.outsideState = outsideState;
    }

    public Float getInsideState() {
        return insideState;
    }

    public void setInsideState(Float insideState) {
        this.insideState = insideState;
    }
}
