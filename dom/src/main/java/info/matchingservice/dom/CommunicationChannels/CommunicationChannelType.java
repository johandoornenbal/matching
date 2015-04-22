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
    PHONE_MAIN(Phone.class),
    PHONE_PRIVATE(Phone.class),
    PHONE_HOME(Phone.class),
    PHONE_WORK(Phone.class),

    EMAIL_MAIN(Email.class),
    EMAIL_WORK(Email.class),
    EMAIL_HOME(Email.class),

    ADDRESS_MAIN(Address.class),
    ADDRESS_COMPANY(Address.class),
    ADDRESS_SECUNDAIR(Address.class);


    //TODO Subtypes , sub type van telefoon bijv: huistelefoon, mobiel nr


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
