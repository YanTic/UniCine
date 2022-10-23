package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.entidades.CuponCliente;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.repo.ClienteRepo;
import co.edu.uniquindio.unicine.repo.CuponClienteRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicioImpl implements ClienteServicio{

    private final ClienteRepo clienteRepo;
    private final CuponClienteRepo cuponClienteRepo;

    // Con esto instanciamos el repositorio, sin usar el @Autowired
    public ClienteServicioImpl(ClienteRepo clienteRepo, CuponClienteRepo cuponClienteRepo) {
        this.clienteRepo = clienteRepo;
        this.cuponClienteRepo = cuponClienteRepo;
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

        return clienteRepo.save(cliente);
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
    public List<Compra> listarHistorial(Integer idCliente) throws Exception {
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
    public Compra realizarCompra(Compra compra) throws Exception {
        return null;
    }

    @Override
    public boolean redimirCupon(Integer idCliente, Integer idCupon) throws Exception {
        boolean cuponRedimible = esCuponRedimible(idCliente, idCupon);

        if(!cuponRedimible) {
            throw new Exception("El cupon no está disponible [Cupon ya ha sido redimido]");
        }

        CuponCliente cupon = obtenerCuponCliente(idCupon);
        cupon.setEstado(true); // Redimimos el cupon
        cuponClienteRepo.save(cupon); // Actualizamos el cupon
        return true;
    }

    private boolean esCuponRedimible(Integer idCliente, Integer idCupon) {
        return clienteRepo.verificarDisponibilidadCupon(idCliente, idCupon).orElse(null) != null;
    }

    @Override
    public CuponCliente obtenerCuponCliente(Integer idCupon) throws Exception {
        Optional<CuponCliente> cupon = cuponClienteRepo.findById(idCupon);

        if(cupon.isEmpty()) {
            throw new Exception("El cupon del cliente [idCupon:"+ idCupon+"] no existe");
        }

        return cupon.get();
    }

    @Override
    public List<CuponCliente> listarCuponesCliente(Integer idCliente) {
        return cuponClienteRepo.obtenerCuponesCliente(idCliente);
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
