import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;

/**
 * Created by Leonid on 19.08.2016.
 */
public class WriterConcurrent implements Runnable{
    private Transaction transaction;
    private Exchanger<Transaction> exchanger;
    private ExecutorService producer;

    public void setProducer(ExecutorService p){
        producer = p;
    }
    WriterConcurrent(Transaction t){
        transaction = t;
    }
    public void setExchanger(Exchanger<Transaction> ex){
        this.exchanger = ex;
    }

    @Override
    public void run() {
        while(!producer.isShutdown()) {
            try {
                if(transaction.getNum() != -1)
                    transaction.print();
                transaction = exchanger.exchange(transaction);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}