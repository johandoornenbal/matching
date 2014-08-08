package nl.xtalus.dom.profile;

public enum ProfileType {
    COMMISSIONER("Opdrachtgever", CommissionerProfile.class),
    BUSINESS("MKB", BusinessProfile.class),
    STUDENT("Student", StudentProfile.class);

    private String title;

    private Class<? extends Profile> cls;

    ProfileType(String title, Class<? extends Profile> cls) {
        this.title = title;
        this.cls = cls;
    }

    public String title() {
        return title;
    }

    public Class<? extends Profile> cls() {
        return cls;
    }
}
