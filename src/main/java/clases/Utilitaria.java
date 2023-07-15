package clases;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Utilitaria {
    private Utilitaria(){}
    
    public static void mostrarMenuPrincipal(){
        ArrayList<Usuario> compradores = Comprador.readFile("compradores.txt");
        ArrayList<Vehiculo> vehiculos = Vehiculo.readFile();
        ArrayList<Oferta> ofertas = Oferta.readFile();
        Oferta.link(compradores, vehiculos, ofertas);
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecciona una opción:\n1. Vendedor\n2. Comprador\n3. Salir");
        int opc = sc.nextInt();
        
        switch(opc){
            case 1:
                mostrarMenuVendedor(vehiculos, ofertas);
            case 2:
                mostrarMenuComprador(vehiculos, compradores);
            case 3:
                System.exit(0);
            default:
                System.out.println("Esta opción no está disponible.");
                mostrarMenuPrincipal();
                break;
        }
    }
    
    public static void mostrarMenuVendedor(ArrayList<Vehiculo> vehiculos, ArrayList<Oferta> ofertas){
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecciona una opción:\n1. Registrar un nuevo vendedor\n2. Registrar un nuevo vehículo\n3. Aceptar oferta\n4. Regresar");
        int opc = sc.nextInt();
        sc.nextLine();
        String correo;
        switch(opc){
            case 1:
                System.out.println("Ingrese los datos del vendedor:");
                Vendedor.nextUsuario("vendedores.txt");
                break;
            case 2:
                correo = loopVerificarCuenta("vendedores.txt");
                System.out.println("Ha iniciado sesión con: " + correo + "\nEscriba qué clase de vehículo quiere registrar: Auto, Moto o Camioneta");
                String tipo = sc.nextLine();
                switch(tipo){
                    case "Moto":
                        Vehiculo.nextMoto(vehiculos, correo);
                        break;
                    case "Auto":
                        Auto.nextAuto(vehiculos, correo);
                        break;
                    case "Camioneta":
                        Camioneta.nextCamioneta(vehiculos, correo);
                        break;
                    default:
                        System.out.println("Debe escribir 'Auto', 'Moto' ó 'Camioneta'");
                        mostrarMenuVendedor(vehiculos, ofertas);
                        break;
                }
                break;
            case 3:
                correo = loopVerificarCuenta("vendedores.txt");
                ArrayList<Usuario> vendedores = Usuario.readFile("vendedores.txt");
                Usuario u = Usuario.searchCorreo(vendedores, correo);
                Vendedor ven = (Vendedor) u;
                Vehiculo v; int idVenVeh = -1;
                do{
                    System.out.println("Ingrese la placa:");
                    String placa = sc.nextLine();
                    v = Vehiculo.searchPlaca(vehiculos, placa);
                    if(v != null){
                        idVenVeh = v.getIdVendedor();
                        if(idVenVeh != ven.getId())
                        System.out.println("La placa que ingresó pertenece a un vehículo que no es suyo");
                    }
                }while(v == null || idVenVeh != ven.getId());
                if(v.getOfertas().isEmpty())
                    System.out.println("No se han realizado ofertas");
                else{
                    System.out.println(v.getMarca() + " " + v.getModelo() + " " + v.getRecorrido() + " Precio: " + v.getPrecio());
                    if(v.getOfertas().size() == 1)
                        System.out.println("Se ha realizado una oferta");
                    if(v.getOfertas().size() > 1)
                        System.out.println("Se han realizado " + v.getOfertas().size() + " ofertas");
                    v.verOfertas(vehiculos, ofertas, ven);
                }
                break;
            case 4:
                mostrarMenuPrincipal();
            default:
                System.out.println("Esta opción no está disponible.");
                mostrarMenuVendedor(vehiculos, ofertas);
                break;
        }
        System.exit(0);
    }
    
    public static void mostrarMenuComprador(ArrayList<Vehiculo> vehiculos, ArrayList<Usuario> compradores){
        Scanner sc = new Scanner(System.in);
        System.out.println("Selecciona una opción:\n1. Registrar un nuevo comprador\n2. Ofertar por un vehículo");
        int opc = sc.nextInt();
        sc.nextLine();
        String correo;
        switch(opc){
            case 1:
                System.out.println("Ingrese los datos del comprador:");
                Comprador.nextUsuario("compradores.txt");
                break;
            case 2:
                correo = loopVerificarCuenta("compradores.txt");
                System.out.println("Ha iniciado sesión con: " + correo);
                Usuario u = Usuario.searchCorreo(compradores, correo);
                Comprador c = (Comprador) u;
                c.buscarVehiculos(vehiculos);
                break;
            default:
                System.out.println("Esta opción no está disponible.");
                mostrarMenuComprador(vehiculos, compradores);
                break;
        }
        System.exit(0);
    }
    
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
     
    public static String toHexString(byte[] hash){
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 64){
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
    
    public static int nextID(String nomfile){
        int id = 0;
        try(Scanner sc = new Scanner(new File(nomfile))){
            while(sc.hasNextLine()){
                String linea = sc.nextLine();
                String[] datos = linea.split("\\|");
                id = Integer.parseInt(datos[0]);
            }
        }
        catch(Exception e){
        }
        return id+1;
    }
    
    public static boolean verificarCuenta(String ce, String cl, String nomfile){
        try(Scanner sc = new Scanner(new File(nomfile))){
            String clavehash = toHexString(getSHA(cl));
            while(sc.hasNextLine()){
                String linea = sc.nextLine();
                String[] datos = linea.split("\\|");
                if(ce.equals(datos[4]))
                    return clavehash.equals(datos[5]);
            }
        }
        catch(Exception e){
        }
        return false;
    }
    
    public static String loopVerificarCuenta(String nomfile){
        Scanner sc = new Scanner(System.in);
        String ce; String cl;
        do{
            System.out.println("Ingrese su correo electrónico:");
            ce = sc.nextLine();
            System.out.println("Ingrese la contraseña:");
            cl = sc.nextLine();
            if(!verificarCuenta(ce, cl, nomfile))
                System.out.println("Correo o contraseña incorrectas.");
        }while(!verificarCuenta(ce, cl, nomfile));
        return ce;
    }
    
    public static void sendMensaje(String destinatario, String asunto, String cuerpo) {
        String remitente = "vendemosttv@gmail.com";
        String claveemail = "frupcoheitutbiza";

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", claveemail);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, claveemail);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
