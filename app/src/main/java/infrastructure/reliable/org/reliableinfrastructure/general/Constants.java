package infrastructure.reliable.org.reliableinfrastructure.general;

public interface Constants {

    final public static String TAG = "[PracticalTest02]";

    final public static boolean DEBUG = true;

    final public static String WEB_SERVICE_CREATE_CLIENT = "http://bda9e792.ngrok.io/clients/saveClient";
    final public static String WEB_SERVICE_GET_ALERTS = "http://bda9e792.ngrok.io/alerts/getClientAlerts";
    final public static String WEB_SERVICE_SEND_ACK = "http://bda9e792.ngrok.io/alerts/sendACK";

    final public static String ALERT_MESSAGE = "infrastructure.reliable.org.reliableinfrastructure.alert_message";
    final public static String USER_PREFERENCES = "infrastructure.reliable.org.reliableinfrastructure.user_preferences";

    final public static String PHONE_NUMBER = "infrastructure.reliable.org.reliableinfrastructure.phone_number";
    final public static String SUBSCRIBED_CITIES = "infrastructure.reliable.org.reliableinfrastructure.subscribed_cities";

    final public static Integer NUMBER_OF_CHARACTERS_TO_BE_SHOWN = 30;
    final public static String DOTS = "...";

    final public static Integer ENTIRE_COUNTRY = 0;
    final public static String STARTING_SAVEOPTIONSTHREAD = "Starting SaveOptionsThread";
    final public static String DEFAULT_STRING = "";
    final public static String COMMA = ",";

    final public static String UPDATE_ALERTS_MESSAGE = "Actualizarea alertelor s-a produs";
    final public static String SUCCESSFUL_OPTIONS_SAVE = "Salvarea s-a produs cu succes";
    final public static String UNSUCCESSFUL_OPTIONS_SAVE = "Salvarea nu s-a putut realiza";

}
