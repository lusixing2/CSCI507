// implement round robin scheduler using circular queue
import java.util.concurrent.ThreadLocalRandom;

public class ProcessScheduler {
    int queue_size;
    int front, rear;
    int items[];
    
    public ProcessScheduler(int size){
       queue_size = size;
       front = -1;
       rear = -1;
       items = new int[queue_size];
    }
  
    // Check if the queue is full
    boolean isFull() {
      if (front == 0 && rear == queue_size - 1) {
        return true;
      }
      if (front == rear + 1) {
        return true;
      }
      return false;
    }
  
    // Check if the queue is empty
    boolean isEmpty() {
      if (front == -1)
        return true;
      else
        return false;
    }
  
    // enqueue
    int enQueue(int element) {
      if (isFull()) {
        // System.out.println("Queue is full");
        return -1;
      } else {
        if (front == -1)
          front = 0;
        rear = (rear + 1) % queue_size;
        items[rear] = element;
        return 1;
      }
    }
  
    // dequeue
    int deQueue() {
      int element;
      if (isEmpty()) {
        return (-1);
      } else {
        element = items[front];
        if (front == rear) {
          front = -1;
          rear = -1;
        } 
        else {
          front = (front + 1) % queue_size;
        }
        return (element);
      }
    }
  

    public static void main(String[] args) {
        int process_numbers = 10;
        int scheduler_size = 5;
        int time_slot = 1;

        long startTime = System.nanoTime();
        long beforeUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();

        int[] process = new int[process_numbers];
        ProcessScheduler scheduler = new ProcessScheduler(scheduler_size);

        //randomly generate time for each process
        for (int i=0;i<process_numbers;i++){
            process[i] = ThreadLocalRandom.current().nextInt(10, 100);
        }
        //initiate the scheduler
        for (int i=0;i<scheduler_size;i++){
          scheduler.enQueue(i);
        }

        while(!scheduler.isEmpty()){
          int current_process = scheduler.deQueue();
          process[current_process] -= time_slot;
          
          //if the process is not finished, put it back to the queue
          if(process[current_process]>0){
            scheduler.enQueue(current_process);
          }else{
            System.out.printf("Process %d is finished\n",current_process);
          }

        }
        System.out.println("All processes are done");

        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        long actualMemUsed=afterUsedMem-beforeUsedMem;
        
        long endTime = System.nanoTime();
        float totalTime = endTime - startTime;
        System.out.format("time: %5.2f ms, memory used: %d bytes\n",  totalTime/1000000, actualMemUsed);
    }

    
}
