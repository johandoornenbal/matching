package nl.socrates.app.integtest;


import org.apache.isis.core.integtestsupport.IsisSystemForTest;

/**
 * Holds an instance of an {@link IsisSystemForTest} as a {@link ThreadLocal} on
 * the current thread, initialized with Socrates's domain services and with
 * {@link org.estatio.fixture.EstatioBaseLineFixture reference data fixture}.
 */
public class SocratesSystemInitializer {

    private SocratesSystemInitializer() {
    }

    public static IsisSystemForTest initIsft() {
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if (isft == null) {
            isft = new SocratesIntegTestBuilder().build().setUpSystem();
            IsisSystemForTest.set(isft);
        }
        return isft;
    }
}