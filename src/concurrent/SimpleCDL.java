package concurrent;

import java.util.concurrent.CountDownLatch;

public class SimpleCDL {
    public static void main(String[] args) {
        final int THREAD_COUNT = 4; // задает кол-во потоков
        final CountDownLatch cdl = new CountDownLatch(THREAD_COUNT); // задаем значение счетчика
        System.out.println("START");
        for (int i = 0; i < 10; i++) {
            final int w = i;
            new Thread(() -> {
                try {
                    Thread.sleep(200 * w + (int)(1500 * Math.random())); // считает что выполнение задачи занимает от 200 до 350 мс
                    cdl.countDown(); // как только задача выполнена, уменьшает счетчик
                    System.out.println("THREAD #" + w + " - Ready");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        try {
            cdl.await(); // ждет пока не сбросится в ноль, пока это не произойдет, будет стоять на этой строчке
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВЫПОЛНЯЕТСЯ только для потоков в CountDownLatch"); // как только все потоки выполнили задачу, печатается эта строчка
    }
}
