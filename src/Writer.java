/**
 * Created by Leonid on 17.08.2016.
 */
public class Writer implements Runnable{
    private Transaction transaction;
    Writer(Transaction t, Thread p){
        transaction = t;
        producer = p;
    }
    public void printTransaction(Transaction t){
        t.print();
    }
    Thread producer;

    @Override
    public void run() {
        while (producer.isAlive()) {
            synchronized (transaction) {
                while (transaction.getNum() == -1) {
                    try {
                        transaction.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                printTransaction(transaction);
                transaction.setNum(-1);
                transaction.notify();
            }
        }
    }
}
