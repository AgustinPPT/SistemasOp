import java.util.ArrayList;
import java.util.List;
public class MainEstacionamiento {
    public static void main(String[] args) {
        List<Estacionamiento> carros = new ArrayList<>();
        carros.add(new Estacionamiento("Toyota", 1000, 5000));
        carros.add(new Estacionamiento("Cadillac", 1000, 5000));
        carros.add(new Estacionamiento("Ford", 1000, 5000));
        carros.add(new Estacionamiento("Chevrolet", 1000, 5000));
        carros.add(new Estacionamiento("DeLorean", 1000, 5000));
        carros.add(new Estacionamiento("Dodge", 1000, 5000));
        
        for(Estacionamiento carro : carros){
            carro.start();
        }
        for(Estacionamiento carro : carros){
            try {
                carro.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Estacionamiento ganador = carros.get(0);
        for(Estacionamiento carro : carros){
            if(carro.getTiempoTotalMs() < ganador.getTiempoTotalMs()){
                ganador = carro;
            }
        }
        System.out.printf("El carro que estacionó más rápido es: %s%n", ganador.getNombre());
    }
    
}
