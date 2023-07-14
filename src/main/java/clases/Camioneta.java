package clases;

import java.util.ArrayList;
import java.util.Scanner;

public class Camioneta extends Auto{
    private String tracción;

    public Camioneta(int id, String placa, String marca, String modelo, String tipodemotor, int año, double recorrido, String color, String tipodecombustible, int precio, int idVendedor, String vidrios, String transmision, String tracción) {
        super(id, placa, marca, modelo, tipodemotor, año, recorrido, color, tipodecombustible, precio, idVendedor, vidrios, transmision);
        this.tracción = tracción;
    }
    
    @Override
    public String toString(){
        return super.toString() + "|" + this.tracción;
    }
    
     public static void nextCamioneta(ArrayList<Vehiculo> vehiculos, String correo){
        Scanner sc = new Scanner(System.in);
        String placa;
        do{
            System.out.println("Ingrese la placa del vehículo:");
            placa = sc.nextLine();
        }while(checkPlaca(vehiculos, placa));
        System.out.println("Marca:");
        String ma = sc.nextLine();
        System.out.println("Modelo:");
        String mo = sc.nextLine();
        System.out.println("Tipo de motor:");
        String tm = sc.nextLine();
        System.out.println("Año:");
        int año = sc.nextInt();
        System.out.println("Recorrido:");
        double re = sc.nextDouble();
        sc.nextLine();
        System.out.println("Color:");
        String co = sc.nextLine();
        System.out.println("Tipo de combustible:");
        String tc = sc.nextLine();
        System.out.println("Precio:");
        int pr = sc.nextInt();
        sc.nextLine();
        System.out.println("Vidrios:");
        String vi = sc.nextLine();
        System.out.println("Transmisión:");
        String tr = sc.nextLine();
        System.out.println("Tracción:");
        String tra = sc.nextLine();
        int id = Utilitaria.nextID("vehiculos.txt");
        Camioneta c = new Camioneta(id, placa, ma, mo, tm, año, re, co, tc, pr, 0, vi, tr, tra);
        c.saveVehiculo(correo);
    }

    public String getTracción() {
        return tracción;
    }

    public void setTracción(String tracción) {
        this.tracción = tracción;
    }
}
