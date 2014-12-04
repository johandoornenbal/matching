package info.matchingservice.dom.Actor;

import org.apache.isis.applib.annotation.ViewModel;

import nl.yodo.dom.TrustLevel;

@ViewModel
public class Referral {

    public Referral(Person referrer, TrustLevel trustLevel) {
        this.referrer = referrer;
        this.trustLevel = trustLevel;
    }

    private Person referrer;

    public Person getReferrer() {
        return referrer;
    }

    public void setReferrer(final Person ref) {
        this.referrer = ref;
    }

    private TrustLevel trustLevel;

    public TrustLevel getTrustLevel() {
        return trustLevel;
    }

    public void setTrustLevel(final TrustLevel level) {
        this.trustLevel = level;
    }
}
