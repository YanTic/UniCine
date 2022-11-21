package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.dto.PeliculaFuncionDTO;
import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        Cliente cliente = clienteRepo.findByEmail(email).orElse(null);

        if(cliente == null) {
            throw new Exception("El correo no existe");
        }
        if(cliente.getEstado() == false) {
            throw new Exception("La cuenta del clientes está desactivada [Debe activarla]");
        }

        StrongPasswordEncryptor spe = new StrongPasswordEncryptor();
        if (!spe.checkPassword(contrasenia, cliente.getContrasenia())) {
            throw new Exception("La contraseña es incorrecta");
        }

        return cliente;
    }

    @Override
    public Cliente registrarCliente(Cliente cliente) throws Exception {
        boolean correoExiste = esEmailRepetido(cliente.getEmail());

        if(correoExiste){
            throw new Exception("El correo ya está en uso");
        }

        StrongPasswordEncryptor spe = new StrongPasswordEncryptor();
        cliente.setContrasenia(spe.encryptPassword(cliente.getContrasenia()));
        cliente.setEstado(false);
        cliente.setImagen_perfil("");

        Cliente registro = clienteRepo.save(cliente);

        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword("raton");

        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("America/Bogota"));

        String param1 = textEncryptor.encrypt(registro.getEmail());
        String param2 = textEncryptor.encrypt(""+zdt.toInstant().toEpochMilli());
        String link = "http://localhost:8085/activar_cuenta.xhtml?p1="+param1+"&amp;p2="+param2;

        emailServicio.enviarEmail("Bienvenido a Unicine | Registro Exitoso!"
                ,"<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "    <head>\n" +
                        "        <title>Registro</title>\n" +
                        "    </head>\n" +
                        "    <body>\n" +
                        "        <p><strong>Hola, "+ cliente.getNombre()+ " Bienvenido a Unicine!</strong></p>\n" +
                        "        <p>Solo falta un paso más, ahora debes activar tu cuenta para verificar tu identidad. Haz click en la imagen: </p>\n" +
                        "        <a href=\""+link+"\" ><img src=\"\" alt=\"Activar Cuenta\"></a>\n" +
                        "        <p>Si no puedes ingresar usa este link: <a href=\""+link+"\">link</a> </p>\n" +
                        "    </body>\n" +
                        "</html>"
                ,registro.getEmail());
        System.out.println("Se ha enviado el correo");
        return registro;
    }

    @Override
    public void activarCuenta(String correo, String fecha) throws Exception {

        correo = correo.replaceAll(" ", "+");
        fecha = fecha.replaceAll(" ", "+");

        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword("raton");

        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("America/Bogota"));

        String correoDes = textEncryptor.decrypt(correo);
        String fechaDes = textEncryptor.decrypt(fecha);

        Cliente guardado = clienteRepo.findByEmail(correoDes).orElse(null);

        if (guardado == null) {
            throw new Exception("El cliente no existe, no se ha registrado");
        }

        // Ahora se le asigna el un cupon por registro al cliente
        CuponCliente cuponRegistro = asignarCuponACliente(guardado.getCedula(), 1);
        guardado.setEstado(true);
        guardado.getCupones().add(cuponRegistro);

        emailServicio.enviarEmail("Cuenta activada!"
                , "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "    <head>\n" +
                        "        <title>Cuenta Activada</title>\n" +
                        "    </head>\n" +
                        " <style></style>"+
                        "    <body>\n" +
                        "        <p>Su cuenta ha sido activada exitosamente</p>\n" +
                        "        <p>Unicine le ha hecho un regalo por su registro: </p>\n" +
                        "        <a href=#><img src=\"\" alt=\"Cupon Registro\"></a>\n" +
                        "    </body>\n" +
                        "</html>"
                ,guardado.getEmail());

        clienteRepo.save(guardado);
    }

    private boolean esEmailRepetido(String email) {
        return clienteRepo.findByEmail(email).orElse(null) != null ;
    }

    private boolean esCedulaRepetida(Integer cedula) {
        return clienteRepo.findById(cedula).orElse(null) != null;
    }

    private CuponCliente asignarCuponACliente(Integer idCliente, Integer idCupon) throws Exception{
        Optional<CuponCliente> cuponAsignado = cuponClienteRepo.obtenerPorCuponYCliente(idCliente, idCupon);

        if (!cuponAsignado.isEmpty()) {
            throw new Exception("Este cupon [id:"+idCupon+"] ya ha sido asignado a este cliente [id:"+idCliente+"]");
        }

        CuponCliente nuevoCuponCliente = CuponCliente.builder()
                .cliente(obtenerCliente(idCliente))
                .cupon(clienteRepo.obtenerCupon(idCupon))
                .estado(false)
                .build();

        return cuponClienteRepo.save(nuevoCuponCliente);
    }

    @Override
    public Cliente actualizarCliente(Integer idCliente, Cliente clienteActualizado) throws Exception {
        Optional<Cliente> guardado = clienteRepo.findById(idCliente);
        boolean emailDisponible = esEmailRepetido(clienteActualizado.getEmail());
        boolean cedulaRepetida = esCedulaRepetida(clienteActualizado.getCedula());

        if (guardado.isEmpty()) {
            throw new Exception("El cliente no existe");
        }
        if (emailDisponible) {
            throw new Exception("Otro cliente ya tiene este email");
        }
        if (cedulaRepetida) {
            throw new Exception("Otro cliente ya tiene esta cedula");
        }

        guardado.get().setCedula(clienteActualizado.getCedula());
        guardado.get().setNombre(clienteActualizado.getNombre());
        guardado.get().setTelefonos(clienteActualizado.getTelefonos());
        guardado.get().setImagen_perfil(clienteActualizado.getImagen_perfil());
        guardado.get().setContrasenia(clienteActualizado.getContrasenia());

        return clienteRepo.save(guardado.get());
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

        if (cliente.isEmpty()) {
            throw new Exception("El cliente [id:"+ idCliente+ "] no existe");
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


        boolean cuponRedimible;
        if (compra.getCupon() == null) {
            cuponRedimible = false;
        }
        else {
            cuponRedimible = esCuponRedimible(idCliente, compra.getCupon().getCupon().getId());

            if (cuponRedimible) {
                redimirCupon(idCliente, compra.getCupon().getCupon().getId());
            }
            else{
                // No tiro una excepcion, porque la compra se puede realizar sin cupon igualmente
                System.out.println("El cupon no está disponible [Cupon ya ha sido redimido o no Pertenece a este cliente]");
                // Si no puede ser redimido la compra no puede tener el cupon
                compra.setCupon(null);
            }
        }


        compra.setValorTotal((float)1);
        compraRepo.save(compra); // Este save() es para obtener el id de la compra y asignarselo a las boletas y confiterias
        System.out.println("Compra sin boletas, Confiterias, ni Valor_Total: "+ compra);

        // Ahora que las boletas con las que se va a realizar la compra son disponibles les agrego el id de la nueva compra
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

        float precioTotalCompra = 0;
        if (cuponRedimible)
            precioTotalCompra = precioBoletas + precioConfiteria - ((precioBoletas + precioConfiteria) * compra.getCupon().getCupon().getValor_descuento());
        else
            precioTotalCompra = precioBoletas + precioConfiteria;

        compra.setValorTotal(precioTotalCompra);

        System.out.println("precioBoletas: "+ precioBoletas);
        System.out.println("precioConfiteria: "+ precioConfiteria);
        System.out.println("precioTotalCompra: "+ precioTotalCompra);

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
            throw new Exception("El cupon no está disponible [Cupon ya ha sido redimido, no Pertenece a este cliente o está vencido]");
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
    public List<Pelicula> listarPeliculasPorEstadoCiudad(EstadoPelicula estadoPelicula, Integer idCiudad) throws Exception {
        List<Pelicula> peliculas = clienteRepo.obtenerPeliculasPorEstadoCiudad(estadoPelicula, idCiudad);

        if (peliculas.isEmpty()) {
            throw new Exception("No existen peliculas con el estado ["+estadoPelicula+"] y ciudad [id:"+idCiudad+"]");
        }

        return peliculas;
    }

    @Override
    public List<Pelicula> listarPeliculasPorEstado(EstadoPelicula estadoPelicula) throws Exception {
        List<Pelicula> peliculas = clienteRepo.obtenerPeliculasPorEstado(estadoPelicula);

        if(peliculas.isEmpty()) {
            throw new Exception("No existen peliculas con el estado ["+estadoPelicula+"]");
        }

        return peliculas;
    }

    @Override
    public List<PeliculaFuncionDTO> buscarPeliculaFuncion(String nombre) {
        return clienteRepo.obtenerPeliculaFuncion(nombre);
    }

    @Override
    public Compra obtenerCompra(Integer idCompra) throws Exception {
        return compraRepo.findById(idCompra).orElseThrow(()->new Exception("No se encontro la compra"));
    }


    // TODO: Hacer la encriptacion al igual que activar cuenta, crear un .xhtml y se puede usar el bien de gestionCuenta o crear otro tambien
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

//        Tambien enviar un correo cuando el usuario quiera cambiar la contraseña y luego este: (diciendo que se cambió correctamente)
//        emailServicio.enviarEmail("Cambio de Contraseña Existoso!","","");

        Cliente cliente = obtenerCliente(idCliente);
        cliente.setContrasenia(contraseniaNueva);
        return clienteRepo.save(cliente);
    }

    private boolean esContraseniaCorrecta(Integer idCliente, String contrasenia) {
        return clienteRepo.verificarContrasenia(idCliente, contrasenia).orElse(null) != null;
    }

}
