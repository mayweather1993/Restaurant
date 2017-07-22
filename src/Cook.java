public class Cook implements Runnable {
    public boolean continueWorking = true;



    @Override
    public void run() {
        boolean needToWait;
        while (continueWorking || needToCookOrders()) {
            try {
                synchronized (this) {
                    needToWait = !needToCookOrders();
                    if (!needToWait) {
                        cooking();
                    }
                }
                if (continueWorking && needToWait) {
                    System.out.println("Повар отдыхает");
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
            }
        }
    }


    private boolean needToCookOrders(){
        return !Manager.getInstance().getOrderQueue().isEmpty();
    }


    private void cooking() throws InterruptedException {
        Manager manager = Manager.getInstance();
        Order order = manager.getOrderQueue().poll();  //povar beret zakaz
        System.out.println(String.format("Заказ будет готовиться %d мс для стола #%d",order.getTime() , order.getTableNumber()));
        Thread.sleep(order.getTime()); //gotovim bludo
        Dishes dishes = new Dishes(order.getTableNumber());  //bludo gotovo
        manager.getDishesQueue().add(dishes);
        System.out.println(String.format("Заказ для стола #%d готов" , dishes.getTableNumber()));
    }
}
