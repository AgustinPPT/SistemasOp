public class Estacionamiento extends Thread {
    private static Object Carros_ = new Object();

    private String nombre;
    private long tiempoMinMs;
    private long tiempoMaxMs;
    private long tiempoTotalMs = 0;

    public Estacionamiento(String nombre, long tiempoMinMs, long tiempoMaxMs) {
        this.nombre = nombre;
        this.tiempoMinMs = tiempoMinMs;
        this.tiempoMaxMs = tiempoMaxMs;
    }

    public long generarTiempoAleatorio() {
        long rango = tiempoMaxMs - tiempoMinMs;
        return tiempoMinMs + (long) (Math.random() * rango);
    }

    //getters
    public String getNombre() {
        return nombre;
    }
    public long getTiempoTotalMs() {
        return tiempoTotalMs;
    }
    @Override 
    public void run() {
        long tiempoEstacionamientoMs = generarTiempoAleatorio();

        try {
            // Simula el tiempo que tarda en estacionarse (aleatorio)
            Thread.sleep(tiempoEstacionamientoMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        // Al completar el sleep, se considera que el carro ha terminado de estacionarse
        tiempoTotalMs += tiempoEstacionamientoMs;

        synchronized (Carros_) {
            System.out.printf("Carro: %s completo su estacionamiento en %d ms%n",
                    nombre, tiempoEstacionamientoMs);
        }
    }
}