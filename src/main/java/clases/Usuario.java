package clases;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Usuario {
    protected int id;
    protected String nombres;
    protected String apellidos;
    protected String organizacion;
    protected String correo;
    protected String clave;

    public Usuario(int id, String nombres, String apellidos, String organizacion, String correo, String clave) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.organizacion = organizacion;
        this.correo = correo;
        this.clave = clave;
    }
    
    @Override
    public String toString(){
        return this.id + "|" + this.nombres + "|" + this.apellidos + "|" + this.organizacion + "|" + this.correo;
    }
    
    public static void nextUsuario(String nomfile){
        Scanner sc = new Scanner(System.in);
        ArrayList<Usuario> usuarios = readFile(nomfile);
        String ce;
        do{
            System.out.println("Correo electrónico:");
            ce = sc.nextLine();
        }while(checkCorreo(usuarios, ce));
        System.out.println("Nombres:");
        String n = sc.nextLine();
        System.out.println("Apellidos:");
        String a = sc.nextLine();
        System.out.println("Organización:");
        String o = sc.nextLine();
        System.out.println("Clave:");
        String cl = sc.nextLine();
        int id = Utilitaria.nextID(nomfile);
        Usuario u = new Usuario(id, n, a, o, ce, cl);
        u.saveUsuario(nomfile);
    }
    
    public void saveUsuario(String namefil){
        try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File(namefil), true))){
            String clavehash = Utilitaria.toHexString(Utilitaria.getSHA(this.clave));
            pw.println(this + "|" + clavehash);
            System.out.println("Usuario registrado exitosamente!");
        }
        catch(Exception e){
        }
    }
    
    public static ArrayList<Usuario> readFile(String nomfile){
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try(Scanner sc = new Scanner(new File(nomfile))){
            while(sc.hasNextLine()){
                String linea = sc.nextLine();
                String[] d = linea.split("\\|");
                if(nomfile.equals("vendedores.txt"))
                    usuarios.add(new Vendedor(Integer.parseInt(d[0]), d[1], d[2], d[3], d[4], d[5]));
                else if(nomfile.equals("compradores.txt")){
                    usuarios.add(new Comprador(Integer.parseInt(d[0]), d[1], d[2], d[3], d[4], d[5]));
                }
            }
        }
        catch(Exception e){
        }
        return usuarios;
    }
    
    public static boolean checkCorreo(ArrayList<Usuario> usuarios, String correo){
        for(Usuario u : usuarios){
            if(u.correo.equals(correo)){
                System.out.println("Este correo ya se encuentra registrado");
                return true;
            }
        }
        return false;
    }
    
    public static Usuario searchID(ArrayList<Usuario> usuarios, int id){
        for(Usuario u : usuarios){
            if(u.id == id)
                return u;
        }
        return null;
    }
    
    public static Usuario searchCorreo(ArrayList<Usuario> usuarios, String ce){
        for(Usuario u : usuarios){
            if(u.correo.equals(ce))
                return u;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
