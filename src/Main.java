import java.io.FileNotFoundException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Leonid on 17.08.2016.
 */
public class Main {
    public static void main(String[] args){
        //new Main().executeOld();
        new Main().executeConcurrent();
    }
    private void executeOld(){
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
    private void executeConcurrent(){
        ExecutorService executorServiceR = Executors.newSingleThreadExecutor();
        ExecutorService executorServiceW = Executors.newSingleThreadExecutor();
        CyclicBarrier barrier = new CyclicBarrier(2);
        Transaction t = new Transaction();
        Exchanger<Transaction> exchanger = new Exchanger<>();
        ReaderConcurrent r;
        try {
            r = new ReaderConcurrent("src\\Transactions.txt", t);
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return;
        }
        WriterConcurrent w = new WriterConcurrent(t);
        r.setExchanger(exchanger);
        w.setExchanger(exchanger);
        w.setProducer(executorServiceR);
        r.setBarrier(barrier);
        w.setBarrier(barrier);
        executorServiceR.execute(r);
        executorServiceW.execute(w);
    }
}

