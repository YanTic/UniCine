package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.dto.PeliculaFuncionDTO;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;

@Component
@ViewScoped
public class BusquedaBean implements Serializable {

    @Getter @Setter
    private String busqueda;

    @Autowired
    private ClienteServicio clienteServicio;

    @Value("#{param['busqueda']}") // Esta variable tiene asignado con el valor del parametro que llega en la URL
    private String busquedaParam;

    @Getter @Setter
    private List<PeliculaFuncionDTO> peliculas;

    @PostConstruct
    public void init(){
        System.out.println("texto cajon: "+busqueda);
        System.out.println("texto param: "+busquedaParam);

        if(busquedaParam != null && !busquedaParam.isEmpty()) {
            peliculas = clienteServicio.buscarPeliculaFuncion(busquedaParam);
        }
    }


    public String buscar() {

        // Redireccion pero sin pasar el parametro
        //return "/resultados_busqueda?faces-redirect=true";

        // Esta es una forma para pasar varios parametros con '&', porque no se puede usar '?' mas de una vez
        // Esta forma puede ocasionar errores por el tipo de codificacion del archivo, por lo que se usar '&amp;'
        //return "/resultados_busqueda?faces-redirect=true&parametro1=2&parametro2=hola";

        if(!busqueda.isEmpty()) {
            return "/resultados_busqueda?faces-redirect=true&amp;busqueda="+busqueda;
        }
        else{
            return "";
        }
    }
}
