package info.matchingservice.webapp.custom_rest.viewmodels;

import org.joda.time.LocalDate;

import java.util.HashMap;

/**
 * Created by jonathan on 15-12-15.
 */
public class Interest {

    private final String name;
    private final HashMap<String, LocalDate> period = new HashMap<>(2);
    private final int timeAvailable;

    public Interest(String name, final LocalDate from, final LocalDate till, final int timeAvailable) {
        this.name = name;
        period.put("from", from);
        period.put("till", till);
        this.timeAvailable = timeAvailable;

    }




}
