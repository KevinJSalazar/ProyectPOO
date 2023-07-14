package clases;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Oferta {
    private int id;
    private int valor;
    private Comprador comprador;
    private int idComprador;
    private Vehiculo vehiculo;
    private int idVehiculo;

    public Oferta(int id, int valor, int idComprador, int idVehiculo) {
        this.id = id;
        this.valor = valor;
        this.idComprador = idComprador;
        this.idVehiculo = idVehiculo;
    }
    
    @Override
    public String toString(){
        return this.id + "|" + this.valor + "|" + this.idComprador + "|" + this.idVehiculo;
    }
    
    public static ArrayList<Oferta> readFile(){
        ArrayList<Oferta> ofertas = new ArrayList<>();
        try(Scanner sc = new Scanner(new File("ofertas.txt"))){
            while(sc.hasNextLine()){
                String linea = sc.nextLine();
                String[] d = linea.split("\\|");
                Oferta o = new Oferta(Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2]), Integer.parseInt(d[3]));
                ofertas.add(o);
            }
        }
        catch(Exception e){
        }
        return ofertas;
    }
    
    public void saveOferta(){
        try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("ofertas.txt"), true))){
            pw.println(this);
            System.out.println("Oferta registrada exitosamente!");
        }
        catch(Exception e){
        }
    }
    
    public static void link(ArrayList<Usuario> usuarios, ArrayList<Vehiculo> vehiculos, ArrayList<Oferta> ofertas){
        for(Oferta o : ofertas){
            Usuario u = Usuario.searchID(usuarios, o.getIdComprador());
            Vehiculo v = Vehiculo.searchID(vehiculos, o.getIdVehiculo());
            Comprador c = (Comprador) u;
            c.getOfertas().add(o);
            v.getOfertas().add(o);
            o.setComprador(c);
            o.setVehiculo(v);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public int getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(int idComprador) {
        this.idComprador = idComprador;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
}
