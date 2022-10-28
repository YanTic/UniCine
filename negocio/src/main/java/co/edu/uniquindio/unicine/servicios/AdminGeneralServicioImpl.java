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
            throw new Exception("El teatro ya existe [Otro teatro tiene el mismo nombre, direccion, ciudad]");
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
            throw new Exception("El cupon ya existe [Otro cupon tiene el mismo valor_descuento, fecha_vencimiento, criterio]");
        }

        return cuponRepo.save(cupon);
    }

    private boolean esCuponExistente(Cupon cupon) {
        return cuponRepo.verificarExistencia(cupon.getValor_descuento(),
                                             cupon.getFecha_vencimiento(),
                                             cupon.getDescripcion(),
                                             cupon.getCriterio())
                        .orElse(null) != null;
    }

    @Override
    public Evento crearEvento(Evento evento) throws Exception {
        boolean eventoDisponible = esEventoDisponible(evento);

        if(!eventoDisponible) {
            throw new Exception("El evento no es disponible [Otro evento tiene la misma fecha, hora_inicio, hora_fin, teatro]");
        }

        return eventoRepo.save(evento);
    }

    private boolean esEventoDisponible(Evento e) {
        return eventoRepo.verificarDisponibilidad(e.getFecha(),
                        e.getHora_inicio(),
                        e.getHora_fin(),
                        e.getTeatro().getId())
                .orElse(null) == null;
    }

    @Override
    public Pelicula crearPelicula(Pelicula pelicula) throws Exception {
        boolean peliculaDisponible = esPeliculaDisponible(pelicula.getNombre());

        if (peliculaDisponible) {
            throw new Exception("La pelicula ya existe, no disponible [Otra pelicula tiene el mismo nombre]");
        }

        return peliculaRepo.save(pelicula);
    }

    private boolean esPeliculaDisponible(String nombre) {
        return peliculaRepo.findByNombre(nombre).orElse(null) != null;
    }

    @Override
    public Genero crearGenero(Genero genero) throws Exception {
        boolean generoExiste = esGeneroExistente(genero);

        if(generoExiste) {
            throw new Exception("El genero ya existe [Otro genero tiene el mismo nombre]");
        }

        return generoRepo.save(genero);
    }

    private boolean esGeneroExistente(Genero genero) {
        return generoRepo.verificarExistencia(genero.getNombre()).orElse(null) != null;
    }

    @Override
    public Confiteria crearConfiteria(Confiteria confiteria) throws Exception {
        boolean confiteriaDisponible = esConfiteriaDisponible(confiteria.getProducto());

        if(!confiteriaDisponible) {
            throw new Exception("La confiteria ya existe [Otra confiteria tiene el mismo producto]");
        }

        return confiteriaRepo.save(confiteria);
    }

    private boolean esConfiteriaDisponible(String producto) {
        return confiteriaRepo.findByProducto(producto).orElse(null) == null;
    }


    // Actualizar
    @Override
    public Ciudad actualizarCiudad(Ciudad ciudad) throws Exception {
        Optional<Ciudad> guardada = ciudadRepo.findById(ciudad.getId());

        // Aquí no se usan restrincciones para verificar si otra ciudad tiene el mismo nombre
        // porque pueden existir ciudades con el mismo nombre
        if(guardada.isEmpty()) {
            throw new Exception("La ciudad no existe");
        }

        return ciudadRepo.save(ciudad);
    }

    @Override
    public Teatro actualizarTeatro(Integer idTeatro, Teatro teatroActualizado) throws Exception {
        Optional<Teatro> guardado = teatroRepo.findById(idTeatro);
        boolean teatroExistente = esTeatroExistente(teatroActualizado);

        if(guardado.isEmpty()) {
            throw new Exception("El teatro no existe");
        }
        if(teatroExistente) {
            throw new Exception("El teatro ya existe [Otro teatro tiene el mismo nombre, direccion, ciudad]");
        }

        guardado.get().setNombre(teatroActualizado.getNombre());
        guardado.get().setDireccion(teatroActualizado.getDireccion());
        guardado.get().setCiudad(teatroActualizado.getCiudad());

        return teatroRepo.save(guardado.get());
    }

    @Override
    public Cupon actualizarCupon(Integer idCupon, Cupon cuponActualizado) throws Exception {
        Optional<Cupon> guardado = cuponRepo.findById(idCupon);
        boolean cuponExistente = esCuponExistente(cuponActualizado);

        if(guardado.isEmpty()) {
            throw new Exception("El cupon no existe");
        }
        if(cuponExistente) {
            throw new Exception("El cupon ya existe [Otro cupon tiene el mismo valor_descuento, fecha_vencimiento, criterio]");
        }

        guardado.get().setValor_descuento(cuponActualizado.getValor_descuento());
        guardado.get().setFecha_vencimiento(cuponActualizado.getFecha_vencimiento());
        guardado.get().setDescripcion(cuponActualizado.getDescripcion());
        guardado.get().setCriterio(cuponActualizado.getCriterio());

        return cuponRepo.save(guardado.get());
    }

    @Override
    public Evento actualizarEvento(Integer idEvento, Evento eventoActualizado) throws Exception {
        Optional<Evento> guardado = eventoRepo.findById(idEvento);
        boolean eventoExistente = esEventoExistente(eventoActualizado);
        boolean eventoDisponible = esEventoActualizadoDisponible(idEvento, eventoActualizado);

        if (guardado.isEmpty()) {
            throw new Exception("El evento no existe");
        }
        if (eventoExistente) {
            throw new Exception("Otro evento tiene las mismas caracteristicas [nombre, imagenURL, fecha, hora_inicio, hora_fin, teatro]");
        }
        if (!eventoDisponible) {
            throw new Exception("El evento no es disponible [Otro evento tiene la misma fecha, hora_inicio]");
        }

        guardado.get().setNombre(eventoActualizado.getNombre());
        guardado.get().setImagenURL(eventoActualizado.getImagenURL());
        guardado.get().setFecha(eventoActualizado.getFecha());
        guardado.get().setHora_inicio(eventoActualizado.getHora_inicio());
        guardado.get().setHora_fin(eventoActualizado.getHora_fin());
        guardado.get().setTeatro(eventoActualizado.getTeatro());

        return eventoRepo.save(guardado.get());
    }

    private boolean esEventoExistente(Evento evento) {
        return eventoRepo.verificarExistencia(evento.getNombre(),
                        evento.getImagenURL(),
                        evento.getFecha(),
                        evento.getHora_inicio(),
                        evento.getHora_fin(),
                        evento.getTeatro().getId())
                .orElse(null) != null;
    }

    private boolean esEventoActualizadoDisponible(Integer idEvento, Evento eventoActualizado){
        return eventoRepo.verificarDisponibilidadParaActualizados(idEvento,
                        eventoActualizado.getFecha(),
                        eventoActualizado.getHora_inicio(),
                        eventoActualizado.getHora_fin(),
                        eventoActualizado.getTeatro().getId())
                .orElse(null) == null;
    }

    @Override
    public Pelicula actualizarPelicula(Integer idPelicula, Pelicula peliculaActualizada) throws Exception {
        Optional<Pelicula> guardada = peliculaRepo.findById(idPelicula);
        boolean peliculaExistente = esPeliculaExistente(idPelicula, peliculaActualizada);
        boolean peliculaDisponible = esPeliculaActualizadaDisponible(idPelicula, peliculaActualizada.getNombre());

        if (guardada.isEmpty()) {
            throw new Exception("La pelicula no existe");
        }
        if (peliculaExistente) {
            throw new Exception("Otra pelicula tiene las mismas caracteristicas [nombre, imagenURL, trailerURL, sinopsis, reparto, estado, generos]");
        }
        if (!peliculaDisponible) {
            throw new Exception("La pelicula ya existe, no disponible [Otra pelicula tiene el mismo nombre]");
        }

        guardada.get().setNombre(peliculaActualizada.getNombre());
        guardada.get().setImagenURL(peliculaActualizada.getImagenURL());
        guardada.get().setTrailerURL(peliculaActualizada.getTrailerURL());
        guardada.get().setSinopsis(peliculaActualizada.getSinopsis());
        guardada.get().setReparto(peliculaActualizada.getReparto());
        guardada.get().setEstado(peliculaActualizada.getEstado());
        guardada.get().setGeneros(peliculaActualizada.getGeneros());

        return peliculaRepo.save(guardada.get());
    }

    // Esta metodo es especial, diferente a los anteriores metodos de existencia, porque este requiere el id del
    // objeto. Pelicula contiene una lista (generos), por lo que en JPQL no se puede comparar listas, por lo
    // que ahora no se va a mandar la lista:generos, pero si el id; para comparar con otras peliculas que no
    // tengan el mismo id a la que se está actualizando. Esta tiene una brecha, que es que si puede tener un
    // problema (no una excepcion) dejando actualizar la misma pelicula igual (con los mismos valores).

    // Otra solucion es no comparar con el id, ni con la lista, pero cuando actualice la misma pelicula y solo
    // le cambie los generos, va a tirar una excepcion de existencia, porque al hacer la consulta se encontraria
    // a si misma (como si existieria otro objeto con los mismos valores)
    private boolean esPeliculaExistente(Integer idPelicula, Pelicula pelicula) {
        return peliculaRepo.verificarExistencia(idPelicula,
                        pelicula.getNombre(),
                        pelicula.getImagenURL(),
                        pelicula.getTrailerURL(),
                        pelicula.getSinopsis(),
                        pelicula.getReparto(),
                        pelicula.getEstado())
                .orElse(null) != null;
    }

    private boolean esPeliculaActualizadaDisponible(Integer idPelicula, String nombre) {
        return peliculaRepo.verificarDisponibilidadParaActualizadas(idPelicula, nombre).orElse(null) == null;
    }

    @Override
    public Genero actualizarGenero(Integer idGenero, Genero generoActualizado) throws Exception {
        Optional<Genero> guardado = generoRepo.findById(idGenero);
        boolean generoExistente = esGeneroExistente(generoActualizado);

        if (guardado.isEmpty()) {
            throw new Exception("El genero no existe");
        }
        if (generoExistente) {
            throw new Exception("El genero ya existe [Otro genero tiene el mismo nombre]");
        }

        guardado.get().setNombre(generoActualizado.getNombre());

        return generoRepo.save(guardado.get());
    }

    @Override
    public Confiteria actualizarConfiteria(Integer idConfiteria, Confiteria confiteriaActualizada) throws Exception {
        Optional<Confiteria> guardada = confiteriaRepo.findById(idConfiteria);
        boolean confiteriaExistente = esConfiteriaExistente(confiteriaActualizada);
        boolean confiteriaDisponible = esConfiteriaActualizadaDisponible(idConfiteria, confiteriaActualizada);

        if (guardada.isEmpty()) {
            throw new Exception("La confiteria no existe");
        }
        if (confiteriaExistente) {
            throw new Exception("Otra confiteria tiene las mismas caracteristicas [producto, precio, imagenURL]");
        }
        if (!confiteriaDisponible) {
            throw new Exception("La confiteria ya existe, no disponible [Otra confiteria tiene el mismo producto]");
        }

        guardada.get().setProducto(confiteriaActualizada.getProducto());
        guardada.get().setPrecio(confiteriaActualizada.getPrecio());
        guardada.get().setImagenURL(confiteriaActualizada.getImagenURL());

        return confiteriaRepo.save(guardada.get());
    }

    private boolean esConfiteriaExistente(Confiteria confiteria) {
        return confiteriaRepo.verificarExistencia(confiteria.getProducto(),
                        confiteria.getPrecio(),
                        confiteria.getImagenURL())
                .orElse(null) != null;
    }

    private boolean esConfiteriaActualizadaDisponible(Integer idConfiteria, Confiteria confiteria) {
        return confiteriaRepo.verificarDisponibilidadParaActualizadas(idConfiteria, confiteria.getProducto()).orElse(null) == null;
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
