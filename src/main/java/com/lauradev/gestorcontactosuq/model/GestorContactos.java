package com.lauradev.gestorcontactosuq.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GestorContactos {

    private ArrayList<Contacto> contactos;

    private final List<Contacto> listaContactos = new ArrayList<>();

    public void agregarContacto(Contacto contacto) {
        listaContactos.add(contacto);
    }

    public void editarContacto(Contacto contactoEditado) {
        Optional<Contacto> contactoExistente = listaContactos.stream()
                .filter(c -> c.getId().equals(contactoEditado.getId()))
                .findFirst();



        contactoExistente.ifPresent(contacto -> {
            contacto.setNombre(contactoEditado.getNombre());
            contacto.setApellido(contactoEditado.getApellido());
            contacto.setTelefono(contactoEditado.getTelefono());
            contacto.setEmail(contactoEditado.getEmail());
            contacto.setFechaCumpleanios(contactoEditado.getFechaCumpleanios());
        });
    }

    public void eliminarContacto(String id) {
        listaContactos.removeIf(contacto -> contacto.getId().equals(id));
    }

    public Contacto buscarContacto(String criterio, String valor) {
        return listaContactos.stream()
                .filter(contacto -> (criterio.equals("Nombre") && contacto.getNombre().equalsIgnoreCase(valor)) ||
                        (criterio.equals("Teléfono") && contacto.getTelefono().equals(valor)))
                .findFirst()
                .orElse(null);
    }

    public List<Contacto> obtenerContactos() {
        return listaContactos;
    }

    public ArrayList<String> listarOpciones(){
        ArrayList<String> opciones=new ArrayList<>();
        opciones.add("Telefono");
        opciones.add("Nombre");
        return opciones;
    }

    public List<Contacto> buscarContactoTelefono(String telefono){
        return contactos
                .stream()
                .filter(c -> c.getTelefono().equals(telefono))
                .collect(Collectors.toList());
    }

    public List<Contacto> buscarContactoNombre(String nombre){
        return contactos
                .stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
                .collect(Collectors.toList());
    }

    public boolean verificarContacto(String telefono){
        boolean encontrado=false;
        for(Contacto contacto: contactos){
            if(contacto.getTelefono().equals(telefono)){
                return true;
            }
        }
        return encontrado;
    }

    //Validaciones

    public static boolean validarTelefono(String telefono){
        String regexTelefono="^\\+?\\d{1,3}?\\d{7,15}$";
        Pattern expresionValida=Pattern.compile(regexTelefono);
        Matcher matcherTelefono=expresionValida.matcher(telefono);
        boolean valido;
        valido=matcherTelefono.matches();
        return valido;
    }

    public static boolean validarCorreo(String correo){
        String regexEmail="^[a-zA-Z0-9._%+-]+@[a-zA-z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern expresionValida=Pattern.compile(regexEmail);
        Matcher matcher=expresionValida.matcher(correo);
        boolean valido;
        valido= matcher.matches();
        return valido;
    }

    public static boolean validarTexto(String texto){
        String regexTexto="[a-zA-ZñÑ]+";
        Pattern expresionValida= Pattern.compile(regexTexto);
        Matcher matcher= expresionValida.matcher(texto);
        return matcher.matches();
    }
}
