/**
 * Created by Leonid on 17.08.2016.
 */
public class Transaction {
    private int sum;
    private String date;
    private String from;
    private String to;
    private int num;

    public Transaction(){
        sum = 0;
        date = "";
        from = "";
        to = "";
        num = -1;
    }

    public void print(){

        System.out.println("Order number: #" + num);
        System.out.println("  Date: " + date);
        System.out.println("  From: " + from);
        System.out.println("  To: " + to);
        System.out.println("  Sum: " + sum);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
