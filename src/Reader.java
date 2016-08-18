import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Leonid on 17.08.2016.
 */
public class Reader implements Runnable{
    private BufferedReader bufferedReader;
    private Transaction transaction;
    boolean ready = false;

    public Reader(){

    }
    public Reader(Transaction t){
        transaction = t;
    }
    public void setPath(String path)throws FileNotFoundException{
        bufferedReader = new BufferedReader(new FileReader(path));
    }
    public void close(){
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(";");
                synchronized (transaction) {
                    while (transaction.getNum() != -1) {
                        try {
                            transaction.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    //transaction = new Transaction();
                    transaction.setNum(Integer.parseInt(parts[0]));
                    transaction.setDate(parts[1]);
                    transaction.setFrom(parts[2]);
                    transaction.setTo(parts[3]);
                    transaction.setSum(Integer.parseInt(parts[4]));
                    transaction.notify();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
