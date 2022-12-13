import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Customer> customers = new ArrayList<>();
        int numCashiers = 3;


        CustomerFactory customerFactory = new CustomerFactory(5000,50,customers,100000,3,3000);
        customerFactory.start();

    }
}
