/** kbestCounter
*   Author: Keun Woo Song (ks3651)
*
**/
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.io.*;

public class KBestCounter<T extends Comparable<? super T>> implements KBest<T>{

    private int kSize;
    private PriorityQueue<T> queue;

    //Constructor that recieves int k, which is the amount of largest elements while
    //are requesting to return
    public KBestCounter(int k){
        this.kSize=k;
        queue = new PriorityQueue<T>(k);
    }

    public void count(T x) {
        if (queue.size() < kSize) {
            queue.offer(x);
        }   else{
                if (queue.peek().compareTo(x) < 0) {
                    //that means the element is smaller so replace it with x
                    queue.poll();
                    queue.offer(x);
                }
            }
    }

    public List<T> kbest(){
        int size = queue.size();
        List<T> best = new ArrayList<>();
        PriorityQueue<T> newQueue = new PriorityQueue<T>(queue);
        for (int i = 0; i < size; i++) {
            T next = queue.remove();
            best.add(next);
        }
        queue = newQueue;
        return best;
    }
    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        
        KBestCounter a = new KBestCounter(5);
        int[] b = {5,23,7,12,54,95,22,21,75};
        
    try{
        for(int i:b){
            a.count(i);
        }
        System.out.println(a.kbest());
        System.out.println(a.kbest());
        System.out.println(a.kbest());

    }catch (Exception e) {
        throw new RuntimeException(e);
        }

    }
    
}