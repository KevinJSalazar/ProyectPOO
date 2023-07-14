package clases;

import java.util.ArrayList;

public class Vendedor extends Usuario{
    private ArrayList<Vehiculo> vehiculos;
    
    public Vendedor(int id, String nombres, String apellidos, String organizacion, String correo, String clave) {
        super(id, nombres, apellidos, organizacion, correo, clave);
        this.vehiculos = new ArrayList<>();
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }
}
