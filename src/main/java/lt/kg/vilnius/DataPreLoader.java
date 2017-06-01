package lt.kg.vilnius;

import lt.kg.vilnius.garden.GardenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Pavel on 2017-06-02.
 */
@Service
public class DataPreLoader {

    @Autowired
    GardenService service;

    public void load() {
        CsvUtils.parseGeneralGardensInfo().forEach((garden) -> service.save(garden));

        service.findAll().forEach(garden -> {
            CsvUtils.parseBuildingState().forEach(state -> {
                if (garden.getLabel().equals(state.getLabel())){
                    garden.setBuildingStateInfo(state);
                    service.save(garden);
                }
            });
        });
        service.findAll().forEach(garden -> {
            CsvUtils.parseMissedDaysInfo().forEach(md -> {
                if (garden.getLabel().equals(md.getLabel())){
                    garden.setMissedDaysInfo(md);
                    service.save(garden);
                }
            });
        });
    }
}
