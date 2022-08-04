package post.parthmistry.democlient.util;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvUtil {

    public static List<Map<String, Object>> prepareCsvRecordList(String csvFilePath) throws IOException {
        var csvMapper = new CsvMapper();
        var csvSchema = csvMapper.typedSchemaFor(Map.class).withHeader();

        var records = new ArrayList<Map<String, Object>>();

        var iterator = csvMapper.readerFor(Map.class)
                .with(csvSchema.withColumnSeparator(','))
                .readValues(new File(csvFilePath));

        try (iterator) {
            while (iterator.hasNext()) {
                Map<String, Object> recordMap = (Map<String, Object>) iterator.next();
                records.add(recordMap);
            }
        }

        return records;
    }

}
