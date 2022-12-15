import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CustomerFactory extends Thread{

    private long timeSlice;
    private long chance;
    private long nextAttempt;
    private ArrayList<Customer> customers;
    private ConcurrentLinkedQueue<Customer> customerCheck = new ConcurrentLinkedQueue<Customer>();
    private ArrayList<Cashiers> cashiers = new ArrayList<Cashiers>();
    private long runTime;
    private int numCashiers;
    private long waitCashier;
    private int totalCustomer = 0;
    private ArrayList<Cashiers> running = new ArrayList<Cashiers>();


    public CustomerFactory(long timeSlice, long chance,ArrayList<Customer> customers,long runTime, int numCashiers, long waitCashier){
        this.timeSlice= timeSlice;
        this.chance = chance;
        this.customers = customers;
        this.runTime = runTime;
        this.numCashiers = numCashiers;
        this.waitCashier = waitCashier;
        for(int i =0; i<numCashiers;i++){
            cashiers.add(new Cashiers(waitCashier,i));
        }
        nextAttempt = System.currentTimeMillis() + this.timeSlice;
    }

    @Override
    public void run(){
        long start = System.currentTimeMillis();
        System.out.println();
        while( System.currentTimeMillis() - start < runTime){
            if(nextAttempt<System.currentTimeMillis()){
                long randNum = (long)(1+Math.random() * 100);
                if(randNum <=chance){
                    System.out.println("make customer");
                    Customer c = new Customer();
                    customers.add(c);
                    c.start();
                    totalCustomer++;
                }else{
                    System.out.println("did not make customer");
                }
                nextAttempt = System.currentTimeMillis() + timeSlice;
            }
            for(int i =0; i<customers.size(); i++){
                if(!customers.get(i).isAlive()){
                    customerCheck.add(customers.get(i));
                    customers.remove(i);
                }
            }
            
//            for(int j =0; j<customerCheck.size(); j++){
//                for(int x = 0; x<cashiers.size(); x++){
//                    if(!cashiers.get(x).isAlive()){
//                        cashiers.get(x).setCustomer(customerCheck.poll());
//                        cashiers.get(x).start();
//                    }
//                }
//            }
        }
        
    }
}
