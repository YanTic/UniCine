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

    @Getter @Setter
    private List<Funcion> funciones; // Traer las funciones dada la pelicula y la ciudad
                                     // Y dada la pelicula y ciudad, traer los teatros

    @Getter @Setter
    private List<Teatro> teatros;

    @Getter @Setter
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


    public void actualizarFunciones() {
        try {
            funciones = adminTeatroServicio.listarFuncionesPorPeliculaCiudad(Integer.parseInt(peliculaId), ciudad.getId());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensajes", fm);
        }
    }

    public void actualizarTeatroSalas(LocalDate fecha) {
        try {
            teatros = adminGeneralServicio.listarTeatrosPorCiudadPeliculaFecha(ciudad.getId(), Integer.parseInt(peliculaId),fecha);
            salas = adminTeatroServicio.listarSalasPorCiudadPeliculaFecha(ciudad.getId(), Integer.parseInt(peliculaId),fecha); // Es la misma consulta solo para obtener las salas

            teatros.forEach(System.out::println);
            salas.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensajes", fm);
        }
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

        System.out.println(dia + "\n"+ fechaCompleta);

        return dia + "\n"+ fechaCompleta;
    }

    public String getMsgTeatro(Teatro t) {
        return t.getNombre() + " - "+ t.getDireccion();
    }

    public void realizarCompra() {
        System.out.println("compre pues mor");
    }

}
