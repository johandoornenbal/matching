package info.matchingservice.dropdown;

public class QualitiesFixture extends QualityAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createQuality("ijverig", executionContext);
        createQuality("aandachtig", executionContext);
        createQuality("analytisch", executionContext);
        createQuality("out-of-the-box", executionContext);
        createQuality("verbindend", executionContext);
        createQuality("empatisch", executionContext);
    }

}
