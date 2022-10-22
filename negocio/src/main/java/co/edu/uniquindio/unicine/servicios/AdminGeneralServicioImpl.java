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
    private final ConfiteriaRepo confiteriaRepo;

    public AdminGeneralServicioImpl(AdminGeneralRepo adminGeneralRepo, CiudadRepo ciudadRepo, TeatroRepo teatroRepo, CuponRepo cuponRepo, EventoRepo eventoRepo, PeliculaRepo peliculaRepo, ConfiteriaRepo confiteriaRepo) {
        this.adminGeneralRepo = adminGeneralRepo;
        this.ciudadRepo = ciudadRepo;
        this.teatroRepo = teatroRepo;
        this.cuponRepo = cuponRepo;
        this.eventoRepo = eventoRepo;
        this.peliculaRepo = peliculaRepo;
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
        return null;
    }

    @Override
    public Teatro crearTeatro(Teatro teatro) {
        return null;
    }

    @Override
    public Cupon crearCupon(Cupon cupon) {
        return null;
    }

    @Override
    public Evento crearEvento(Evento evento) {
        return null;
    }

    @Override
    public Pelicula crearPelicula(Pelicula pelicula) {
        return null;
    }

    @Override
    public Confiteria crearConfiteria(Confiteria confiteria) {
        return null;
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
    public Evento actualizarEvento(Evento evento) {
        return null;
    }

    @Override
    public Pelicula actualizarPelicula(Pelicula pelicula) {
        return null;
    }

    @Override
    public Confiteria actualizarConfiteria(Confiteria confiteria) {
        return null;
    }


    // Eliminar
    @Override
    public void eliminarCiudad(Integer idCiudad) {

    }

    @Override
    public void eliminarTeatro(Integer idTeatro) {

    }

    @Override
    public void eliminarCupon(Integer idCupon) {

    }

    @Override
    public void eliminarEvento(Integer idEvento) {

    }

    @Override
    public void eliminarPelicula(Integer idPelicula) {

    }

    @Override
    public void eliminarConfiteria(Integer idConfiteria) {

    }


    // Listar
    @Override
    public List<Ciudad> listarCiudades() {
        return null;
    }

    @Override
    public List<Teatro> listarTeatros() {
        return null;
    }

    @Override
    public List<Cupon> listarCupones() {
        return null;
    }

    @Override
    public List<Evento> listarEventos() {
        return null;
    }

    @Override
    public List<Pelicula> listarPeliculas() {
        return null;
    }

    @Override
    public List<Confiteria> listarConfiterias() {
        return null;
    }

    @Override
    public List<Compra> listarCompras() {
        return null;
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
            throw new Exception("El cupon ["+ idCupon+ "] no existe");
        }

        return cupon.get();
    }

    @Override
    public Evento obtenerEvento(Integer evento) {
        return null;
    }

    @Override
    public Pelicula obtenerPelicula(Integer idPelicula) {
        return null;
    }

    @Override
    public Confiteria obtenerConfiteria(Integer idConfiteria) {
        return null;
    }
}
