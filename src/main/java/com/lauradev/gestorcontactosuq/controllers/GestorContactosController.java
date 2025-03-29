package com.lauradev.gestorcontactosuq.controllers;

import com.lauradev.gestorcontactosuq.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;





public class GestorContactosController {

    Contacto contacto = Contacto.builder().nombre("santiago").apellido("Gonsalez").id("5236541256").fechaCumpleanios(LocalDate.of(1990, 5, 15)).email("santigonza@jahsj.com").telefono("2554136520").imagenPefil(null).build();

    @FXML
    public void initialize() {

        cl_nombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        cl_apellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        cl_id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        cl_fecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaCumpleanios().toString()));
        cl_email.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        cl_telefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));



        img_imagenPerfil.setImage(imagenDeFault);

        tablaUsuario.setItems(listaContactos);


        List<String> opciones = new ArrayList<>();
        opciones.add("Nombre");
        opciones.add("ID");

        cbOpciones.setItems( FXCollections.observableList(opciones) );

    }

    //Buttons
    @FXML
    private void crearActionButton() {
        String nombre = campoTxtNombre.getText();
        String apellido = campoTxtApellido.getText();
        String id = campoTxtID.getText();
        LocalDate fechaNacimiento = Datepuckerfecha.getValue();
        String email = campoTxtEmail.getText();
        String telefono = campoTxtTelefono.getText();

        if (!validarCampos(nombre, apellido, id, fechaNacimiento, email, telefono)) {
            return;
        }

        Contacto nuevoContacto = new Contacto(nombre, apellido, id, fechaNacimiento.toString(), email, telefono);
        listaContactos.add(nuevoContacto);
        limpiarCampos();
    }


    @FXML
    private TableView<?> TableUsuario;
    @FXML private TextField campoTxtNombre;
    @FXML private TextField campoTxtApellido;
    @FXML private TextField campoTxtID;
    @FXML private DatePicker Datepuckerfecha;
    @FXML private TextField campoTxtEmail;
    @FXML private TextField campoTxtTelefono;
    @FXML private ImageView img_imagenPerfil;

    //TextFileds
    @FXML public TextField txtFiltro;
    @FXML private TableView<Contacto> tablaUsuario;
    @FXML private TableColumn<Contacto, String> cl_nombre;
    @FXML private TableColumn<Contacto, String> cl_apellido;
    @FXML private TableColumn<Contacto, String> cl_id;
    @FXML private TableColumn<Contacto, String> cl_fecha;
    @FXML private TableColumn<Contacto, String> cl_email;
    @FXML private TableColumn<Contacto, String> cl_telefono;


    @FXML private ComboBox<String> cbOpciones;


    private final ObservableList<Contacto> listaContactos = FXCollections.observableArrayList();


    Image imagenDeFault= new Image(getClass().getResource("/imagenproyecto.jpg").toExternalForm());


    @FXML
    private void seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        File archivoSeleccionado = fileChooser.showOpenDialog(null);

        if (archivoSeleccionado != null) {
            Image imagen = new Image(archivoSeleccionado.toURI().toString());
            img_imagenPerfil.setImage(imagen);
        }
    }

    private void limpiarCampos() {
        campoTxtNombre.clear();
        campoTxtApellido.clear();
        campoTxtID.clear();
        Datepuckerfecha.setValue(null);
        campoTxtEmail.clear();
        campoTxtTelefono.clear();
        img_imagenPerfil.setImage(imagenDeFault);
    }

    private boolean validarCampos(String nombre, String apellido, String id, LocalDate fechaNacimiento, String email, String telefono) {
        return false;
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void editarActionButton() {
        Contacto seleccionado = tablaUsuario.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un contacto para editar.");
            return;
        }

        String nombre = campoTxtNombre.getText();
        String apellido = campoTxtApellido.getText();
        String id = campoTxtID.getText();
        LocalDate fechaNacimiento = Datepuckerfecha.getValue();
        String email = campoTxtEmail.getText();
        String telefono = campoTxtTelefono.getText();

        if (!validarCampos(nombre, apellido, id, fechaNacimiento, email, telefono)) {
            return;
        }

        seleccionado.setNombre(nombre);
        seleccionado.setApellido(apellido);
        seleccionado.setId(id);
        seleccionado.setFechaCumpleanios(LocalDate.parse(fechaNacimiento.toString()));
        seleccionado.setEmail(email);
        seleccionado.setTelefono(telefono);
        tablaUsuario.refresh();
        limpiarCampos();
    }
    @FXML
    private void eliminarActionButton() {
        Contacto seleccionado = tablaUsuario.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Selecciona un contacto para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Deseas eliminar este contacto?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> respuesta = confirmacion.showAndWait();

        if (respuesta.isPresent() && respuesta.get() == ButtonType.YES) {
            listaContactos.remove(seleccionado);
        }
    }

    @FXML
    private void agregarActionButton() {

    }

    @FXML
    public void btnActionButtonSeleccionarImg() {

    }

    @FXML
    public void eliminarFoto(ActionEvent actionEvent) {
    }

    @FXML
    public void mostrarFoto(ActionEvent actionEvent) {
    }

    public void buscarContactos(ActionEvent actionEvent) {
        String seleccion = cbOpciones.getValue();

        if(seleccion != null) {

            if(seleccion.equals("ID")){
                String texto = txtFiltro.getText();

                if( !texto.matches("^\\d+$")){
                    mostrarAlerta("Solo se puede escribir numeros");
                }else{

                }
            }

        }

    }
}