package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Confiteria;
import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.MetodoPago;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@ViewScoped
public class CompraBean implements Serializable {

    @Value("#{param['funcion_id']}")
    private String funcionId;

    @Getter @Setter
    private Funcion funcion;

    //@Value("#{param['cliente_id']}")
    private Integer clienteId;

    @Getter @Setter
    private Cliente cliente;

    @Getter @Setter
    private String codigoCupon;

    @Getter @Setter
    private MetodoPago metodoPagoSeleccionado;

    @Getter @Setter
    private List<MetodoPago> metodosPago;

    @Getter @Setter
    private List<Confiteria> confiterias;

    @Getter @Setter
    private List<Confiteria> totalConfiteriaComprada;

    @Getter @Setter
    private double precioTotalCompra;

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @PostConstruct
    public void init() {
        try {
            funcion = adminTeatroServicio.obtenerFuncion(Integer.parseInt(funcionId));
            metodosPago = Arrays.asList(MetodoPago.values());
            confiterias = adminGeneralServicio.listarConfiterias();
            totalConfiteriaComprada = new ArrayList<>();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void restarUnidadesConfi(Confiteria confiteria) {
        /*if(!totalConfiteriaComprada.contains(confiteria)) {
            totalConfiteriaComprada.remove(confiteria);
        }*/
    }

    public void sumarUnidadesConfi(Confiteria confiteria) {
        /*totalConfiteriaComprada.add(confiteria);*/
    }

    public void realizarCompra() {

    }
}
