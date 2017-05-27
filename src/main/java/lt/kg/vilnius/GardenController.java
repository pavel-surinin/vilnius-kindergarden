package lt.kg.vilnius;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Pavel on 2017-05-27.
 */
@RestController
public class GardenController {

    @Autowired
    GardenService service;

    @GetMapping("/")
    public Iterable<GardenEntity> findAll(){
        return service.findAll();
    }
}
