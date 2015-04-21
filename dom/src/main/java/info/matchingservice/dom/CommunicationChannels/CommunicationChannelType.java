package info.matchingservice.dom.CommunicationChannels;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import info.matchingservice.dom.CommunicationChannels.Interfaces.TitledEnum;
import info.matchingservice.dom.Utils.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jonathan on 3-4-15.
 */
public enum CommunicationChannelType implements TitledEnum {


    NIKS(Niks.class),
    PHONE_NUMBER(Phone.class),
    MOBILE_NUMBER(Phone.class);


    //TODO Subtypes ,


   // EMAIL_ADDRES(Email.class);

    private Class<? extends CommunicationChannel> cls;


    private CommunicationChannelType(final Class<? extends CommunicationChannel> cls) {
        this.cls = cls;
    }

    public String title() {
        return StringUtils.enumTitle(this.toString());
    }

    public static List<CommunicationChannelType> matching(final Class<? extends CommunicationChannel> cls) {
        return Lists.newArrayList(Iterables.filter(Arrays.asList(values()), new Predicate<CommunicationChannelType>() {

            @Override
            public boolean apply(final CommunicationChannelType input) {
                return input.cls == cls;
            }
        }));
    }




}
