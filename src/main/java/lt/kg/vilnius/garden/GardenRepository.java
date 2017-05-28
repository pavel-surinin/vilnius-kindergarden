package lt.kg.vilnius.garden;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Pavel on 2017-05-27.
 */
public interface GardenRepository extends CrudRepository<GardenEntity, Long> {

    List<GardenEntity> findByLabel(String lastName);

    GardenEntity findById(Long id);
}