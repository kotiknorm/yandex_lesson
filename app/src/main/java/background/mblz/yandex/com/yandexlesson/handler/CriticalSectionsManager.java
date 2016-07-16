package background.mblz.yandex.com.yandexlesson.handler;

public class CriticalSectionsManager {

    private static CriticalSectionsHandler sCriticalSectionsHandler;

    public static void init(CriticalSectionsHandler criticalSectionsHandler) {
        sCriticalSectionsHandler = criticalSectionsHandler;
    }

    public static CriticalSectionsHandler getHandler() {
        if (sCriticalSectionsHandler == null) {
            sCriticalSectionsHandler = new StubCriticalSectionsHandler();
        }
        return sCriticalSectionsHandler;
    }
}
