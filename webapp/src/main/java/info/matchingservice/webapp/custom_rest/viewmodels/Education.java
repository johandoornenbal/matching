package info.matchingservice.webapp.custom_rest.viewmodels;

import info.matchingservice.webapp.custom_rest.utils.JsonConvertable;

/**
 * Created by jonathan on 27-12-15.
 */
public class Education implements JsonConvertable {

    private final String title, institute, location, honoursProgram;

    public Education(String title, String institute, String location, String honoursProgram) {
        this.title = title;
        this.institute = institute;
        this.location = location;
        this.honoursProgram = honoursProgram;
    }

    public Education(info.matchingservice.dom.Xtalus.Education e){
        this.honoursProgram = e.getHonoursProgramm();
        this.institute = e.getInstitute();
        this.location = e.getLocation();
        this.title = e.getTitle();

    }
}
