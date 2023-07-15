package clases;

import java.util.ArrayList;
import java.util.Scanner;

public class Comprador extends Usuario{
    private ArrayList<Oferta> ofertas;

    public Comprador(int id, String nombres, String apellidos, String organizacion, String correo, String clave) {
        super(id, nombres, apellidos, organizacion, correo, clave);
        this.ofertas = new ArrayList<>();
    }
    
    public void newOferta(Vehiculo v, int valor){
        int Id = Utilitaria.nextID("ofertas.txt");
        Oferta o = new Oferta(Id, valor, this.getId(), v.getId());
        o.saveOferta();
        System.exit(0);
    }
    
    public void buscarVehiculos(ArrayList<Vehiculo> vehiculos){
        Scanner sc = new Scanner(System.in);
        String tipo = null; double recMin = 0; double recMax = Double.MAX_VALUE; int añoMin = 0; int añoMax = Integer.MAX_VALUE; int preMin = 0; int preMax = Integer.MAX_VALUE;
        int opc;
        do{
            System.out.println("Escoja qué parámetros desea determinar: \n 1. Tipo de vehículo \n 2. Recorrido \n 3. Año \n 4. Precio \n 5. Buscar");
            opc = sc.nextInt();
            sc.nextLine();
            switch(opc){
                case 1:
                    System.out.println("Ingrese el tipo de vehículo que busca (Auto/Camioneta/Moto):");
                    tipo = sc.nextLine();
                    break;
                case 2:
                    System.out.println("Recorrido mínimo:");
                    recMin = sc.nextDouble();
                    System.out.println("Recorrido máximo:");
                    recMax = sc.nextDouble();
                    break;
                case 3:
                    System.out.println("Año mínimo:");
                    añoMin = sc.nextInt();
                    System.out.println("Año máximo:");
                    añoMax = sc.nextInt();
                    break;
                case 4:
                    System.out.println("Precio mínimo:");
                    preMin = sc.nextInt();
                    System.out.println("Precio máximo");
                    preMax = sc.nextInt();
                    break;
                case 5:
                    ArrayList<Vehiculo> filVehiculos = Vehiculo.filtrarVehiculos(vehiculos, tipo, recMin, recMax, añoMin, añoMax, preMin, preMax);
                    this.verVehiculos(filVehiculos);
                    break;
                default:
                    System.out.println("No es una de las opciones");
                    break;
            }
        }while(opc != 5);
    }
    
    public void verVehiculos(ArrayList<Vehiculo> vehiculos){
        Scanner sc = new Scanner(System.in);
        int opc; int valor; Vehiculo v; String tipo;
        if(vehiculos.isEmpty())
            System.out.println("No se encontraron resultados");
        else{
            for(int i = 0; i < vehiculos.size(); i++){
                System.out.println("Vehículo " + (i+1));
                v = vehiculos.get(i);
                tipo = "Moto";
                if(v instanceof Camioneta)
                    tipo = "Camioneta";
                else if(v instanceof Auto)
                    tipo = "Auto";
                System.out.println(tipo + " - " + v.marca + " " + v.modelo + " - Recorrido: " + v.recorrido + " - Año: " + v.año + " - Precio: " + v.precio);
                if(vehiculos.size() == 1){
                    System.out.println("Selecciona una opción: \n1. Ofertar");
                    opc = sc.nextInt();
                    if(opc == 1){
                            System.out.println("Ingrese el valor de su oferta:");
                            valor = sc.nextInt();
                            this.newOferta(vehiculos.get(i), valor);
                    }
                }
                else if(i == 0){
                    System.out.println("Selecciona una opción: \n1. Siguiente vehículo \n2. Ofertar");
                    opc = sc.nextInt();
                    if(opc == 2){
                            System.out.println("Ingrese el valor de su oferta:");
                            valor = sc.nextInt();
                            this.newOferta(vehiculos.get(i), valor);
                    }
                }
                else if((i+1) == vehiculos.size()){
                    System.out.println("Selecciona una opción: \n1. Anterior vehículo \n2. Ofertar");
                    opc = sc.nextInt();
                    switch(opc){
                        case 1:
                            i -= 2;
                            break;
                        case 2:
                            System.out.println("Ingrese el valor de su oferta:");
                            valor = sc.nextInt();
                            this.newOferta(vehiculos.get(i), valor);
                            break;
                    }
                }
                else{
                    System.out.println("Selecciona una opción: \n1. Siguiente vehículo \n2. Anterior vehículo \n3. Ofertar");
                    opc = sc.nextInt();
                    switch(opc){
                        case 1:
                            break;
                        case 2:
                            i -= 2;
                            break;
                        case 3:
                            System.out.println("Ingrese el valor de su oferta:");
                            valor = sc.nextInt();
                            this.newOferta(vehiculos.get(i), valor);
                            break;
                    }
                }
            }
        }
    }

    public ArrayList<Oferta> getOfertas() {
        return ofertas;
    }

    public void setOfertas(ArrayList<Oferta> ofertas) {
        this.ofertas = ofertas;
    }
}
