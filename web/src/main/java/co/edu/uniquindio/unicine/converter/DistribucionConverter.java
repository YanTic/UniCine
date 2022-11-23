package co.edu.uniquindio.unicine.converter;

import co.edu.uniquindio.unicine.entidades.DistribucionSillas;
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
public class DistribucionConverter implements Converter<DistribucionSillas>, Serializable {

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Override
    public DistribucionSillas getAsObject(FacesContext context, UIComponent component, String value) {
        DistribucionSillas distribucion = null;

        if (value != null && !"".equals(value)) {
            try {
                distribucion = adminTeatroServicio.obtenerDistribucionSillas(Integer.parseInt(value));
            } catch (Exception e) {
                throw new ConverterException(new FacesMessage(component.getId()+ ": id no es valido"));
            }
        }

        return distribucion;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, DistribucionSillas value) {
        if (value != null) {
            return ""+value.getId();
        }
        return "";
    }
}
