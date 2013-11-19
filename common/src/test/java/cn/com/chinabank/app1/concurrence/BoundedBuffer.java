package cn.com.chinabank.app1.concurrence;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 11/18/13
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class BoundedBuffer {

    private final Logger logger = Logger.getLogger(getClass());
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        Thread.sleep(2);
        logger.info("putting method started");
        lock.lock();
        logger.info("putting method started,locking");
        int i = 1;
        try {
            while (count == items.length) {
                logger.info("putting " + i++);
                notFull.await();
            }
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal();
        } finally {
            logger.info("putting method ended,before unlock");
            lock.unlock();
            Thread.sleep(500);
            logger.info("putting method ended");
        }
    }

    public Object take() throws InterruptedException {
        logger.info("taking method started");
        lock.lock();
        logger.info("taking method started,locking");
        int i = 1;
        try {
            while (count == 0) {
                logger.info("taking " + i++);
                notEmpty.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            logger.info("taking method ended,before unlock");
            lock.unlock();
            logger.info("taking method ended");
        }
    }

    @Test
    public void test() {
        final BoundedBuffer bound = new BoundedBuffer();
        Thread take = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("to take");
                    Object obj = bound.take();
                    logger.info("took " + obj);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        Thread put = new Thread() {
            public void run() {
                try {
                    logger.info("to put");
                    bound.put("key");
                    logger.info("put");
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        };
        take.start();
        put.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        logger.info("main thread over");
    }

}
