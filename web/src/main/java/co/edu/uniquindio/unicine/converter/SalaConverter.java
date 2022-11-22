package co.edu.uniquindio.unicine.converter;

import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.entidades.Sala;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import java.io.Serializable;

@Component
public class SalaConverter implements Converter<Sala>, Serializable {

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Override
    public Sala getAsObject(FacesContext context, UIComponent component, String value) {
        Sala sala = null;

        if (value != null && !"".equals(value)) {
            try {
                sala =  adminTeatroServicio.obtenerSala(Integer.parseInt(value));
            } catch (Exception e) {
                throw new ConverterException(new FacesMessage(component.getId()+ ": id no es valido"));
            }
        }

        return sala;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Sala value) {
        if (value != null) {
            return ""+value.getId();
        }
        return "";
    }
}
