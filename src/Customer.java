public class Customer extends Thread {

    private static int nextID = 0;
    private int id;
    private long shopTime;      //how long in ms the customer shops for
    private long checkoutTime;  //how long in ms it take the customer to check out
    private long enterTime;     // the time in ms the customer enters the store
    private long leaveTime;     //the time in ms the customer leaves the store
    private long minShopTime = 3000;   //the min time in ms the customer shops
    private long maxShopTime = 20000;   //the max time in ms the customer shops

    private long startTime;
    private long endTime;
    private long startWait;
    private long endWait;
    public Customer(){
        id = nextID;
        nextID++;
        enterTime = System.currentTimeMillis();
        shopTime = (long)(minShopTime + Math.random() * (maxShopTime-minShopTime));
        checkoutTime = 2000;
    }

    @Override
    public String toString(){
        return "Customer " + id;
    }

    @Override
    public void run(){
        startTime = System.currentTimeMillis();
        leaveTime = enterTime+shopTime;
       while(leaveTime > System.currentTimeMillis()){
       }
       endTime = System.currentTimeMillis();
       System.out.println(this.toString() + " is done shopping with shop time " + shopTime);
    }

    public long getShopTime() {
        return shopTime;
    }

    public long getLeaveTime() {
        return leaveTime;
    }

    @Override
    public long getId() {
        return id;
    }
    public long getProcessTime(){
        return endTime - startTime;
    }
    public void startWait(){
        startWait = System.currentTimeMillis();
    }
    public void endWait(){
        endWait = System.currentTimeMillis();
        CustomerFactory.avgWaitTime += endWait-startWait;
    }

}
