package lt.kg.vilnius;

import lt.kg.vilnius.garden.GardenEntity;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Pavel on 2017-05-27.
 */
public class CsvUtilsTest {
    @Test
    public void shouldParseGeneralGardensInfo() throws Exception {
        List<GardenEntity> gardenEntities = CsvUtils.parseGeneralGardensInfo();
        assertNotNull(gardenEntities);
        assertThat(gardenEntities.size(), CoreMatchers.is(156));
    }

}