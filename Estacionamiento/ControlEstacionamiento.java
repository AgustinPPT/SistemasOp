import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ControlEstacionamiento {
    private Semaphore semaforo;
    private Deque<Integer> lugaresDisponibles = new ArrayDeque<>();
    private List<Estacionamiento> estacionados = new ArrayList<>();
    private List<Estacionamiento> esperando = new ArrayList<>();

    public ControlEstacionamiento(int totalLugares) {
        this.semaforo = new Semaphore(totalLugares);
        for (int i = 1; i <= totalLugares; i++) {
            lugaresDisponibles.add(i);
        }
    }

    public synchronized void registrarLlegada(Estacionamiento carro) {
        esperando.add(carro);
        System.out.printf("Llego el carro: %s%n", carro.getNombre());
        if (semaforo.availablePermits() == 0) {
            System.out.printf("%s esta esperando un lugar disponible. En espera: %s%n", carro.getNombre(), nombres(esperando));
        }
    }

    public void entrar(Estacionamiento carro) throws InterruptedException {
        semaforo.acquire();
        synchronized (this) {
            esperando.remove(carro);
            int lugar = lugaresDisponibles.pollFirst();
            carro.setLugar(lugar);
            carro.sethoraEntrada(System.currentTimeMillis());
            estacionados.add(carro);

            double minutos = (double) carro.gettiempoEstacionado() / 1000.0;
            System.out.printf("Se estaciono el carro: %s en el lugar: %d por %.2f minutos%n", carro.getNombre(), lugar, minutos);
            imprimirEstado();
        }
    }

    public void salir(Estacionamiento carro) {
        synchronized (this) {
            long ahora = System.currentTimeMillis();
            double minutosReales = (double) (ahora - carro.gethoraEntrada()) / 1000.0;
            estacionados.remove(carro);
            lugaresDisponibles.add(carro.getLugar());
            System.out.printf("Salio el carro: %s del lugar: %d despues de %.2f minutos%n", carro.getNombre(), carro.getLugar(), minutosReales);

            if (!estacionados.isEmpty()) {
                System.out.printf("Tiempo restante de los carros estacionados: %s%n", nombres(estacionados));
                for (Estacionamiento c : estacionados) {
                    double transcurrido = (ahora - c.gethoraEntrada()) / 1000.0;
                    double restante = Math.max(0, c.gettiempoEstacionado() / 1000.0 - transcurrido);
                    System.out.printf("- %s (lugar %d): le quedan %.1f minutos%n", c.getNombre(), c.getLugar(), restante);
                }
            } else {
                System.out.println("No queda ningun carro estacionado");
            }

            if (!esperando.isEmpty()) {
                System.out.printf("Carros que se encuentran en espera: %s%n", nombres(esperando));
            }

            semaforo.release();
        }
    }

    private void imprimirEstado() {
        System.out.println("Estacionados ahora: " + nombres(estacionados));
        System.out.println("Esperando ahora: " + nombres(esperando));
    }

    private String nombres(List<Estacionamiento> lista) {
        if (lista.isEmpty()) return "ninguno";
        StringBuilder sb = new StringBuilder();
        for (Estacionamiento c : lista) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(c.getNombre());
        }
        return sb.toString();
    }
}