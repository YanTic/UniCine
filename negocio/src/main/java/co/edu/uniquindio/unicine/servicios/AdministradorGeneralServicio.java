package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.Cupon;
import co.edu.uniquindio.unicine.entidades.Horario;
import co.edu.uniquindio.unicine.entidades.Teatro;

public interface AdministradorGeneralServicio {

    Ciudad crearCiudad(Ciudad ciudad);

    Teatro crearTeatro(Teatro teatro);

    Cupon crearCupon(Cupon cupon);

    Horario crearHorario(Horario horario);
}
