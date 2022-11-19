package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class DetallePeliculaBean implements Serializable {

    @Value("#{param['pelicula_id']}")
    private String peliculaId;

    @Getter @Setter
    private Pelicula pelicula;

    private List<Funcion> funciones; // Traer las funciones dada la pelicula y la ciudad
                                     // Y dada la pelicula y ciudad, traer los teatros

    @Getter @Setter // Le dejamos solo el getter-setter a teatro, para que la xhtml solo obtenga la lista de teatros con sus salas y funciones
    private List<Teatro> teatros;

    private List<Sala> salas;

    @Getter @Setter
    private List<Ciudad> ciudades;

    @Getter @Setter
    private Ciudad ciudad;

    @Getter @Setter
    private List<LocalDate> fechas;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @PostConstruct
    public void init() {
        try {
            if (!peliculaId.isEmpty() && peliculaId != null) {
                pelicula = adminGeneralServicio.obtenerPelicula(Integer.parseInt(peliculaId));
                ciudades = adminGeneralServicio.listarCiudades();
                funciones = new ArrayList<>();
                teatros = new ArrayList<>();
                salas = new ArrayList<>();
                ciudad = new Ciudad();
                fechas = new ArrayList<>();
                llenarFechas();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    // Este metodo solo se ejecuta cuando el usuario click en el boton de las fechas, este si no recibe un ciudad para
    // listar las funciones (es decir: el usuario no escogió una ciudad, por lo tanto es null) este va a tirar la excepcion
    // y no va a hacer nada de lo que está despues de la linea de 'funciones = adminTeatroSer...', por lo que no se van
    // a setear ni los teatros ni salas que están asociados a las funciones.
    // Este metodo es muy importante si se quieren obtener las funciones disponibles de una pelicula en una fecha especifica, para
    // un teatro y salas en una ciudad. Al setear las funciones, sus teatros y sus salas, en la vista (el xhtml) a traves del
    // bean se facilita la obtencion de todos esos elementos
    public void actualizarTeatroSalasFunciones(LocalDate fecha) {
        try {
            funciones = adminTeatroServicio.listarFuncionesPorPeliculaCiudadFecha(Integer.parseInt(peliculaId), ciudad.getId(), fecha);

            // Agregamos a una lista los teatros y salas de las funciones, sin repetir teatro o sala
            for(Funcion f : funciones) {
                if (!teatros.contains(f.getSala().getTeatro())) {
                    teatros.add(f.getSala().getTeatro());
                }
                if (!salas.contains(f.getSala())) {
                    salas.add(f.getSala());
                }
            }

            // Y ahí eliminamos los datos que vienen de la BD (como las salas del teatro y las funciones de la sala) y se relaciona
            // cada una entre si, ejem: a los teatros se les agrega las salas que (como se verificó antes) tengan una asociacion
            // con las funciones, y se le agregan a la sala las funciones que tengan relacion
            //
            // Esto se hace más que tod para que desde la web obtenga las salas-teatros-funciones correspondientes de cada una,
            // porque sino mostrará en un teatro todas las salas (sin importar si son del teatro) y en las salas todas las
            // funciones (sin importar si son de esa sala)
            for(Teatro t : teatros) {
                // Eliminamos las salas asociadas que llegan de la BD y así conectarle las salas que tienen relacion
                // con las funciones. porque sino, la pagweb mostrará datos iguales,
                t.getSalas().clear();

                for(Sala s : salas) {
                    if(!t.getSalas().contains(s) && s.getTeatro().getId() == t.getId()) {
                        t.getSalas().add(s);
                    }

                    // Eliminamos las funciones asociadas que llegan de la BD y así conectarle las funciones que tienen relacion
                    // con las salas. porque sino.
                    s.getFunciones().clear();

                    for(Funcion f : funciones) {
                        if (!s.getFunciones().contains(f) && f.getSala().getId() == s.getId()) {
                           s.getFunciones().add(f);
                        }
                    }
                }
            }

            // Pruebas para verificar con los datos en la B
         /* funciones.forEach(System.out::println);
            teatros.forEach(System.out::println);
            salas.forEach(System.out::println); */


        } catch (Exception e) {
            System.out.println(e.getMessage());
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensajes", fm);
        }
    }

    // Se utiliza cuando se cambie la ciudad, cuando se cambia se deberia volver a reiniciar los datos de las
    // funciones salas y teatros, para que no queden con los datos de una busqueda anterior con otra ciudad
    public void actualizarFunciones() {
        funciones = new ArrayList<>();
        teatros = new ArrayList<>();
        salas = new ArrayList<>();
    }

    public void llenarFechas() {
        fechas.add(LocalDate.now());

        for(int i = 1 ; i<7; i++) {
            fechas.add(LocalDate.now().plusDays(i));
        }

        fechas.forEach(System.out::println);
    }

    public String getMsgBtnDiaFunciones(LocalDate fecha) {
        String dia = fecha.getDayOfWeek().toString();
        String fechaCompleta = ""+ fecha.getDayOfMonth()+" "+ fecha.getMonth().name()+" "+ fecha.getYear();

        //System.out.println(dia + "\n"+ fechaCompleta);

        return dia + "\n"+ fechaCompleta;
    }

    public String getMsgTeatro(Teatro t) {
        return t.getNombre() + " - "+ t.getDireccion();
    }

    public String realizarCompra(Integer idFuncion) {
        //return "/proceso_compra?faces-redirect=true&parametro1=2&parametro2=hola";
        if(!idFuncion.equals(null) || idFuncion != null) {
            return "/cliente/proceso_compra?faces-redirect=true&amp;funcion_id="+idFuncion;
        }
        else{
            return "";
        }
    }

}
