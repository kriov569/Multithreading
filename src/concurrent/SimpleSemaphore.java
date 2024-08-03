package concurrent;

import java.util.concurrent.Semaphore;

// Mutual Exclusion
public class SimpleSemaphore {
    public static void main(String[] args) {
        final Semaphore smp = new Semaphore(4);
        Runnable limitedCall =
                new Runnable() {
                    int count = 0;
                    public void run() {
                        int time = 3 + (int) (Math.random() * 4.0);
                        int num = count++;
                        try {
                            smp.acquire(); // в этой секции поток захватывает семафор,
                            // если в нем есть свободное место, если мест нет,
                            // ждет пока не появится место
                            System.out.println("Поток #" + num + " начинает выполнять очень долгое действие "
                            + time + " сек.");
                            Thread.sleep(time * 10); // делает вид, что поток выполняет важную задачу
                            System.out.println("Поток #" + num + " завершил работу!");
                            smp.release(); // освобождает семафор, чтобы другой поток мог его занять
                        } catch (InterruptedException intEx) {
                            intEx.printStackTrace();
                        }
                    }
                };
        for (int i = 0; i < 10; i++)
            new Thread(limitedCall).start(); // пытается запустить одновременно 10 потоков
    }
}
