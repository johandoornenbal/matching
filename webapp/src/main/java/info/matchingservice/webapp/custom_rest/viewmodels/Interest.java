package info.matchingservice.webapp.custom_rest.viewmodels;

import info.matchingservice.dom.DemandSupply.Demand;
import org.joda.time.LocalDate;

import java.util.HashMap;

/**
 * Created by jonathan on 15-12-15.
 */
public class Interest {

    private final String name;
    private final HashMap<String, String> period = new HashMap<>(2);
    private final int timeAvailable;

    public Interest(String name, final LocalDate from, final LocalDate till, final int timeAvailable) {
        this.name = name;
        period.put("from", from.toString("dd-MM-yyyy"));
        period.put("till", till.toString("dd-MM-yyyy"));
        this.timeAvailable = timeAvailable;

    }







}
