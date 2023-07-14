package clases;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Vehiculo {
    protected int id;
    protected String placa;
    protected String marca;
    protected String modelo;
    protected String tipodemotor;
    protected int año;
    protected double recorrido;
    protected String color;
    protected String tipodecombustible;
    protected int precio;
    protected Vendedor vendedor;
    protected int idVendedor;
    protected ArrayList<Oferta> ofertas;

    public Vehiculo(int id, String placa, String marca, String modelo, String tipodemotor, int año, double recorrido, String color, String tipodecombustible, int precio, int idVendedor) {
        this.id = id;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.tipodemotor = tipodemotor;
        this.año = año;
        this.recorrido = recorrido;
        this.color = color;
        this.tipodecombustible = tipodecombustible;
        this.precio = precio;
        this.idVendedor = idVendedor;
        this.ofertas = new ArrayList<>();
    }
    
    @Override
    public String toString(){
        return this.id + "|" + this.placa + "|" + this.marca + "|" + this.modelo + "|" + this.tipodemotor + "|" + this.año + "|" + this.recorrido + "|" + this.color + "|"+ this.tipodecombustible + "|" + this.precio;
    }
    
    public static void nextMoto(ArrayList<Vehiculo> vehiculos, String correo){
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
        int id = Utilitaria.nextID("vehiculos.txt");
        Vehiculo v = new Vehiculo(id, placa, ma, mo, tm, año, re, co, tc, pr, 0);
        v.saveVehiculo(correo);
    }
    
    public void saveVehiculo(String correo){
        Usuario u = Usuario.searchCorreo(Usuario.readFile("vendedores.txt"), correo);
        try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("vehiculos.txt"), true))){
            pw.println(this + "|" + u.getId());
            System.out.println("Vehículo registrado exitosamente!");
        }
        catch(Exception e){
        }
    }
    
    public static ArrayList<Vehiculo> readFile(){
        ArrayList<Vehiculo> vehiculos = new ArrayList<>();
        try(Scanner sc = new Scanner(new File("vehiculos.txt"))){
            while(sc.hasNextLine()){
                String linea = sc.nextLine();
                String[] d = linea.split("\\|");
                if(d.length == 11){
                    Vehiculo m = new Vehiculo(Integer.parseInt(d[0]), d[1], d[2], d[3], d[4], Integer.parseInt(d[5]), Double.parseDouble(d[6]), d[7], d[8], Integer.parseInt(d[9]), Integer.parseInt(d[10]));
                    vehiculos.add(m);
                }
                if(d.length == 13){
                    Auto a = new Auto(Integer.parseInt(d[0]), d[1], d[2], d[3], d[4], Integer.parseInt(d[5]), Double.parseDouble(d[6]), d[7], d[8], Integer.parseInt(d[9]), Integer.parseInt(d[12]), d[10], d[11]);
                    vehiculos.add(a);
                }
                if(d.length == 14){
                    Camioneta c = new Camioneta(Integer.parseInt(d[0]), d[1], d[2], d[3], d[4], Integer.parseInt(d[5]), Double.parseDouble(d[6]), d[7], d[8], Integer.parseInt(d[9]), Integer.parseInt(d[13]), d[10], d[11], d[12]);
                    vehiculos.add(c);
                }
            }
        }
        catch(Exception e){
        }
        return vehiculos;
    }
    
    public static boolean checkPlaca(ArrayList<Vehiculo> vehiculos, String placa){
        for(Vehiculo v : vehiculos){
            if(v.placa.equals(placa)){
                System.out.println("Esta placa ya se encuentra registrada");
                return true;
            }       
        }
        return false;
    }
    
    public static Vehiculo searchID(ArrayList<Vehiculo> vehiculos, int id){
        for(Vehiculo v : vehiculos){
            if(v.id == id)
                return v;
        }
        return null;
    }
    
    public static Vehiculo searchPlaca(ArrayList<Vehiculo> vehiculos, String placa){
        for(Vehiculo v : vehiculos){
            if(v.placa.equals(placa))
                return v;
        }
        System.out.println("Esta placa no se encuentra registrada");
        return null;
    }
    
    public void verOfertas(ArrayList<Vehiculo> vehiculos, ArrayList<Oferta> ofertas){
        Scanner sc = new Scanner(System.in);
        int opc; int Id;
        for(int i = 0; i < this.ofertas.size(); i++){
            System.out.println("Oferta " + (i+1));
            Id = this.ofertas.get(i).getIdComprador();
            System.out.println("Correo: " + Usuario.searchID(Usuario.readFile("compradores.txt"), Id).getCorreo());
            System.out.println("Precio ofertado: " + this.ofertas.get(i).getValor());
            if(this.ofertas.size() == 1){
                System.out.println("Selecciona una opción: \n1. Aceptar oferta");
                opc = sc.nextInt();
                if(opc == 1)
                    this.aceptarOferta(i, vehiculos, ofertas);
            }
            else if(i == 0){
                System.out.println("Selecciona una opción: \n1. Siguiente oferta \n2. Aceptar oferta");
                opc = sc.nextInt();
                if(opc == 2)
                    this.aceptarOferta(i, vehiculos, ofertas);
            }
            else if((i+1) == this.ofertas.size()){
                System.out.println("Selecciona una opción: \n1. Anterior oferta \n2. Aceptar oferta");
                opc = sc.nextInt();
                switch(opc){
                    case 1:
                        i -= 2;
                        break;
                    case 2:
                        this.aceptarOferta(i, vehiculos, ofertas);
                }
            }
            else{
                System.out.println("Selecciona una opción: \n1. Siguiente oferta \n2. Anterior oferta \n3. Aceptar Oferta");
                opc = sc.nextInt();
                switch(opc){
                    case 1:
                        break;
                    case 2:
                        i -= 2;
                        break;
                    case 3:
                        this.aceptarOferta(i, vehiculos, ofertas);
                }
            }
        }
    }
    
    public void aceptarOferta(int i, ArrayList<Vehiculo> vehiculos, ArrayList<Oferta> ofertas){
        System.out.println("Ha aceptado la oferta!");
        String cuerpo = "El propietario del vehículo:\n" + this.marca + " " + this.modelo + " - Placa: " + this.placa + " - Recorrido: " + this.recorrido + " - Año: " + this.año + "\nHa aceptado tu oferta de: " + this.ofertas.get(i).getValor();
        Utilitaria.sendMensaje(this.ofertas.get(i).getComprador().getCorreo(), "Una oferta ha sido aceptada!", cuerpo);
        ArrayList<Oferta> newOfertas = ofertas;
        ArrayList<Vehiculo> newVehiculos = vehiculos;
        newOfertas.remove(this.ofertas.get(i));
        newVehiculos.remove(this);
        try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("ofertas.txt")))){
            for(Oferta o : newOfertas){
                pw.println(o);
            }
        }
        catch(Exception e){
        }
        try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("vehiculos.txt")))){
            for(Vehiculo v : newVehiculos){
                pw.println(v);
            }
        }
        catch(Exception e){
        }
        System.exit(0);
    }
    
    public static ArrayList<Vehiculo> filtrarVehiculos(ArrayList<Vehiculo> vehiculos, String tipo, double recMin, double recMax, int añoMin, int añoMax, int preMin, int preMax){
        ArrayList<Vehiculo> vehiculosfil = new ArrayList<>();
        String tipoVeh;
        for(Vehiculo v : vehiculos){
            tipoVeh = "Moto";
            if(v instanceof Camioneta)
                tipoVeh = "Camioneta";
            else if(v instanceof Auto)
                tipoVeh = "Auto";
            if(tipo == null || tipoVeh.equals(tipo)){
                if(recMin <= v.recorrido && v.recorrido <= recMax){
                    if(añoMin <= v.año && v.año <= añoMax){
                        if(preMin <= v.precio && v.precio <= preMax)
                            vehiculosfil.add(v);
                    }
                }
            }
        }
        return vehiculosfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipodemotor() {
        return tipodemotor;
    }

    public void setTipodemotor(String tipodemotor) {
        this.tipodemotor = tipodemotor;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public double getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(double recorrido) {
        this.recorrido = recorrido;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipodecombustible() {
        return tipodecombustible;
    }

    public void setTipodecombustible(String tipodecombustible) {
        this.tipodecombustible = tipodecombustible;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public ArrayList<Oferta> getOfertas() {
        return ofertas;
    }

    public void setOfertas(ArrayList<Oferta> ofertas) {
        this.ofertas = ofertas;
    }
}
