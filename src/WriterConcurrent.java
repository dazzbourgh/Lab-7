import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;

/**
 * Created by Leonid on 19.08.2016.
 */
public class WriterConcurrent implements Runnable{
    private Transaction transaction;
    private Exchanger<Transaction> exchanger;
    private ExecutorService producer;
    private CyclicBarrier barrier;

    public void setBarrier(CyclicBarrier b){
        barrier = b;
    }
    public void setProducer(ExecutorService p){
        producer = p;
    }
    WriterConcurrent(Transaction t){
        transaction = t;
    }
    public void setExchanger(Exchanger<Transaction> ex){
        this.exchanger = ex;
    }
    public void printTransaction(Transaction t){
        t.print();
    }
    @Override
    public void run() {
        while(!producer.isTerminated()) {
            try {
                if(transaction.getNum() != -1)
                    transaction.print();
                barrier.await();
                transaction = exchanger.exchange(transaction);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}