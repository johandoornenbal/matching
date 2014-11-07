package nl.yodo.fixture;

public final class Util {
    
    private Util(){}

    public static String localNameFor(final String prefix, String user) {
        return prefix + "-" + coalesce(user, "current");
    }

    public static String coalesce(final String... strings) {
        for (String str : strings) {
            if(str != null) { return str; }
        }
        return null;
    }

}