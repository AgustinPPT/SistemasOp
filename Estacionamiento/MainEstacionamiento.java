import java.util.ArrayList;
import java.util.List;
public class MainEstacionamiento {
    public static void main(String[] args) throws InterruptedException {
        int lugaresDisponibles = 3;
        ControlEstacionamiento control = new ControlEstacionamiento(lugaresDisponibles);
        List<Estacionamiento> carros = new ArrayList<>();
        //Creacion de los carros con sus tiempos de estacionamiento
        carros.add(new Estacionamiento("Impala", 3000, 10000, control));
        carros.add(new Estacionamiento("Toyota", 3000, 10000, control));
        carros.add(new Estacionamiento("Prius", 3000, 10000, control));
        carros.add(new Estacionamiento("Honda", 3000, 10000, control));
        carros.add(new Estacionamiento("Civic", 3000, 10000, control));
        carros.add(new Estacionamiento("Raptor", 3000, 10000, control));
        
        System.out.println("==Estacionamiento con " + lugaresDisponibles + " lugares disponibles==\n");
        for (Estacionamiento carro : carros) {
            carro.start();
        }
        for (Estacionamiento carro : carros) {
            carro.join();
        }
        System.out.println("==Todos los carros se han estacionado y salido del estacionamiento==");
    }
}