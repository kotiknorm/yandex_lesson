package background.mblz.yandex.com.yandexlesson.handler;

public interface CriticalSectionsHandler {

    void startSection(int id);

    void stopSection(int id);

    void stopSections();

    void postLowPriorityTask(Task task);

    void postLowPriorityTaskDelayed(Task task, int delay);

    void removeLowPriorityTask(Task task);

    void removeLowPriorityTasks();

}
