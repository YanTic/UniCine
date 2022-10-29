package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicioImpl implements ClienteServicio{

    private final ClienteRepo clienteRepo;
    private final CuponClienteRepo cuponClienteRepo;
    private final CompraRepo compraRepo;
    private final BoletaRepo boletaRepo;
    private final ConfiteriaRepo confiteriaRepo;
    private final ConfiteriaCompraRepo confiteriaCompraRepo;
    private final EmailServicio emailServicio;

    // Con esto instanciamos el repositorio, sin usar el @Autowired
    public ClienteServicioImpl(ClienteRepo clienteRepo, CuponClienteRepo cuponClienteRepo, CompraRepo compraRepo, BoletaRepo boletaRepo, ConfiteriaRepo confiteriaRepo, ConfiteriaCompraRepo confiteriaCompraRepo, EmailServicio emailServicio) {
        this.clienteRepo = clienteRepo;
        this.cuponClienteRepo = cuponClienteRepo;
        this.compraRepo = compraRepo;
        this.boletaRepo = boletaRepo;
        this.confiteriaRepo = confiteriaRepo;
        this.confiteriaCompraRepo = confiteriaCompraRepo;
        this.emailServicio = emailServicio;
    }

    @Override
    public Cliente obtenerCliente(Integer cedula) throws Exception {
        Optional<Cliente> cliente = clienteRepo.findById(cedula);

        if(cliente.isEmpty()){
            throw new Exception("El cliente no existe");
        }

        return cliente.get();
    }

    @Override
    public Cliente login(String email, String contrasenia) throws Exception {
        Cliente cliente = clienteRepo.comprobarAutenticacion(email, contrasenia);

        if(cliente == null) {
            throw new Exception("Los datos son incorrectos");
        }

        return cliente;
    }

    @Override
    public Cliente registrarCliente(Cliente cliente) throws Exception {
        boolean correoExiste = esRepetido(cliente.getEmail());

        if(correoExiste){
            throw new Exception("El correo ya está en uso");
        }

        //emailServicio.enviarEmail("Bienvenido a Unicine | Activar Cuenta!", "Para activar su cuenta haz click en la imagen, o ingresa al siguiente link",)

        return clienteRepo.save(cliente);
    }

    public Cliente activarCuenta() {
        //emailServicio.enviarEmail("Cuenta activada!", "Gracias por activar su cuenta. Unicine le ha hecho un regalo:")

                // Y PUES AHÍ LE MANDA EL CUPON AL CLIENTE (MOSTRANDO LA IMG DEL CUPON), ASIGNADOLE EL CUPON EN LA BD
        return null;
    }

    private boolean esRepetido(String email) {
        return clienteRepo.findByEmail(email).orElse(null) != null ;
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) throws Exception {
        Optional<Cliente> guardado = clienteRepo.findById(cliente.getCedula());

        if(guardado.isEmpty()) {
            throw new Exception("El cliente no existe");
        }

        return clienteRepo.save(cliente);
    }

    @Override
    public void eliminarCliente(Integer idCliente) throws Exception {
        Optional<Cliente> guardado = clienteRepo.findById(idCliente);

        if(guardado.isEmpty()) {
            throw new Exception("El cliente no existe");
        }

        clienteRepo.delete(guardado.get());
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepo.findAll();
    }

    @Override
    public List<Compra> listarHistorialCompras(Integer idCliente) throws Exception {
        boolean clienteExiste = esClienteExistente(idCliente);

        if(!clienteExiste){
            throw new Exception("El cliente [id:"+idCliente+ "] no existe");
        }

        return clienteRepo.listarHistorialCompras(idCliente);
    }

    boolean esClienteExistente(Integer idCliente) {
        return clienteRepo.findById(idCliente).orElse(null) != null;
    }

    @Override
    public Compra realizarCompra(Compra compra, List<Boleta> boletas, List<ConfiteriaCompra> confiteriaCompras) throws Exception {
        Integer idCliente = compra.getCliente().getCedula();
        Optional<Cliente> cliente = clienteRepo.findById(idCliente);

        boolean cuponRedimible;
        if (compra.getCupon() == null) {
            cuponRedimible = false;
        }
        else {
            cuponRedimible = esCuponRedimible(idCliente, compra.getCupon().getId());
            // Si no puede ser redimido la compra no puede tener el cupon
            if (!cuponRedimible)
                compra.setCupon(null);
        }

//        boolean compraClienteExistente = esCompraClienteExistente(idCliente, compra.getId());

        if (cliente.isEmpty()) {
            throw new Exception("El cliente [id:"+ idCliente+ "] no existe");
        }
        if (cuponRedimible) {
            redimirCupon(idCliente, compra.getId());
        }
        else{ // No tiro una excepcion, porque la compra se puede realizar sin cupon igualmente
            System.out.println("El cupon no está disponible [Cupon ya ha sido redimido o no Pertenece a este cliente]");
        }

        // Se Verifica la disponibilidad de cada boleta que se hace en la compra
        String boletasNoDisponibles = "";
        for (Boleta b : boletas) {
            boolean boletaDisponible = esBoletaDisponible(compra.getFuncion().getId(), b);

            if(!boletaDisponible) {
                boletasNoDisponibles += "Boleta [Fila:"+b.getFila()+", Columna:"+b.getColumna()+"]. ";
            }
        }

        if (!boletasNoDisponibles.isEmpty()) {
            throw new Exception("La compra no se puedo realizar. Boleta/s no disponible/s [Silla/s Ya Ocupada/s] \n"+ boletasNoDisponibles);
        }


        // Se Verifica la disponibilidad de cada confiteria que se hace en la compra
        String confiteriasNoDisponibles = "";
        for (ConfiteriaCompra c : confiteriaCompras) {
            boolean confiteriaExistente = esConfiteriaExistente(c.getConfiteria().getId());

            if(!confiteriaExistente) {
                boletasNoDisponibles += "Confiteria [id:"+c.getConfiteria().getId()+"] no existe. ";
            }
        }

        if (!confiteriasNoDisponibles.isEmpty()) {
            throw new Exception("La compra no se puedo realizar. Confiteria/s no existente/s\n"+ confiteriasNoDisponibles);
        }

        compra.setValorTotal((float)1);
        compraRepo.save(compra); // Este save() es para obtener el id de la compra y asignarselo a las boletas y confiterias
        System.out.println("Compra sin boletas, Confiterias, ni Valor_Total: "+ compra);
        // Ahora que las boletas con las que se va a realizar la compra son disponibles
        // les agrego el id de la nueva compra
        float precioBoletas = 0;
        for (Boleta b : boletas) {
            b.setCompra(compra);
            precioBoletas += b.getPrecio();
            boletaRepo.save(b);
        }

        float precioConfiteria = 0;
        for (ConfiteriaCompra c : confiteriaCompras) {
            c.setCompra(compra);
            precioConfiteria += c.getPrecio(); // Este precio internamente en la entidad se genera por el precio de la confiteria y las unidades
            confiteriaCompraRepo.save(c);
        }

        float precioTotalCompra = precioBoletas + precioBoletas;
        compra.setValorTotal(precioTotalCompra);


        // Se envia el correo con la compra
        //emailServicio.enviarEmail("Compra Realizada Exitosa!", "Usted ha comprado ",);


        return compraRepo.save(compra);
    }

    private boolean esConfiteriaExistente(Integer idConfiteria) {
        return confiteriaRepo.findById(idConfiteria).orElse(null) != null;
    }

    private boolean esBoletaDisponible(Integer idFuncion, Boleta boleta) {
        return boletaRepo.verificarDisponibilidad(idFuncion, boleta.getFila(), boleta.getColumna())
                .orElse(null) == null;
    }

    @Override
    public List<Boleta> listarBoletasCompra(Integer idCompra) {
        return boletaRepo.obtenerBoletas(idCompra);
    }


    @Override
    public boolean redimirCupon(Integer idCliente, Integer idCupon) throws Exception {
        CuponCliente cupon = obtenerCuponCliente(idCliente, idCupon);
        boolean cuponRedimible = esCuponRedimible(idCliente, idCupon);

        if(!cuponRedimible) {
            throw new Exception("El cupon no está disponible [Cupon ya ha sido redimido o no Pertenece a este cliente]");
        }


        cupon.setEstado(true); // Redimimos el cupon
        cuponClienteRepo.save(cupon); // Actualizamos el cupon
        System.out.println("cupon redimido");
        return true;
    }

    private boolean esCuponRedimible(Integer idCliente, Integer idCupon) {
        return clienteRepo.verificarDisponibilidadCupon(idCliente, idCupon).orElse(null) != null;
    }

    @Override
    public CuponCliente obtenerCuponCliente(Integer idCliente, Integer idCupon) throws Exception {
        Optional<CuponCliente> cupon = cuponClienteRepo.obtenerPorCuponYCliente(idCliente, idCupon);

        if(cupon.isEmpty()) {
            throw new Exception("El cupon del cliente [idCliente:" +idCliente +", idCupon:"+ idCupon+"] no existe, el cliente no tiene este cupon");
        }

        return cupon.get();
    }

    @Override
    public List<CuponCliente> listarCuponesCliente(Integer idCliente) {
        return cuponClienteRepo.obtenerCuponesCliente(idCliente);
    }

    @Override
    public List<ConfiteriaCompra> listarConfiteriasCompra(Integer idCompra) {
        return confiteriaCompraRepo.obtenerConfiteriasCompra(idCompra);
    }


    @Override
    public List<Pelicula> buscarPelicula(String nombre) {
        return clienteRepo.obtenerPelicula(nombre);
    }

    @Override
    public Cliente cambiarContrasenia(Integer idCliente, String contraseniaAnterior, String contraseniaNueva) throws Exception {
        boolean clienteExiste = esClienteExistente(idCliente);
        boolean contraseniaCorrecta = esContraseniaCorrecta(idCliente, contraseniaAnterior);

        if(!clienteExiste){
            throw new Exception("El cliente [id:"+ idCliente+ "] no existe");
        }
        if(!contraseniaCorrecta) {
            throw new Exception("La contraseña anterior es Incorrecta!");
        }

        Cliente cliente = obtenerCliente(idCliente);
        cliente.setContrasenia(contraseniaNueva);
        return clienteRepo.save(cliente);
    }

    private boolean esContraseniaCorrecta(Integer idCliente, String contrasenia) {
        return clienteRepo.verificarContrasenia(idCliente, contrasenia).orElse(null) != null;
    }

}
