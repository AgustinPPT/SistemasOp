import java.util.ArrayList;
import java.util.List;
public class MainEstacionamiento {
    public static void main(String[] args) throws InterruptedException {
        int lugaresDisponibles = 3;
        ControlEstacionamiento control = new ControlEstacionamiento(lugaresDisponibles);
        List<Estacionamiento> carros = new ArrayList<>();
        carros.add(new Estacionamiento("Carro 1", 1000, 5000, control));
        carros.add(new Estacionamiento("Carro 2", 1000, 5000, control));
        carros.add(new Estacionamiento("Carro 3", 1000, 5000, control));
        carros.add(new Estacionamiento("Carro 4", 1000, 5000, control));
        carros.add(new Estacionamiento("Carro 5", 1000, 5000, control));
        carros.add(new Estacionamiento("Carros6", 1000, 5000, control));

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