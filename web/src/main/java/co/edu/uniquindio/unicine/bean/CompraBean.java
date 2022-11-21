package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ViewScoped
public class CompraBean implements Serializable {

    // Parametros que llegan por link o de otro Bean
    @Value("#{param['funcion_id']}")
    private String funcionId;

    @Value("#{param['fecha_sel']}")
    private String fechaString;

    @Value(value = "#{seguridadBean.cliente}")
    private Cliente clienteSesion;

    @Getter @Setter
    private Funcion funcion;

    @Getter @Setter
    private Cliente cliente;

    @Getter @Setter
    private Integer codigoCupon;

    @Getter @Setter
    private DistribucionSillas distribucion;

    @Getter @Setter
    private ArrayList<Integer> filas, columnas;

    @Getter @Setter
    private MetodoPago metodoPagoSeleccionado;

    @Getter @Setter
    private List<MetodoPago> metodosPago;

    @Getter @Setter
    private List<Confiteria> confiterias;

    @Getter @Setter
    private List<ConfiteriaCompra> confiteriaCliente;

    @Getter @Setter
    private List<Boleta> boletasCliente;

    @Getter @Setter
    private LocalDate fechaSeleccionada;

    @Getter @Setter
    private double precioTotalCompra;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;


    @PostConstruct
    public void init() {
        try {
            metodosPago = Arrays.asList(MetodoPago.values());
            confiterias = adminGeneralServicio.listarConfiterias();
            confiteriaCliente = new ArrayList<>();
            boletasCliente = new ArrayList<>();
            filas = new ArrayList<>();
            columnas = new ArrayList<>();
            precioTotalCompra = 0f;

            confiterias.forEach(c -> {
                confiteriaCliente.add( ConfiteriaCompra.builder().confiteria(c).unidades(0).build() );
            });

            try {
                if (clienteSesion != null)  {
                    cliente = clienteSesion;
                    if (funcionId != null && !funcionId.isEmpty()) {
                        funcion = adminTeatroServicio.obtenerFuncion(Integer.parseInt(funcionId));
                        fechaSeleccionada = LocalDate.parse(fechaString);
                        crearDistribucionSala();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: Reorganizar el metodo para que se añada una boleta (o silla) si no hay otra con la misma fila y col,
    //       si la hay entonces elimenela
    public void selecionarSilla(Integer fila, Integer col) {
        for(Boleta b : boletasCliente) {
            if (!b.getFila().equals(fila.toString()) && !b.getColumna().equals(col.toString()) ) {
                boletasCliente.add(Boleta.builder().tipo(TipoSilla.ESTANDAR).fila(""+fila).columna(""+col).build());
                break;
            }
        }
        boletasCliente.add(Boleta.builder().tipo(TipoSilla.ESTANDAR).fila(""+fila).columna(""+col).build());
    }

    public boolean buscarSilla(Integer fila, Integer col) {
        return filas.contains(fila) && columnas.contains(col) ? true : false;
    }

    // TODO: Hacer el metodo para que se le sume a la confiteria las unidades
    public void restarUnidadesConfi(Confiteria confiteria) {
        /*if(!totalConfiteriaComprada.contains(confiteria)) {
            totalConfiteriaComprada.remove(confiteria);
        }*/
    }

    // TODO: Hacer el metodo para que se le reste a la confiteria las unidades
    public void sumarUnidadesConfi(Confiteria confiteria) {

    }

    public void crearDistribucionSala() {
        distribucion = funcion.getSala().getDistribucionSillas();
        String esquema = distribucion.getRutaEsquema();

        for (int i=0; i<distribucion.getFilas(); i++) {
            filas.add(i);
        }

        for (int i=0; i<distribucion.getColumnas(); i++) {
            columnas.add(i);
        }
    }

    public String realizarCompra() {
        if (clienteSesion != null) {
            if (!boletasCliente.isEmpty() && fechaSeleccionada != null) {
                try {
                    // Como la lista confiteriaCliente se inicializa con todas las confiterias que existen, se deben
                    // obtener solo las confiterias que a las que se halla asignado una cantidad de unidades (el usuario
                    // suma y resta la cantidad que quiere de algo en 'proceso_compra.xhtml'), por lo que en la compra
                    // solo se va a almacenar la lista de las confiterias que tengan unidades mayores a cero, con ayuda de .filter()
                    List<ConfiteriaCompra> listaConfi = confiteriaCliente.stream().filter(c -> c.getUnidades() > 0).collect(Collectors.toList());

                    Compra datosCompra = Compra.builder()
                            .cliente(cliente)
                            .fechaFuncionCompra(fechaSeleccionada)
                            .funcion(funcion)
                            .metodo_pago(metodoPagoSeleccionado)
                            .cupon(clienteServicio.obtenerCuponCliente(cliente.getCedula(), codigoCupon))
                            .build();
                    Compra compra = clienteServicio.realizarCompra(datosCompra, boletasCliente, listaConfi);

                    if (compra != null) {
                        FacesMessage fm;

                        if (compra.getCupon() != null) {
                            fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Se ha realizado la compra exitosamente!! Se le ha enviado un correo con los detalles de la compra y se ha redimido el cupon!");
                        }
                        else{
                            fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Se ha realizado la compra exitosamente!!.Se le ha enviado un correo con los detalles de la compra");
                        }

                        FacesContext.getCurrentInstance().addMessage("msg_bean", fm);
                        Thread.sleep(2000);
                        return "/cliente/detalle_compra.xhtml?faces-redirect=true&amp;compra_id="+compra.getId();
                    }
                }catch (Exception e) {
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
                    FacesContext.getCurrentInstance().addMessage("msg_bean", fm);
                }

            }
            else {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe escoger al menos una silla");
                FacesContext.getCurrentInstance().addMessage("msg_bean", fm);
            }
        }

        return "";
    }


}
