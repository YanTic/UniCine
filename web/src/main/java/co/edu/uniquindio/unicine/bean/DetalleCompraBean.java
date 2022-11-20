package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

@Component
@ViewScoped
public class DetalleCompraBean implements Serializable {

    @Autowired
    private ClienteServicio clienteServicio;

    @Value("#{param['compra_id']}")
    private String idCompra;

    @Getter @Setter
    private Compra compra;

    @PostConstruct
    public void init() {
        if (idCompra != null && !idCompra.isEmpty()) {
            try {
                compra = clienteServicio.obtenerCompra(Integer.parseInt(idCompra));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
