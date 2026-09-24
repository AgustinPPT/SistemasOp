import java.util.Random;
import java.util.concurrent.Semaphore;

public class Estacion {

    public static void main(String[] args) throws InterruptedException {
        int lugaresDisponibles = 3;
        int totalCarros = 6;

        // true = orden FIFO, así los carros se estacionan en el orden en que llegaron
        Semaphore lugares = new Semaphore(lugaresDisponibles, true);

        Thread[] carros = new Thread[totalCarros];

        System.out.println("=== Estacionamiento con " + lugaresDisponibles
                + " lugares disponibles ===\n");

        for (int i = 1; i <= totalCarros; i++) {
            carros[i - 1] = new Thread(new Carro(i, lugares));
            carros[i - 1].start();
            Thread.sleep(200); // los carros no llegan todos en el mismo instante exacto
        }

        for (Thread t : carros) {
            t.join();
        }

        System.out.println("\n✅ Todos los carros ya se estacionaron y se fueron.");
    }
}

class Carro implements Runnable {
    private final int id;
    private final Semaphore lugares;
    private static final Random random = new Random();

    public Carro(int id, Semaphore lugares) {
        this.id = id;
        this.lugares = lugares;
    }

    @Override
    public void run() {
        long tiempoLlegada = System.currentTimeMillis();
        log("🚗 Carro " + id + " llegó al estacionamiento.");

        try {
            if (lugares.availablePermits() == 0) {
                log("⏳ Carro " + id + " está ESPERANDO un lugar libre...");
            }

            // Se bloquea aquí si no hay lugar; cuando otro carro sale, se libera y entra
            lugares.acquire();

            long tiempoEspera = System.currentTimeMillis() - tiempoLlegada;
            int tiempoEstacionado = 2000 + random.nextInt(4000); // entre 2 y 6 segundos

            log("🅿️  Carro " + id + " se ESTACIONÓ (esperó " + tiempoEspera + " ms). "
                    + "Va a estar " + tiempoEstacionado + " ms. Lugares libres: "
                    + lugares.availablePermits());

            Thread.sleep(tiempoEstacionado);

            log("🚙 Carro " + id + " SALIÓ del estacionamiento tras "
                    + tiempoEstacionado + " ms estacionado.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lugares.release(); // libera el lugar: el contador de disponibles sube en 1
            log("🔓 Lugar liberado por el Carro " + id + ". Lugares libres ahora: "
                    + lugares.availablePermits());
        }
    }

    // synchronized para que los mensajes de distintos hilos no se mezclen en consola
    private static synchronized void log(String mensaje) {
        System.out.println(mensaje);
    }
}