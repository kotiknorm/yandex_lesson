package background.mblz.yandex.com.yandexlesson.handler;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class StubCriticalSectionsHandler implements CriticalSectionsHandler {

    private ConcurrentHashMap<Integer, Boolean> activeCriticalSections = new ConcurrentHashMap<>();
    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    void work() {
        final Handler handler = new Handler(Looper.getMainLooper());
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                    //synchronized (this)
                    {
                        if (taskQueue.size() > 0 && activeCriticalSections.size() == 0) {
                            final Task task = taskQueue.take();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    task.run();
                                }
                            });
                        }
                    }
                    Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void startSection(int id) {
        activeCriticalSections.put(id, true);
    }

    @Override
    public void stopSection(int id) {
        activeCriticalSections.remove(id);
    }

    @Override
    public void stopSections() {
        activeCriticalSections.clear();
    }

    private java.util.concurrent.BlockingQueue<Task> taskQueue = new ArrayBlockingQueue<Task>(100, true);
    @Override
    public void postLowPriorityTask(Task task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void postLowPriorityTaskDelayed(Task task, int delay) {

    }

    @Override
    public void removeLowPriorityTask(Task task) {

    }

    @Override
    public void removeLowPriorityTasks() {

    }
}
