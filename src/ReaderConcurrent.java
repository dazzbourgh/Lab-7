import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Exchanger;

/**
 * Created by Leonid on 19.08.2016.
 */
public class ReaderConcurrent implements Runnable {
    private BufferedReader bufferedReader;
    private Transaction t;
    private Exchanger<Transaction> exchanger;

    public void setExchanger(Exchanger<Transaction> exchanger){
        this.exchanger = exchanger;
    }

    public ReaderConcurrent(String path, Transaction t) throws FileNotFoundException{
        bufferedReader = new BufferedReader(new FileReader(path));
        this.t = t;
        exchanger = new Exchanger<>();
    }

    private String[] parseString() throws IOException, NullPointerException {
        String[] returnValue = bufferedReader.readLine().split(";");
        return returnValue;
    }

    private void formTransaction(String[] strArr){
        t.setNum(Integer.parseInt(strArr[0]));
        t.setDate(strArr[1]);
        t.setFrom(strArr[2]);
        t.setTo(strArr[3]);
        t.setSum(Integer.parseInt(strArr[4]));
    }

    @Override
    public void run() {
        try {
            while(true) {
                t = new Transaction();
                formTransaction(parseString());
                t = exchanger.exchange(t);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("---END OF FILE---");
            return;
        }
    }


}
