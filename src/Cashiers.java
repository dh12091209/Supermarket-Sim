public class Cashiers extends Thread{

    private Customer customer;
    private long waitTime;
    private int cashierID;
    public Cashiers(long waitTime, int cashierID){
        this.waitTime = waitTime;
        this.cashierID = cashierID;
    }
    @Override
    public void run(){
        while(customer.getLeaveTime() + waitTime > System.currentTimeMillis()){
        }
        System.out.println("Cashier " + cashierID + " finished customer " + customer.getId());
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
