package lt.kg.vilnius;

import lt.kg.vilnius.garden.GardenEntity;
import lt.kg.vilnius.garden.GardenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by Pavel on 2017-05-27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
public class GardenControllerIntegrationTest {
    private static final String URI = "/api/garden";

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private GardenService service;

    @Before
    public void setUp(){
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
    public void shouldSaveAndDeleteSchool(){
        //setup
        GardenEntity garden = new GardenEntity();
        garden.setLabel("a");
        garden.setAddress("a");
        garden.setElderate("a");
        garden.setBuildDate(new Date());
        garden.setSchoolNo(1L);
        garden.setSchoolType("a");
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
}
