import java.util.concurrent.ConcurrentLinkedQueue;

public class Cashiers extends Thread{

    private Customer customer;
    private long waitTime;
    private int cashierID;
    private boolean jobDone = false;

    private ConcurrentLinkedQueue<Customer> customerCheck;


    public Cashiers(long waitTime, int cashierID){
        this.waitTime = waitTime;
        this.cashierID = cashierID;
    }

    public Cashiers(ConcurrentLinkedQueue<Customer> queue, long waitTime, int cashierID){
        this.customerCheck = queue;
        this.waitTime = waitTime;
        this.cashierID = cashierID;
    }


    @Override
    public void run(){

        while (!jobDone) {
            Customer customer;
            while ((customer = customerCheck.poll()) != null) {
                while(customer.getLeaveTime() + waitTime > System.currentTimeMillis()){
                }
                System.out.println("**Cashier " + cashierID + " finished customer " + customer.getId() + "**");
            }
        }
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setJobDone(boolean flag) {
        this.jobDone = true;
    }

    public long getWaitTime() {
        return waitTime;
    }

    @Override
    public String toString(){
        return "Cashier " + cashierID;
    }
}
