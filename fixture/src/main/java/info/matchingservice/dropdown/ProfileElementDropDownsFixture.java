package info.matchingservice.dropdown;

import info.matchingservice.dom.Profile.ProfileElementCategory;

public class ProfileElementDropDownsFixture extends ProfileElementDropDownAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createDropdown(ProfileElementCategory.QUALITY, "ijverig", executionContext);
        createDropdown(ProfileElementCategory.QUALITY, "aandachtig", executionContext);
        createDropdown(ProfileElementCategory.QUALITY, "analytisch", executionContext);
        createDropdown(ProfileElementCategory.QUALITY, "out-of-the-box", executionContext);
        createDropdown(ProfileElementCategory.QUALITY, "verbindend", executionContext);
        createDropdown(ProfileElementCategory.QUALITY, "empatisch", executionContext);
        createDropdown(ProfileElementCategory.QUALITY, "supercool", executionContext);
        createDropdown(ProfileElementCategory.QUALITY, "nieuwsgierig", executionContext);
    }

}
