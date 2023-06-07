package org.hse.mainbuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Template {
    File file;
    Map<Long, Unit> units;
    Long lastKey;

    public Template() {
        lastKey = 0L;
        this.units = new HashMap<>();
        file = new File("../");
    }

    boolean addUnit(Unit unit){
        units.put(lastKey++, unit);
        return true;
    }
}
