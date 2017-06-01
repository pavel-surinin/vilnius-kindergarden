package lt.kg.vilnius;

import lt.kg.vilnius.garden.BuildingStateEntity;
import lt.kg.vilnius.garden.GardenEntity;
import lt.kg.vilnius.garden.MissedDaysEntity;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by Pavel on 2017-05-27.
 */
public class CsvUtilsTest {

    private static final String LABEL = "Medynelis";

    @Test
    public void shouldParseGeneralGardensInfo() throws Exception {
        List<GardenEntity> gardenEntities = CsvUtils.parseGeneralGardensInfo();
        assertNotNull(gardenEntities);
        assertThat(gardenEntities.size(), is(156));
    }

    @Test
    public void shouldParseMissedDaysInfo() throws IOException {
        List<MissedDaysEntity> missedDaysEntities = CsvUtils.parseMissedDaysInfo();
        assertNotNull(missedDaysEntities);
        MissedDaysEntity missedDaysFirstEntity = missedDaysEntities.get(0);
        assertThat(missedDaysFirstEntity.getLabel(), is(LABEL));
        assertThat(missedDaysEntities.size(), is(129));
        assertThat(missedDaysFirstEntity.getAmountOfKids(), is(230L));
        assertThat(missedDaysFirstEntity.getNorApprovedMissedDays(), is(1361L));
        assertThat(missedDaysFirstEntity.getOtherApprovedMissedDays(), is(12977L));
        assertThat(missedDaysFirstEntity.getSickMissedDays(), is(10652L));
    }

    @Test
    public void shouldParseBuildingState() throws IOException {
        List<BuildingStateEntity> builingStates =  CsvUtils.parseBuildingState();
        BuildingStateEntity buildingStateFirstEntity = builingStates.get(0);
        //assertion
        assertThat(builingStates, notNullValue());
        assertThat(buildingStateFirstEntity.getId(), nullValue());
        assertThat(buildingStateFirstEntity.getLabel(), is(LABEL));
        assertThat(buildingStateFirstEntity.getInsideState(), is(3.72F));
        assertThat(buildingStateFirstEntity.getOutsideState(), is(3.27F));
    }

}