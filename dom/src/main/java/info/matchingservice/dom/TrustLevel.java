package info.matchingservice.dom;


// compare to kan gebruikt worden op enum: de eerst gedeclareerde waarde heeft de laagste ordening
// dus compare(BANNED, OUTERCIRCLE) levert negatieve waarde
public enum TrustLevel implements TitledEnum{
    BANNED("verbannen-niveau (buitenste (cirkel 5)"),
    OUTER_CIRCLE("buitenring-niveau (cirkel 4)"),
    ENTRY_LEVEL("instap-niveau (middelste cirkel 3)"),
    INNER_CIRCLE("binnenring-niveau (cirkel 2)"),
    INTIMATE("intimi-niveau (binnenste circle 1)");

    private String title;
    
    TrustLevel (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }
}
