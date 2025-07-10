package threadpool2;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This is a Thread Pool that reuses WorkerThreads during our client
 * and server communication. This version uses locks.
 *
 * @author Bre Burd
 */
public class ThreadPool {

    final public int SIZE = 3;
    public WorkerThread[] pool;
    public WorkQueue workQueue;

    private final Lock lock  = new ReentrantLock();
    private final Condition listAccess = lock.newCondition();

    /**
     * Creates an array of threads and instantiates the work queue.
     */
    public ThreadPool() {
        pool = new WorkerThread[SIZE];
        workQueue = new WorkQueue();

        // fills the array with WorkerThread objects and starts them
        for(int i = 0; i < SIZE; i++) {
            pool[i] = new WorkerThread();
            pool[i].start();
        }
    }

    /**
     * Adds a connection to the queue
     */
    public void add(Connection connection) {
        workQueue.add(connection);
    }

    /**
     * Stores a linked list of Connection objects.
     */
    public class WorkQueue {
        private LinkedList<Connection> connections;

        public WorkQueue(){
            connections = new LinkedList<>();
        }

        /**
         * Adds a Connection to the queue
         */
        public void add(Connection connection) {
            lock.lock();
            try{
                connections.add(connection);
                listAccess.signalAll();
            } finally {
                lock.unlock();
            }
        }

        /**
         * Gets the connection at the beginning of the list and
         * removes it from the waiting queue.
         */
        public Connection get() {
            lock.lock();
            Connection temp = null;     //This should never be returned, so this should work
            try {
                while (connections.isEmpty())
                    listAccess.await();
                // Also removes the first item because it is no longer
                // in the queue
                temp = connections.get(0);
                connections.remove(0);
            } catch (InterruptedException e) {
                // not handled
            } finally {
                lock.unlock();
            }
            return temp;
        }
    }

    /**
     * Gets a connection from the que and runs it.
     */
    public class WorkerThread extends Thread {

        @Override
        public void run() {
            while(true)
                workQueue.get().run();
        }
    }
}
