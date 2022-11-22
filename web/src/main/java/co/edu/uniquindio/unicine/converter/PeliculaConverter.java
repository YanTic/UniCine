package co.edu.uniquindio.unicine.converter;

import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import java.io.Serializable;

@Component
public class PeliculaConverter implements Converter<Pelicula>, Serializable {

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @Override
    public Pelicula getAsObject(FacesContext context, UIComponent component, String value) {
        Pelicula pelicula = null;

        if (value != null && !"".equals(value)) {
            try {
                pelicula = adminGeneralServicio.obtenerPelicula(Integer.parseInt(value));
            } catch (Exception e) {
                throw new ConverterException(new FacesMessage(component.getId()+ ": id no es valido"));
            }
        }

        return pelicula;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Pelicula value) {
        if (value != null) {
            return ""+value.getId();
        }
        return "";
    }
}
