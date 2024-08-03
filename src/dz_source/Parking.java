package dz_source;

import java.util.concurrent.Semaphore;

public class Parking {
    private static final boolean[] PARKING_PLACES = new boolean[5];
    // определите ваш семафор
    private static Semaphore SEMAPHORE = new Semaphore(5);

    public static void main(String[] args) throws InterruptedException {
        // запустите процесс парковки
        for (int i = 0; i < 7; i++) {
            new Thread(new Car(i)).start();
            Thread.sleep(500); // добавьте паузу для удобства просмотра
        }
    }

    public static class Car implements Runnable {
        private int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public void run() {
            // здесь ваше решение
            System.out.printf("Car #%d coming to parking place\n", carNumber);
            try {
                SEMAPHORE.acquire(); // блокировка семафора
                int parkingNumber = 0;
                synchronized (PARKING_PLACES) {
                    for (int i = 0; i < 5; i++) {
                        if (!PARKING_PLACES[i]) {
                            PARKING_PLACES[i] = true;
                            parkingNumber = i;
                            System.out.printf("Car #%d have parking on place %d.\n",
                                    carNumber, i);
                            break;
                        }
                    }
                }
                Thread.sleep(5000); // машина постояла на парковке и уехала
                synchronized (PARKING_PLACES) {
                    PARKING_PLACES[parkingNumber] = false;
                }
                SEMAPHORE.release(); // разблокировка семафора

                System.out.printf("Car #%d have left the parking place\n", carNumber);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

