package lt.kg.vilnius.garden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Pavel on 2017-05-27.
 */
@RestController()
public class GardenController {

    @Autowired
    GardenService service;

    @GetMapping
    public Iterable<GardenEntity> findAll(){
        return service.findAll();
    }

    @GetMapping("api/garden/{id}")
    public GardenEntity findById(@PathVariable Long id){
        return service.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("api/garden")
    public GardenEntity save(@RequestBody GardenEntity garden){
        return service.save(garden);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("api/garden")
    public void deleteAll(){
        service.deleteAll();
    }
}
