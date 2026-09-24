public class Estacionamiento extends Thread {
    private String nombre;
    private long tiempoMin;
    private long tiempoMax;
    private long tiempoEstacionado;
    private ControlEstacionamiento control;
    private int lugar;
    private long horaEntrada;
    
    public Estacionamiento(String nombre, long tiempoMin, long tiempoMax, ControlEstacionamiento control) {
        this.nombre = nombre;
        this.tiempoMin = tiempoMin;
        this.tiempoMax = tiempoMax;
        this.control = control;
        this.tiempoEstacionado = generarTiempoAleatorio();
    }
    //Tiempo aleatorio entre tiempoMin y tiempoMax
    private long generarTiempoAleatorio() {
        long rango = tiempoMax - tiempoMin;
        return tiempoMin + (long) (Math.random() * rango);
    }
    // Getters y setters
    public String getNombre() { return nombre; }
    public long gettiempoEstacionado() { return tiempoEstacionado; }
    public int getLugar() { return lugar; }
    public void setLugar(int lugar) { this.lugar = lugar; }
    public long gethoraEntrada() { return horaEntrada; }
    public void sethoraEntrada(long horaEntrada) { this.horaEntrada = horaEntrada; }

    @Override
    public void run() {
        control.registrarLlegada(this);
        try {
            control.entrar(this);
            Thread.sleep(tiempoEstacionado);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        } finally {
            control.salir(this);
        }
    }
}