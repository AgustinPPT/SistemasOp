public class Estacionamiento extends Thread {
    private String nombre;
    private long tiempoMin;
    private long tiempoMax;
    private long tiempoEstacionado;
    private ControlEstacionamiento control;
    private int lugar;
    private long horaEntrada;
    //Constructor
    public Estacionamiento(String nombre, long tiempoMin, long tiempoMax, ControlEstacionamiento control) {
        this.nombre = nombre;
        this.tiempoMin = tiempoMin;
        this.tiempoMax = tiempoMax;
        this.control = control;
        this.tiempoEstacionado = generarTiempoAleatorio();
    }

    private long generarTiempoAleatorio() {
    long rango = tiempoMax - tiempoMin;
    return tiempoMin + (long) (Math.random());
    }

    //Getters y Setters
    public String getNombre(){return nombre;}
    public long gettiempoEstacionado(){return tiempoEstacionado;}
    public int getLugar(){return lugar;}
    public void setLugar(int lugar){this.lugar = lugar;}
    public long gethoraEntrada(){return horaEntrada;}
    public void sethoraEntrada(long horaEntrada){this.horaEntrada = horaEntrada;}

    @Override 
    public void run() {
        control.registrarLlegada(this);
        try{
            control.entrar(this);//aqui se bloquea si no hay lugar
            Thread.sleep(tiempoEstacionado);//simula el tiempo que el carro esta estacionado
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
            return;
        }fianlly{
            control.salir(this);//libera el lugar 
        }
    }
}