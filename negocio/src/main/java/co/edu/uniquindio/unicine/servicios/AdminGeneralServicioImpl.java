package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminGeneralServicioImpl implements AdminGeneralServicio {

    private final AdminGeneralRepo adminGeneralRepo;
    private final CiudadRepo ciudadRepo;
    private final TeatroRepo teatroRepo;
    private final CuponRepo cuponRepo;
    private final EventoRepo eventoRepo;
    private final PeliculaRepo peliculaRepo;
    private final GeneroRepo generoRepo;
    private final ConfiteriaRepo confiteriaRepo;

    public AdminGeneralServicioImpl(AdminGeneralRepo adminGeneralRepo, CiudadRepo ciudadRepo, TeatroRepo teatroRepo, CuponRepo cuponRepo, EventoRepo eventoRepo, PeliculaRepo peliculaRepo, GeneroRepo generoRepo, ConfiteriaRepo confiteriaRepo) {
        this.adminGeneralRepo = adminGeneralRepo;
        this.ciudadRepo = ciudadRepo;
        this.teatroRepo = teatroRepo;
        this.cuponRepo = cuponRepo;
        this.eventoRepo = eventoRepo;
        this.peliculaRepo = peliculaRepo;
        this.generoRepo = generoRepo;
        this.confiteriaRepo = confiteriaRepo;
    }

    @Override
    public AdminGeneral login(String email, String contrasenia) throws Exception {
        AdminGeneral adminGeneral = adminGeneralRepo.comprobarAutenticacion(email, contrasenia);

        if(adminGeneral == null) {
            throw new Exception("Los datos son incorrectos");
        }

        return adminGeneral;
    }


    // Crear
    @Override
    public Ciudad crearCiudad(Ciudad ciudad) {
        // Se puede dejar sin Exception, porque pueden existir varias ciudades con el mismo nombre
        return ciudadRepo.save(ciudad);
    }

    @Override
    public Teatro crearTeatro(Teatro teatro) throws Exception {
        boolean teatroExiste = esTeatroExistente(teatro);

        if(teatroExiste) {
            throw new Exception("El teatro ya existe");
        }

        return teatroRepo.save(teatro);
    }

    private boolean esTeatroExistente(Teatro teatro) {
        return teatroRepo.verificarExistencia(teatro.getNombre(),
                                              teatro.getDireccion(),
                                              teatro.getCiudad().getId())
                         .orElse(null) != null;
    }

    @Override
    public Cupon crearCupon(Cupon cupon) throws Exception {
        boolean cuponExiste = esCuponExistente(cupon);

        if(cuponExiste) {
            throw new Exception("El cupon ya existe");
        }

        return cuponRepo.save(cupon);
    }

    private boolean esCuponExistente(Cupon cupon) {
        return cuponRepo.verificarExistencia(cupon.getValor_descuento(),
                                             cupon.getFecha_vencimiento(),
                                             cupon.getCriterio())
                        .orElse(null) != null;
    }

    @Override
    public Evento crearEvento(Evento evento) throws Exception {
        boolean eventoExiste = esEventoExistente(evento);

        if(eventoExiste) {
            throw new Exception("El evento ya existe");
        }

        return eventoRepo.save(evento);
    }

    private boolean esEventoExistente(Evento e) {
        return eventoRepo.verificarExistencia(e.getFecha(),
                                              e.getHora_inicio(),
                                              e.getHora_fin(),
                                              e.getTeatro().getId())
                .orElse(null) != null;
    }

    @Override
    public Pelicula crearPelicula(Pelicula pelicula) throws Exception {
        boolean peliculaExiste = esPeliculaExistente(pelicula.getNombre());

        if (peliculaExiste) {
            throw new Exception("La pelicula ya existe");
        }

        return peliculaRepo.save(pelicula);
    }

    private boolean esPeliculaExistente(String nombre) {
        return peliculaRepo.findByNombre(nombre).orElse(null) != null;
    }

    @Override
    public Genero crearGenero(Genero genero) throws Exception {
        boolean generoExiste = esGeneroExistente(genero.getNombre());

        if(generoExiste) {
            throw new Exception("El genero ya existe");
        }

        return generoRepo.save(genero);
    }

    private boolean esGeneroExistente(String nombre) {
        return generoRepo.findByNombre(nombre).orElse(null) != null;
    }

    @Override
    public Confiteria crearConfiteria(Confiteria confiteria) throws Exception {
        boolean confiteriaExiste = esConfiteriaExistente(confiteria.getProducto());

        if(confiteriaExiste) {
            throw new Exception("La confiteria ya existe");
        }

        return confiteriaRepo.save(confiteria);
    }

    private boolean esConfiteriaExistente(String producto) {
        return confiteriaRepo.findByProducto(producto).orElse(null) != null;
    }


    // Actualizar
    @Override
    public Ciudad actualizarCiudad(Ciudad ciudad) throws Exception {
        Optional<Ciudad> guardada = ciudadRepo.findById(ciudad.getId());

        if(guardada.isEmpty()) {
            throw new Exception("La ciudad no existe");
        }

        return ciudadRepo.save(ciudad);
    }

    @Override
    public Teatro actualizarTeatro(Teatro teatro) throws Exception {
        Optional<Teatro> guardado = teatroRepo.findById(teatro.getId());

        if(guardado.isEmpty()) {
            throw new Exception("El teatro no existe");
        }

        return teatroRepo.save(teatro);
    }

    @Override
    public Cupon actualizarCupon(Cupon cupon) throws Exception {
        Optional<Cupon> guardado = cuponRepo.findById(cupon.getId());

        if(guardado.isEmpty()) {
            throw new Exception("El cupon no existe");
        }

        return cuponRepo.save(cupon);
    }

    @Override
    public Evento actualizarEvento(Evento evento) throws Exception {
        Optional<Evento> guardado = eventoRepo.findById(evento.getId());

        if (guardado.isEmpty()) {
            throw new Exception("El evento no existe");
        }

        return eventoRepo.save(evento);
    }

    @Override
    public Pelicula actualizarPelicula(Pelicula pelicula) throws Exception {
        Optional<Pelicula> guardada = peliculaRepo.findById(pelicula.getId());

        if(guardada.isEmpty()) {
            throw new Exception("La pelicula no existe");
        }

        return peliculaRepo.save(pelicula);
    }

    @Override
    public Genero actualizarGenero(Genero genero) throws Exception {
        Optional<Genero> guardado = generoRepo.findById(genero.getId());

        if(guardado.isEmpty()) {
            throw new Exception("El genero no existe");
        }

        return generoRepo.save(genero);
    }

    @Override
    public Confiteria actualizarConfiteria(Confiteria confiteria) throws Exception {
        Optional<Confiteria> guardada = confiteriaRepo.findById(confiteria.getId());

        if(guardada.isEmpty()) {
            throw new Exception("La confiteria no existe");
        }

        return confiteriaRepo.save(confiteria);
    }


    // Eliminar
    @Override
    public void eliminarCiudad(Integer idCiudad) throws Exception {
        Optional<Ciudad> ciudad = ciudadRepo.findById(idCiudad);

        if(ciudad.isEmpty()) {
            throw new Exception("La ciudad [id:"+idCiudad+ "] no existe");
        }

        ciudadRepo.delete(ciudad.get());
    }

    @Override
    public void eliminarTeatro(Integer idTeatro) throws Exception {
        Optional<Teatro> teatro = teatroRepo.findById(idTeatro);

        if(teatro.isEmpty()) {
            throw new Exception("El teatro [id:"+ idTeatro+ "] no existe");
        }

        teatroRepo.delete(teatro.get());
    }

    @Override
    public void eliminarCupon(Integer idCupon) throws Exception {
        Optional<Cupon> cupon = cuponRepo.findById(idCupon);

        if(cupon.isEmpty()) {
            throw new Exception("El cupon [id:"+ idCupon+ "] no existe");
        }

        cuponRepo.delete(cupon.get());
    }

    @Override
    public void eliminarEvento(Integer idEvento) throws Exception {
        Optional<Evento> evento = eventoRepo.findById(idEvento);

        if(evento.isEmpty()) {
            throw new Exception("El evento [id:"+ idEvento+ "] no existe");
        }

        eventoRepo.delete(evento.get());
    }

    @Override
    public void eliminarPelicula(Integer idPelicula) throws Exception {
        Optional<Pelicula> pelicula = peliculaRepo.findById(idPelicula);

        if(pelicula.isEmpty()) {
            throw new Exception("La pelicula [id:"+ idPelicula+ "] no existe");
        }

        peliculaRepo.delete(pelicula.get());
    }

    @Override
    public void eliminarGenero(Integer idGenero) throws Exception {
        Optional<Genero> genero = generoRepo.findById(idGenero);

        if(genero.isEmpty()) {
            throw new Exception("El genero [id:"+ idGenero+ "] no existe");
        }

        generoRepo.delete(genero.get());
    }

    @Override
    public void eliminarConfiteria(Integer idConfiteria) throws Exception {
        Optional<Confiteria> confiteria =  confiteriaRepo.findById(idConfiteria);

        if(confiteria.isEmpty()) {
            throw new Exception("La confiteria [id:"+ idConfiteria+ "] no existe");
        }

        confiteriaRepo.delete(confiteria.get());
    }


    // Listar
    @Override
    public List<Ciudad> listarCiudades() {
        return ciudadRepo.findAll();
    }

    @Override
    public List<Teatro> listarTeatros() {
        return teatroRepo.findAll();
    }

    @Override
    public List<Cupon> listarCupones() {
        return cuponRepo.findAll();
    }

    @Override
    public List<Evento> listarEventos() {
        return eventoRepo.findAll();
    }

    @Override
    public List<Pelicula> listarPeliculas() {
        return peliculaRepo.findAll();
    }

    @Override
    public List<Genero> listarGeneros() {
        return generoRepo.findAll();
    }

    @Override
    public List<Confiteria> listarConfiterias() {
        return confiteriaRepo.findAll();
    }


    // Obtener
    @Override
    public Ciudad obtenerCiudad(Integer idCiudad) throws Exception {
        Optional<Ciudad> ciudad = ciudadRepo.findById(idCiudad);

        if(ciudad.isEmpty()) {
            throw new Exception("La ciudad [id: "+ idCiudad+ "] no existe");
        }

        return ciudad.get();
    }

    @Override
    public Teatro obtenerTeatro(Integer idTeatro) throws Exception {
        Optional<Teatro> teatro = teatroRepo.findById(idTeatro);

        if(teatro.isEmpty()) {
            throw new Exception("El teatro [id:"+ idTeatro+ "] no existe");
        }

        return teatro.get();
    }

    @Override
    public Cupon obtenerCupon(Integer idCupon) throws Exception {
        Optional<Cupon> cupon = cuponRepo.findById(idCupon);

        if(cupon.isEmpty()) {
            throw new Exception("El cupon [id:"+ idCupon+ "] no existe");
        }

        return cupon.get();
    }

    @Override
    public Evento obtenerEvento(Integer idEvento) throws Exception {
        Optional<Evento> evento = eventoRepo.findById(idEvento);

        if(evento.isEmpty()) {
            throw new Exception("El evento [id: "+ idEvento+ "] no existe");
        }

        return evento.get();
    }

    @Override
    public Pelicula obtenerPelicula(Integer idPelicula) throws Exception {
        Optional<Pelicula> pelicula = peliculaRepo.findById(idPelicula);

        if(pelicula.isEmpty()) {
            throw new Exception("La pelicula [id:"+ idPelicula+ "] no existe");
        }

        return pelicula.get();
    }

    @Override
    public Genero obtenerGenero(Integer idGenero) throws Exception {
        Optional<Genero> genero = generoRepo.findById(idGenero);

        if(genero.isEmpty()) {
            throw new Exception("El genero [id:"+ idGenero+ "] no existe");
        }

        return genero.get();
    }

    @Override
    public Confiteria obtenerConfiteria(Integer idConfiteria) throws Exception {
        Optional<Confiteria> confiteria = confiteriaRepo.findById(idConfiteria);

        if(confiteria.isEmpty()) {
            throw new Exception("La confiteria [id:"+ idConfiteria+ "] no existe");
        }

        return confiteria.get();
    }
}
