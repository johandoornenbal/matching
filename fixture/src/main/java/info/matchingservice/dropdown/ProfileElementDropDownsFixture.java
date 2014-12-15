package info.matchingservice.dropdown;

import info.matchingservice.dom.Profile.ProfileElementType;

public class ProfileElementDropDownsFixture extends ProfileElementDropDownAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createDropdown(ProfileElementType.QUALITY, "ijverig", executionContext);
        createDropdown(ProfileElementType.QUALITY, "aandachtig", executionContext);
        createDropdown(ProfileElementType.QUALITY, "analytisch", executionContext);
        createDropdown(ProfileElementType.QUALITY, "out-of-the-box", executionContext);
        createDropdown(ProfileElementType.QUALITY, "verbindend", executionContext);
        createDropdown(ProfileElementType.QUALITY, "empatisch", executionContext);
        createDropdown(ProfileElementType.QUALITY, "supercool", executionContext);
        createDropdown(ProfileElementType.QUALITY, "nieuwsgierig", executionContext);
        createDropdown(ProfileElementType.QUALITY, "oorlogszuchtig", executionContext);
        createDropdown(ProfileElementType.QUALITY, "doortastend", executionContext);
        createDropdown(ProfileElementType.QUALITY, "leidinggevend", executionContext);
    }

}
