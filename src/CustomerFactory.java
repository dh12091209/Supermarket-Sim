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
    private long totalShopTime;
    private int totalFinishCustomer = 0;
    private int totalCustomer = 0;
    private ArrayList<Cashiers> running = new ArrayList<Cashiers>();
    private long avgProcessTime=0;
    public static long avgWaitTime = 0;
    private long downTime;
    private int gotCustomer=0;


    //  CustomerFactory(5000,50,customers,100000,3,3000);
    public CustomerFactory(long timeSlice, long chance,ArrayList<Customer> customers,long runTime, int numCashiers, long waitCashier){
        this.timeSlice= timeSlice;
        this.chance = chance;
        this.customers = customers;
        this.runTime = runTime;
        this.numCashiers = numCashiers;
        this.waitCashier = waitCashier;
        // for(int i =0; i<numCashiers;i++){
        //     Cashiers cc = new Cashiers(waitCashier,i);
        //     //cashiers.add(new Cashiers(waitCashier,i));
        //     cashiers.add(cc);
        //     //cc.start();
        // }

        for(int i =0; i<numCashiers;i++){
            Cashiers cc = new Cashiers(customerCheck, waitCashier,i);
            //cashiers.add(new Cashiers(waitCashier,i));
            cashiers.add(cc);
            cc.start();
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

            // ArrayList<Customer> customers
            for(int i =0; i<customers.size(); i++){
                if(!customers.get(i).isAlive()){
//                    System.out.println(customers.get(i).toString() + " is moved to Queue");
                    customers.get(i).startWait();
                    customerCheck.add(customers.get(i));
                    totalShopTime += customers.get(i).getShopTime();
                    totalFinishCustomer++;
                    avgProcessTime += customers.get(i).getProcessTime();
                    customers.remove(i);
                }
            }
            // Queue<Customer> customerCheck
        //     for(int j =0; j<customerCheck.size(); j++){
        //         System.out.println("Assign customer to cashier ");
        //         //ArrayList<Cashiers> cashiers
        //         for(int x = 0; x<cashiers.size(); x++){
        //            if(!cashiers.get(x).isAlive()){
        //                 //System.out.println(cashiers.get(x).toString() + " is available");
        //                 // try {
        //                 //     cashiers.get(x).join();
        //                 // } catch(InterruptedException ie){}
        //                 Customer nxtCustomer = customerCheck.poll();
        //                 if (nxtCustomer != null) { 
        //                     cashiers.get(x).setCustomer(nxtCustomer);
        //                     cashiers.get(x).start();  // <- call twice causes IllegalThreadStateException
        //                     //cashiers.get(x).run();
        //                     System.out.println(cashiers.get(x).toString() + " will take " + nxtCustomer.toString());
        //                 }


        //            }
        //        }
        //    }
        }
        
        // In order to stop Cashier thread
        for(Cashiers cashier : cashiers){
            cashier.setJobDone(true);
        }
        for(Cashiers cashier : cashiers){
            downTime += cashier.getDownTime();
            gotCustomer += cashier.getGotCustomer();
        }
        System.out.println("Total customers: "+totalCustomer);//Total customers
        System.out.println("Total number of Cashiers used: "+numCashiers);//Total number of Cashiers used
        System.out.println("Average shop time per customer: "+totalShopTime/totalFinishCustomer);//Average shop time per customer
        System.out.println("Average process time per customer: "+avgProcessTime/totalFinishCustomer);//Average process time per customer
        System.out.println("Average wait time in the Queue per customer: "+avgWaitTime/totalFinishCustomer);//Average wait time in the Queue per customer
        System.out.println("Average down-time per Cashier: "+downTime/gotCustomer);//Average down-time per Cashier

    }
}
