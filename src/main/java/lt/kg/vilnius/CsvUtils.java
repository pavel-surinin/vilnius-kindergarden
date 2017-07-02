package lt.kg.vilnius;

import lt.kg.vilnius.garden.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-05-27.
 */
public class CsvUtils {
    /**
     * Parses csv with missed days info to {@Link} missed days entities
     *
     * @return
     */
    public static List<MissedDaysEntity> parseMissedDaysInfo() {
        Reader in;
        Iterator<CSVRecord> records = null;
        try {
            in = new FileReader("./src/main/resources/csv/missed-days.csv");
            records = CSVFormat.DEFAULT.parse(in).iterator();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<MissedDaysEntity> missedDaysEntities = new ArrayList<>();
        records.next();
        records.forEachRemaining(record -> {
            MissedDaysEntity missedDaysEntity = new MissedDaysEntity();
            missedDaysEntity.setLabel(record.get(0));
            missedDaysEntity.setSickMissedDays(Long.valueOf(record.get(2)));
            missedDaysEntity.setOtherApprovedMissedDays(Long.valueOf(record.get(3)));
            missedDaysEntity.setNorApprovedMissedDays(Long.valueOf(record.get(4)));
            missedDaysEntity.setAmountOfKids(Long.valueOf(record.get(1)));
            missedDaysEntities.add(missedDaysEntity);
        });
        return missedDaysEntities;

    }

    /**
     * Parses building state info from csv to entities
     *
     * @return List of {@link BuildingStateEntity}
     */
    public static List<BuildingStateEntity> parseBuildingState() {
        List<BuildingStateEntity> buildingStates;
        Reader in;
        buildingStates = new ArrayList<>();
        Iterator<CSVRecord> records = null;
        try {
            in = new FileReader("./src/main/resources/csv/building-state.csv");
            records = CSVFormat.DEFAULT.parse(in).iterator();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<MissedDaysEntity> missedDaysEntities = new ArrayList<>();
        records.next();
        records.forEachRemaining(record -> {
            BuildingStateEntity entity = new BuildingStateEntity();
            entity.setLabel(record.get(0));
            entity.setInsideState((Float) getNumber(record.get(11)));
            entity.setOutsideState((Float) getNumber(record.get(10)));
            if (entity.getInsideState() != null && entity.getOutsideState() != null) {
                buildingStates.add(entity);
            }
        });
        return buildingStates;
    }

    private static Number getNumber(String s) {
        try {
            return Float.valueOf(s);
        } catch (NumberFormatException e) {
            Float value = Arrays.stream(s.split("m"))
                    .filter(val -> {
                        try {
                            Float.valueOf(val);
                            return true;
                        } catch (Exception ex) {
                            return false;
                        }
                    })
                    .map(Float::valueOf)
                    .findFirst()
                    .orElse(null);
            return value;
        }
    }

    /**
     * Parses kids groups info from csv to entities
     *
     * @return List of {@link KidsGroup}
     */
    public static List<KidsGroup> parseGroups() {
        Reader in;
        Iterable<CSVRecord> records = null;
        try {
            in = new FileReader("./src/main/resources/csv/groups.csv");
            records = CSVFormat.RFC4180.parse(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<KidsGroup> groups = new ArrayList<>();
        records.iterator().next();
        for (CSVRecord record : records) {
            KidsGroup group = new KidsGroup();
            List<Float> valuesFromTo = getValuesFromTo(record.get(3));
            group.setAgeFrom(valuesFromTo.get(0));
            if (valuesFromTo.size() == 2) {
                group.setAgeTo(Float.valueOf(valuesFromTo.get(1)));
            } else {
                group.setAgeTo(Float.valueOf(99));
            }
            group.setLabel(record.get(1));
            group.setType(getGroupType(record.get(1)));
            group.setChildrenCount(Long.valueOf(record.get(2)));
            GardenEntity gardenEntity = new GardenEntity();
            gardenEntity.setIdFromSource(Long.valueOf(record.get(4)));
            group.setGarden(gardenEntity);
            groups.add(group);
        }
        return groups;
    }

    private static GroupTypes getGroupType(String name) {
        if (name.toLowerCase().contains("alerg")) return GroupTypes.ALLERGY;
        if (name.toLowerCase().contains("lopš")) return GroupTypes.SMALL;
        if (name.toLowerCase().contains("lops")) return GroupTypes.SMALL;
        if (name.toLowerCase().contains("log")) return GroupTypes.SPEECH;
        if (name.toLowerCase().contains("spec")) return GroupTypes.SPECIAL;
        return GroupTypes.NORMAL;
    }

    private static List<Float> getValuesFromTo(String originalValues) {
        return Arrays.stream(originalValues.split(" "))
                .map(val -> (Float) getNumber(val.replace(",", ".")))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public enum HeadersGeneral {
        ID, LABEL, ADDRESS, PHONE, SCHOOLNO, WWW, EMAIL, SCHOOL_TYPE, BUILDDATE, ELDERATE
    }

    /**
     * Parses csv with kindergartens info to {@Link GardenEntity}
     *
     * @return List of {@Link GardenEntity}
     */
    public static List<GardenEntity> parseGeneralGardensInfo() {
        Reader in;
        Iterable<CSVRecord> records = null;
        try {
            in = new FileReader("./src/main/resources/csv/gardens.csv");
            records = CSVFormat.RFC4180.withHeader(HeadersGeneral.class).parse(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<GardenEntity> gardenEntities = new ArrayList<>();
        records.iterator().next();
        for (CSVRecord record : records) {
            GardenEntity garden = new GardenEntity();
            garden.setIdFromSource(Long.valueOf(record.get(0)));
            garden.setLabel(record.get(HeadersGeneral.LABEL));
            garden.setAddress(record.get(HeadersGeneral.ADDRESS));
            garden.setPhone(record.get(HeadersGeneral.PHONE));
            garden.setSchoolNo(Long.valueOf(record.get(HeadersGeneral.SCHOOLNO)));
            garden.setWww(record.get(HeadersGeneral.WWW));
            garden.setEmail(record.get(HeadersGeneral.EMAIL));
            garden.setSchoolType(record.get(8));
            garden.setBuildDate(getDate(record));
            garden.setElderate(record.get(10));
            gardenEntities.add(garden);
        }
        return gardenEntities;
    }

    /**
     * Return date in csv or if failed 1111-11-11
     *
     * @param record
     * @return {@link Date} object
     */
    private static Date getDate(CSVRecord record) {
        DateFormat format = new SimpleDateFormat("YYYY-MM-DD");
        Date date = null;
        try {
            date = format.parse(record.get(9));
        } catch (ParseException e) {
            try {
                date = format.parse("1111-11-11");
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return date;
    }
}
