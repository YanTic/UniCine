package co.edu.uniquindio.unicine.converter;

import co.edu.uniquindio.unicine.entidades.Horario;
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
public class HorarioConverter implements Converter<Horario>, Serializable {

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Override
    public Horario getAsObject(FacesContext context, UIComponent component, String value) {
        Horario horario = null;

        if (value != null && !"".equals(value)) {
            try {
                horario =  adminTeatroServicio.obtenerHorario(Integer.parseInt(value));
            } catch (Exception e) {
                throw new ConverterException(new FacesMessage(component.getId()+ ": id no es valido"));
            }
        }

        return horario;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Horario value) {
        if (value != null) {
            return ""+value.getId();
        }
        return "";
    }
}
