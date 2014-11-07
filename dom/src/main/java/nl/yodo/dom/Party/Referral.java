package nl.yodo.dom.Party;

import org.apache.isis.applib.annotation.ViewModel;

import nl.yodo.dom.TrustLevel;

@ViewModel
public class Referral {

    public Referral(YodoPerson referrer, TrustLevel trustLevel) {
        this.referrer = referrer;
        this.trustLevel = trustLevel;
    }

    private YodoPerson referrer;

    public YodoPerson getReferrer() {
        return referrer;
    }

    public void setReferrer(final YodoPerson ref) {
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
