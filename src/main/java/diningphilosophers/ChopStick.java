package diningphilosophers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChopStick {
    private final Lock verrouLock = new ReentrantLock();
    private final Condition condition = verrouLock.newCondition();

    private static int stickCount = 0;
    private boolean iAmFree = true;
    private final int myNumber;
    public ChopStick() {
        myNumber = ++stickCount;
    }

    public boolean tryTake(int delay) throws InterruptedException {
            verrouLock.lock();
       try { while (!iAmFree) {
            condition.await();
                return false; // Echec
    }
        // assert iAmFree;
        iAmFree = false;
        return true; // Succès
    }
    finally {verrouLock.unlock();}}

        public void release() {
          verrouLock.lock();
          try {
          iAmFree = true;
          notifyAll();
          condition.signalAll();
          System.out.println("Stick " + myNumber + " Released");
      }
      finally {
              verrouLock.unlock();
          }
        }
  

    @Override
    public String toString() {
        return "Stick#" + myNumber;
    }
}
