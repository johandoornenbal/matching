package info.matchingservice.webapp.custom_rest.viewmodels;

import info.matchingservice.webapp.custom_rest.utils.JsonConvertable;

/**
 * Created by jonathan on 27-12-15.
 */
public class Education implements JsonConvertable {

    private final String title, institute, honoursProgram;

    public Education(String title, String institute, String honoursProgram) {
        this.title = title;
        this.institute = institute;
        this.honoursProgram = honoursProgram;
    }

    public Education(info.matchingservice.dom.Xtalus.Education e){
        this.honoursProgram = e.getHonoursProgramm();
        this.institute = e.getInstitute();
        this.title = e.getTitle();

    }
}
