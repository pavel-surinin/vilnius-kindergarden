package lt.kg.vilnius;

import lt.kg.vilnius.garden.GardenEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Pavel on 2017-05-27.
 */
public class CsvUtils {

    public enum HeadersGeneral {
        ID, LABEL, ADDRESS, PHONE, SCHOOLNO, WWW, EMAIL, SCHOOL_TYPE, BUILDDATE, ELDERATE
    }

    public static List<GardenEntity> parseGeneralGardensInfo() throws IOException {
        Reader in = new FileReader("./src/main/resources/csv/gardens.csv");
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader(HeadersGeneral.class).parse(in);
        List<GardenEntity> gardenEntities = new ArrayList<>();
        records.iterator().next();
        for (CSVRecord record : records) {
            GardenEntity garden = new GardenEntity();
            garden.setId(Long.valueOf(record.get(HeadersGeneral.ID)));
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
     * @return
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
