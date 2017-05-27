package lt.kg.vilnius;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Pavel on 2017-05-27.
 */
@RestController
public class GardenController {

    @Autowired
    GardenService service;

    @GetMapping("api/garden")
    public Iterable<GardenEntity> findAll(){
        return service.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("api/garden")
    public GardenEntity save(@RequestBody GardenEntity garden){
        return service.save(garden);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteAll(){
        service.deleteAll();
    }
}
