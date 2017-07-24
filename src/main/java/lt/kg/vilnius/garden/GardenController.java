package lt.kg.vilnius.garden;

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

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("api/garden")
    public Iterable<GardenEntity> findAll(){
        return service.findAll();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("api/garden/{id}")
    public GardenEntity findById(@PathVariable Long id){
        return service.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("api/garden")
    public GardenEntity save(@RequestBody GardenEntity garden){
        return service.save(garden);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("api/garden")
    public void deleteAll(){
        service.deleteAll();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("api/garden/{id}")
    public KidsGroup save(@RequestBody KidsGroup group, @PathVariable Long id){
        return service.saveKidsGroup(group, id);
    }
}
