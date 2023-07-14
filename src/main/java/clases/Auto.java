/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author evin
 */
public class Auto extends Vehiculo{
    private String vidrios;
    private String transmision;

    public Auto(int id, String placa, String marca, String modelo, String tipodemotor, int año, double recorrido, String color, String tipodecombustible, int precio, int idVendedor, String vidrios, String transmision) {
        super(id, placa, marca, modelo, tipodemotor, año, recorrido, color, tipodecombustible, precio, idVendedor);
        this.vidrios = vidrios;
        this.transmision = transmision;
    }
    
    @Override
    public String toString(){
        return super.toString() + "|" + this.vidrios + "|" + this.transmision;
    }
    
     public static void nextAuto(ArrayList<Vehiculo> vehiculos, String correo){
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
        int id = Utilitaria.nextID("vehiculos.txt");
        Auto a = new Auto(id, placa, ma, mo, tm, año, re, co, tc, pr, 0, vi, tr);
        a.saveVehiculo(correo);
    }

    public String getVidrios() {
        return vidrios;
    }

    public void setVidrios(String vidrios) {
        this.vidrios = vidrios;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }
}
