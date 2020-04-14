package Utils;

public class OAuthCredentials {

    /** Value of the "API Key". */
    public static final String API_KEY = "QMXaCuYT8iuhYPJokx4lw";

    /** Value of the "API Secret". */
    public static final String API_SECRET = "k3BKaBQUubz3fFihp80JzZ7eg5vKyGno";

    /** Port in the "Callback URL". */
    public static final int PORT = 4000;

    /** Domain name in the "Callback URL". */
    public static final String DOMAIN = "https://zoom.us";

    public static void errorIfNotSpecified() {
        if (API_KEY.startsWith("Enter ") || API_SECRET.startsWith("Enter ")) {
            System.out.println(
                    "Enter API Key and API Secret from http://www.dailymotion.com/profile/developer"
                            + " into API_KEY and API_SECRET in " + OAuthCredentials.class);
            System.exit(1);
        }
    }
}