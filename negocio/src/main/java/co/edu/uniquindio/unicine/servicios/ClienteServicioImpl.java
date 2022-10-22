package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.repo.ClienteRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicioImpl implements ClienteServicio{

    private ClienteRepo clienteRepo;

    // Con esto instanciamos el repositorio, sin usar el @Autowired
    public ClienteServicioImpl(ClienteRepo clienteRepo) {
        this.clienteRepo = clienteRepo;
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
        return null;
    }

    @Override
    public Compra realizarCompra(Compra compra) throws Exception {
        return null;
    }

    @Override
    public boolean redimirCupon(Integer idCupon) throws Exception {
        return false;
    }

    @Override
    public List<Pelicula> buscarPelicula(String nombre) {
        return null;
    }

    @Override
    public Cliente cambiarContrasenia(Integer idCliente, String contraseniaVieja, String contraseniaNueva) {
        return null;
    }
}
