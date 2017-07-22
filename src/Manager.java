import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Manager {   //singleton


    private static Manager ourInstance = new Manager();
    private final List<Table> restaurantTables = new ArrayList<>(10);
    private int currentIndex = 0;

    private final Queue<Order> orderQueue = new ConcurrentLinkedDeque<>();
    private final Queue<Dishes> dishesQueue = new ConcurrentLinkedDeque<>();

    public synchronized static Manager getInstance(){
        return ourInstance;
    }


    private Manager(){   //creating 10 tables
        for (int i = 0; i < 10; i++) {
            restaurantTables.add(new Table());
        }
    }
    //officiant hodit po krugu
    public synchronized Table getNextTable(){
        Table table = restaurantTables.get(currentIndex);
        currentIndex = (currentIndex + 1) % 10;
        return table;
    }

    public Queue<Order> getOrderQueue() {
        return orderQueue;
    }

    public Queue<Dishes> getDishesQueue() {
        return dishesQueue;
    }
}
