package lt.kg.vilnius;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Pavel on 2017-05-27.
 */
@Service
public class GardenService {

    @Autowired
    private GardenRepository repository;

    public Iterable<GardenEntity> findAll() {
        return repository.findAll();
    }
}
