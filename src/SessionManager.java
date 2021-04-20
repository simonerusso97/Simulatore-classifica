import java.util.HashMap;

public class SessionManager {

    private static SessionManager instance;

    private  HashMap<String, Object> SESSION = new HashMap<String, Object>();


    public static SessionManager getInstance() {



        if (instance == null)
            instance = new SessionManager();
            return instance;
        }


    public  HashMap<String, Object> getSESSION() {
        return SESSION;
    }

}

