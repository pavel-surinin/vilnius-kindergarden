package lt.kg.vilnius.garden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Pavel on 2017-05-27.
 */
@Service
public class GardenService {

    @Autowired
    private GardenRepository repository;
    @Autowired
    private GroupRepository groupRepository;


    public Iterable<GardenEntity> findAll() {
        return repository.findAll();
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public GardenEntity save(GardenEntity garden) {
        return repository.save(garden);
    }

    public GardenEntity findById(Long id) {
        return repository.findById(id);
    }

    public KidsGroup saveKidsGroup(KidsGroup group, Long id) {
        GardenEntity garden = repository.findByIdFromSource(id);
        group.setGarden(garden);
        return groupRepository.save(group);
    }
}
