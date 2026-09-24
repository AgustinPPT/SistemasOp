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
        this.semaforo = new Semaphore(totalLugares);//Si es FIFO
        for (int i = 1; i <= totalLugares; i++) {
            lugaresDisponibles.add(i);
        }
    }

    public synchronized void registrarLlegada(Estacionamiento carro){
        esperando.add(carro);
        System.out.println("LLego el carro.%s", carro.getNombre());
            if(semaforo.availablePermits() == 0){
                System.out.println("Esta esperando un lugar disponible En espera: %s%n", carro.getNombre(),nombres(esperando));
            }
    }

    
}
