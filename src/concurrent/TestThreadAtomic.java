package concurrent;

/*
 * Класс java.util.concurrent.atomic.AtomicInteger представляет операции с базовым значением int,
 * которые могут быть прочитаны и записаны атомарно.
 * а также содержит расширенные атомарные операции.
 * AtomicInteger поддерживает атомарные операции с базовой переменной int.
 * у него есть методы get и set, которые работают как чтение и запись по переменным.
 * То есть набор имеет отношение "происходит да" с любым последующим получением той же переменной.
 * У атомарного метода compareAndSet также есть эти особенности согласованности памяти.
 */

import java.util.concurrent.atomic.AtomicInteger;

public class TestThreadAtomic {

    static class Counter {
        private AtomicInteger c = new AtomicInteger(0);

        public void increment() {c.getAndIncrement();}

        public int value() {return c.get();}
    }

    public static void main(final String[] arguments) throws InterruptedException {
        final Counter counter = new Counter();

        // 1000 threads
        for (int i = 0; i < 1000; i++) {

            new Thread(new Runnable() {
                public void run() {
                    counter.increment();
                }
            }).start();
        }
        Thread.sleep(6000);
        System.out.println("Final number (should be 1000): " + counter.value());
    }
}
