package lt.kg.vilnius;

import lt.kg.vilnius.garden.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Pavel on 2017-05-27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
public class GardenControllerIntegrationTest {
    private static final String URI = "/api/garden";
    private static final String LABEL_KG_KIDS_BEST = "KidsBest";
    private static final long NUMBER_OF_KIDS_IN_KG = 100L;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private GardenService service;

    @Before
    public void setUp() {
        service.deleteAll();
    }

    @Test
    public void shouldGetEmptyListOfUsers() {
        //setup
        ResponseEntity<GardenEntity[]> response = template.getForEntity(URI, GardenEntity[].class);
        GardenEntity[] gardens = response.getBody();
        //assert
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(gardens.length, is(0));
    }

    @Test
    public void shouldSaveAndDeleteSchool() {
        //setup
        GardenEntity garden = generateGardenEntity(LABEL_KG_KIDS_BEST);
        //execute
        ResponseEntity<GardenEntity> saveResponse = template.postForEntity(URI, garden, GardenEntity.class);
        ResponseEntity<GardenEntity[]> getOneResponse = template.getForEntity(URI, GardenEntity[].class);
        template.delete(URI);
        ResponseEntity<GardenEntity[]> getZeroResponse = template.getForEntity(URI, GardenEntity[].class);
        //assert
        assertThat(saveResponse.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(getOneResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(getOneResponse.getBody().length, is(1));
        assertThat(getZeroResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(getZeroResponse.getBody().length, is(0));
    }

    @Test
    public void shouldSaveCSVRecordsToDB() throws IOException {
        List<GardenEntity> gardenEntities = CsvUtils.parseGeneralGardensInfo();
        for (GardenEntity ent : gardenEntities) {
            ResponseEntity<GardenEntity> saveResponse = template.postForEntity(URI, ent, GardenEntity.class);
            assertThat(saveResponse.getBody().getIdFromSource(), is(ent.getIdFromSource()));
            assertThat(saveResponse.getStatusCode(), is(HttpStatus.CREATED));
            assertThat(saveResponse.getBody().getAddress(), is(ent.getAddress()));
            assertThat(saveResponse.getBody().getLabel(), is(ent.getLabel()));
            assertThat(saveResponse.getBody().getSchoolNo(), is(ent.getSchoolNo()));
        }
        ResponseEntity<GardenEntity[]> getResponse = template.getForEntity(URI, GardenEntity[].class);
        assertThat(getResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(getResponse.getBody().length, is(gardenEntities.size()));
    }

    @Test
    public void shouldReturnRecordById() {
        //setup
        GardenEntity kidsBest = generateGardenEntity(LABEL_KG_KIDS_BEST);
        //execution
        ResponseEntity<GardenEntity> saveResp = template.postForEntity(URI, kidsBest, GardenEntity.class);
        Map<String, Long> params = new HashMap<>();
        params.put("id", saveResp.getBody().getId());
        ResponseEntity<GardenEntity> responseGetById = template.getForEntity(URI + "/{id}", GardenEntity.class, params);
        //assertion
        assertThat(responseGetById.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseGetById.getBody().getLabel(), is(LABEL_KG_KIDS_BEST));
    }

    public void shouldSaveMissedDaysToGardenEntity() {
        //setup
        GardenEntity gardenEntity = generateGardenEntity(LABEL_KG_KIDS_BEST);
        addMissedDaysEntityToGarden(gardenEntity);
        //execution
        ResponseEntity<GardenEntity> createResponse = template.postForEntity(URI, gardenEntity, GardenEntity.class);
        ResponseEntity<GardenEntity[]> allGardensResponse = template.getForEntity(URI, GardenEntity[].class);
        GardenEntity gardenFromDB = allGardensResponse.getBody()[0];
        //assertion
        assertThat(createResponse.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(createResponse.getBody(), notNullValue());
        assertThat(createResponse.getBody().getMissedDaysInfo().getAmountOfKids(), is(NUMBER_OF_KIDS_IN_KG));
        assertThat(allGardensResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(gardenFromDB.getMissedDaysInfo().getAmountOfKids(), is(NUMBER_OF_KIDS_IN_KG));
        assertThat(gardenFromDB.getId(), is(gardenFromDB.getMissedDaysInfo().getId()));
    }

    @Test
    public void shouldParseGeneralMissedDaysCsvAndWriteToDB() throws IOException {
        //setup
        List<GardenEntity> gardenEntities = CsvUtils.parseGeneralGardensInfo();
        List<MissedDaysEntity> missedDaysEntities = CsvUtils.parseMissedDaysInfo();
        //execution
        gardenEntities.forEach(garden -> template.postForEntity(URI, garden, GardenEntity.class));
        ResponseEntity<GardenEntity[]> getAllGardensBefore = template.getForEntity(URI, GardenEntity[].class);
        Arrays.stream(getAllGardensBefore.getBody())
                .forEach(garden -> {
                    missedDaysEntities
                            .stream()
                            .filter(md -> md.getLabel().equals(garden.getLabel()))
                            .findFirst()
                            .ifPresent(md -> {
                                GardenEntity g = garden;
                                g.setMissedDaysInfo(md);
                                ResponseEntity<GardenEntity> saveFullGardenResp = template.postForEntity(URI, g, GardenEntity.class);
                                HashMap<String, Long> params = new HashMap<>();
                                params.put("id", garden.getId());
                                ResponseEntity<GardenEntity> gardenResp = template.getForEntity(URI + "/{id}", GardenEntity.class, params);
                                //assertion
                                assertThat(saveFullGardenResp.getStatusCode(), is(HttpStatus.CREATED));
                                assertThat(gardenResp.getStatusCode(), is(HttpStatus.OK));
                                assertThat(gardenResp.getBody().getLabel(), is(md.getLabel()));
                                assertThat(gardenResp.getBody().getId(), is(saveFullGardenResp.getBody().getId()));
                                assertThat(gardenResp.getBody().getMissedDaysInfo().getLabel(), is(garden.getLabel()));
                            });
                });
    }

    @Test
    public void shouldCreateGardensAndAttachBuildingState() throws IOException {
        //setup
        List<BuildingStateEntity> states = CsvUtils.parseBuildingState();
        CsvUtils.parseGeneralGardensInfo().forEach(garden -> {
            template.postForEntity(URI, garden, GardenEntity.class);
        });
        ResponseEntity<GardenEntity[]> allGardensResp = template.getForEntity(URI, GardenEntity[].class);
        //execution
        for (GardenEntity garden : allGardensResp.getBody()) {
            for (BuildingStateEntity state : states) {
                if (state.getLabel().equals(garden.getLabel())){
                    garden.setBuildingStateInfo(state);
                    ResponseEntity<GardenEntity> gardenSaveResponse = template.postForEntity(URI, garden, GardenEntity.class);
                    BuildingStateEntity stateResp = gardenSaveResponse.getBody().getBuildingStateInfo();
                    HashMap<String, Long> params = new HashMap<>();
                    params.put("id", garden.getId());
                    ResponseEntity<GardenEntity> gardenByIdResp = template.getForEntity(URI + "/{id}", GardenEntity.class, params);
                    //assertion
                    assertThat(gardenSaveResponse.getStatusCode(), is(HttpStatus.CREATED));
                    assertThat(stateResp.getLabel(), is(state.getLabel()));
                    assertThat(stateResp.getOutsideState(), is(state.getOutsideState()));
                    assertThat(stateResp.getInsideState(), is(state.getInsideState()));
                    assertThat(gardenByIdResp.getBody().getBuildingStateInfo().getLabel(), is(garden.getLabel()));
                }
            }
        }
    }

    @Test
    public void shouldAttachGroupsToGarden(){
        CsvUtils.parseGeneralGardensInfo().forEach(gardenEntity -> template.postForEntity(URI, gardenEntity, GardenEntity.class));
        List<KidsGroup> kidsGroups = CsvUtils.parseGroups();
        kidsGroups.stream()
                .forEach(group -> {
                    HashMap<String, Long> params = new HashMap<>();
                    params.put("id", group.getGarden().getIdFromSource());
                    ResponseEntity<KidsGroup> updateWithGroupResp = template.postForEntity(URI + "/{id}", group, KidsGroup.class, params);
                    assertThat(updateWithGroupResp.getStatusCodeValue(), is(202));
                });
        ResponseEntity<GardenEntity[]> allResp = template.getForEntity(URI, GardenEntity[].class);
        List<KidsGroup> groups = Arrays.stream(allResp.getBody())
                .flatMap(gardenEntity -> gardenEntity.getGroups().stream())
                .collect(Collectors.toList());
        assertThat(allResp.getStatusCodeValue(), is(200));
        assertThat(groups.size(), is(1637));

    }

    /**
     * Adds MissedDaysEntity to Garden Entity
     *
     * @param gardenEntity
     */
    private void addMissedDaysEntityToGarden(GardenEntity gardenEntity) {
        MissedDaysEntity missedDaysEntity = new MissedDaysEntity();
        missedDaysEntity.setAmountOfKids(NUMBER_OF_KIDS_IN_KG);
        missedDaysEntity.setNorApprovedMissedDays(10L);
        missedDaysEntity.setOtherApprovedMissedDays(10L);
        missedDaysEntity.setSickMissedDays(1L);
        gardenEntity.setMissedDaysInfo(missedDaysEntity);
    }

    /**
     * Generates garden entity
     *
     * @param label
     * @return
     */
    private GardenEntity generateGardenEntity(String label) {
        GardenEntity garden = new GardenEntity();
        garden.setLabel(label);
        garden.setAddress("a");
        garden.setElderate("a");
        garden.setBuildDate(new Date());
        garden.setSchoolNo(1L);
        garden.setSchoolType("a");
        garden.setIdFromSource(1L);
        return garden;
    }

}
