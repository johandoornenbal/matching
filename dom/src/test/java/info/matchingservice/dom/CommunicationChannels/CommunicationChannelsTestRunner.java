package info.matchingservice.dom.CommunicationChannels;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by jonathan on 21-4-15.
 */
public class CommunicationChannelsTestRunner {

public static void main (String[] args){



    Result result = JUnitCore.runClasses(EmailTest.class, PhoneTest.class, CommunicationChannelsTest.class);


    for(Failure failure: result.getFailures()){

        System.out.println(failure.toString());


    }

}





}
