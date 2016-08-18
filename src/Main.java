import java.io.FileNotFoundException;

/**
 * Created by Leonid on 17.08.2016.
 */
public class Main {
    public static void main(String[] args){
        Transaction t = new Transaction();
        Reader r = new Reader(t);
        try {
            r.setPath("src\\Transactions.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Thread producer = new Thread(r);
        producer.start();
        Writer w = new Writer(t, producer);
        new Thread(w).start();
    }
}

