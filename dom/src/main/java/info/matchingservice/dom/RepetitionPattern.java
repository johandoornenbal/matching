package info.matchingservice.dom;

public enum RepetitionPattern implements TitledEnum {

    SINGLE_INSTANCE("Enkelvoudig"),
    MULTIPLE_INSTANCE("Meervoudig");

    private String title;
    
    RepetitionPattern (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }
}
