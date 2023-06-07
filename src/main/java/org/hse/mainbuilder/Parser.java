package org.hse.mainbuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Parser {
    public HashMap<Long, Unit> jsonToMap(File file) throws IOException {
        HashMap<Long, Unit> unitMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory jsonFactory = objectMapper.getFactory();

        try (JsonParser jsonParser = jsonFactory.createParser(file)) {
            if (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    String fieldName = jsonParser.getCurrentName();
                    if ("units".equals(fieldName)) {
                        jsonParser.nextToken();
                        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                            Unit unit = parseUnit(jsonParser);
                            unitMap.put(unit.id, unit);
                        }
                    }
                }
            }
        }
        return unitMap;
    }

    private static Unit parseUnit(JsonParser jsonParser) throws IOException {
        Long id = null;
        String text = null;
        Coordinates coordinates = null;

        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            jsonParser.nextToken();

            if ("id".equals(fieldName)) {
                id = jsonParser.getValueAsLong();
            } else if ("text".equals(fieldName)) {
                text = jsonParser.getValueAsString();
            } else if ("coordinates".equals(fieldName)) {
                coordinates = parseCoordinates(jsonParser);
            }
        }

        return new Unit(id, text, coordinates);
    }

    private static Coordinates parseCoordinates(JsonParser jsonParser) throws IOException {
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0;

        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            jsonParser.nextToken();

            if ("x1".equals(fieldName)) {
                x1 = jsonParser.getValueAsInt();
            } else if ("y1".equals(fieldName)) {
                y1 = jsonParser.getValueAsInt();
            } else if ("x2".equals(fieldName)) {
                x2 = jsonParser.getValueAsInt();
            } else if ("y2".equals(fieldName)) {
                y2 = jsonParser.getValueAsInt();
            }
        }

        return new Coordinates(x1, y1, x2, y2);
    }
}
