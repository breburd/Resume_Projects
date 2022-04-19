package threadpool;

import java.util.LinkedList;

/**
 * This is a Thread Pool that reuses WorkerThreads during our client
 * and server communication. This version uses synchronized methods.
 *
 * @author Bre Burd
 */
public class ThreadPool {

    final public int SIZE = 3;
    public WorkerThread[] pool;
    public WorkQueue workQueue;

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
    public static class WorkQueue {
        private LinkedList<Connection> connections;

        public WorkQueue(){
            connections = new LinkedList<>();
        }

        /**
         * Adds a Connection to the queue
         */
        public synchronized void add(Connection connection) {
            connections.add(connection);
            notifyAll();
        }

        /**
         * Gets the connection at the beginning of the list and
         * removes it from the waiting queue.
         */
        public synchronized Connection get() {
            try {
                while (connections.isEmpty())
                    wait();
            } catch (InterruptedException e) {
                // not handled
            }
            // Also removes the first item because it is no longer
            // in the queue
            Connection temp = connections.get(0);
            connections.remove(0);
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
