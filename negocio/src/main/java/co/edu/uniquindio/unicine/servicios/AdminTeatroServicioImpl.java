package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminTeatroServicioImpl implements AdminTeatroServicio {

    private final AdminTeatroRepo adminTeatroRepo;
    private final HorarioRepo horarioRepo;
    private final SalaRepo salaRepo;
    private final FuncionRepo funcionRepo;
    private final DistribucionSillasRepo distribucionSillasRepo;

    public AdminTeatroServicioImpl(AdminTeatroRepo adminTeatroRepo, HorarioRepo horarioRepo, SalaRepo salaRepo, FuncionRepo funcionRepo, DistribucionSillasRepo distribucionSillasRepo) {
        this.adminTeatroRepo = adminTeatroRepo;
        this.horarioRepo = horarioRepo;
        this.salaRepo = salaRepo;
        this.funcionRepo = funcionRepo;
        this.distribucionSillasRepo = distribucionSillasRepo;
    }

    @Override
    public AdminTeatro login(String email, String contrasenia) throws Exception {
        AdminTeatro admin = adminTeatroRepo.comprobarAutenticacion(email, contrasenia);

        if (admin == null) {
            throw new Exception("Los datos son incorrectos");
        }

        return admin;
    }


    // Crear
    @Override
    public Horario crearHorario(Horario horario) throws Exception {
        boolean horarioExiste = esHorarioExistente(horario);
        boolean horarioDisponible = esHorarioDisponible(horario);

        if (horarioExiste) {
            throw new Exception("El horario ya existe [Otro horario tiene los mismos horaInicio, horaFin, fechaInicio, fechaFin]");
        }
        if (!horarioDisponible) {
            throw new Exception("El horario se encuentra en una fecha que comparte la misma hora inicio y fin de otro horario [Cambie la hora o la fecha]");
        }

        return horarioRepo.save(horario);
    }

    private boolean esHorarioExistente(Horario horario) {
        return horarioRepo.verificarExistencia(horario.getHora_inicio(),
                        horario.getHora_fin(),
                        horario.getFecha_inicio(),
                        horario.getFecha_fin())
                .orElse(null) != null;
    }

    private boolean esHorarioDisponible(Horario horario) {
        // Esto obtiene los horarios que esten entre las fechas de inicio y fin del horario [que se mandó por parametro]
        List<Horario> horariosPorFecha = horarioRepo.obtenerHorariosPorFecha(horario.getFecha_inicio(), horario.getFecha_fin());

        boolean horarioDisponible = true;
        for (Horario h : horariosPorFecha) {
            // Comparo si la horainicio está entre otra horainicio y horafin
            if(horario.getHora_inicio().isAfter(h.getHora_inicio()) && horario.getHora_inicio().isBefore(h.getHora_fin())) {
                horarioDisponible = false; // La hora inicio está en un intervalo de hora que tiene otro horario [Horario que está en algun dia de la fecha del horario a crear]
                break;
            }
            // Comparo si la horafin está entre otra horainicio y horafin
            if(horario.getHora_fin().isAfter(h.getHora_inicio()) && horario.getHora_fin().isBefore(h.getHora_fin())) {
                horarioDisponible = false; // La hora fin está en un intervalo de hora que tiene otro horario [Horario que está en algun dia de la fecha del horario a crear]
                break;
            }
        }

        return horarioDisponible;
    }

    @Override
    public Sala crearSala(Sala sala) throws Exception {
        boolean salaDisponible = esSalaDisponible(sala.getNombre());

        if (salaDisponible) {
            throw new Exception("La sala ya existe, no disponible [Otra sala tiene el mismo nombre]");
        }

        return salaRepo.save(sala);
    }

    private boolean esSalaDisponible(String nombreSala) {
        return salaRepo.findByNombre(nombreSala).orElse(null) != null;
    }

    @Override
    public Funcion crearFuncion(Funcion funcion) throws Exception {
        boolean funcionDisponible = esFuncionDisponible(funcion);

        if (!funcionDisponible) {
            throw new Exception("La funcion no está disponible [Horario y Sala ya pertenecen a otra funcion]");
        }

        return funcionRepo.save(funcion);
    }

    // Si una función tiene un horario en una sala asignada, otra no puede tener el mismo horario en la misma sala
    // (pero si diferente sala u horario)
    private boolean esFuncionDisponible(Funcion funcion) {
        return funcionRepo.verificarDisponibilidad(funcion.getHorario().getId(), funcion.getSala().getId())
                .orElse(null) == null;
    }

    @Override
    public DistribucionSillas crearDistribucionSillas(DistribucionSillas distribucionSillas) throws Exception {
        boolean distribucionSillasExiste = esDistribucionSillasExistente(distribucionSillas);

        if (distribucionSillasExiste) {
            throw new Exception("La distribucion de sillas ya existe [Otra distribucion tiene las mismas filas, columnas, rutaEsquema, totalSillas]");
        }

        return distribucionSillasRepo.save(distribucionSillas);
    }

    private boolean esDistribucionSillasExistente(DistribucionSillas distri) {
        return distribucionSillasRepo.verificarExistencia(distri.getFilas(),
                        distri.getColumnas(),
                        distri.getRutaEsquema(),
                        distri.getTotalSillas())
                .orElse(null) != null;
    }


    // Actualizar
    @Override
    public Horario actualizarHorario(Integer idHorario, Horario horarioActualizado) throws Exception {
        Optional<Horario> guardado = horarioRepo.findById(idHorario);
        boolean horarioDisponible = esHorarioDisponible(horarioActualizado);

        System.out.println("guardado: " + guardado.get());
        System.out.println("actualizado: " + horarioActualizado);

        // Compara los valores actualizados del horario, si existe un horario ya guardado en
        // la bd exactamente con los mismos valores que otro horario ya guardado, no deberia
        // dejar actualizar
        boolean horarioRepetido = esHorarioExistente(horarioActualizado);

        if (guardado.isEmpty()) {
            throw new Exception("El horario no existe");
        }
        if (horarioRepetido) {
            throw new Exception("Otro horario tiene las mismas caracteristicas [horaInicio, horaFin, fechaInicio, fechaFin]");
        }
        if (horarioDisponible) {
            throw new Exception("El horario se encuentra en una fecha que comparte la misma hora inicio y fin de otro horario [Cambie la hora o la fecha]");
        }

        // Como el horario que se actualizó no tiene exactamente los mismos valores que otro
        // ahora se hace el .set(), no es necesario el save(), pues porque el EntityManger y/o JPA
        // ya lo hace automaticamente al hacer .set(), pero igual lo dejé

        // Aquí se le asignan los valores al horario guardado en la bd, solo se le usa el .set()
        // a valores que están en el builder, porque son los que se pueden cambiar
        guardado.get().setFecha_inicio(horarioActualizado.getFecha_inicio());
        guardado.get().setFecha_fin(horarioActualizado.getFecha_fin());
        guardado.get().setHora_inicio(horarioActualizado.getHora_inicio());
        guardado.get().setHora_fin(horarioActualizado.getHora_fin());

        return horarioRepo.save(guardado.get());
    }

    @Override
    public Sala actualizarSala(Integer idSala, Sala salaActualizada) throws Exception {
        Optional<Sala> guardada = salaRepo.findById(idSala);
        boolean salaRepetida = esSalaExistente(salaActualizada); // Verifica si otra sala tiene exactamente los mismos valores
        boolean salaDisponible = esSalaActualizadaDisponible(idSala, salaActualizada.getNombre()); // Verifica si otra sala tiene el mismo campo unique

        if (guardada.isEmpty()) {
            throw new Exception("La sala no existe");
        }
        if (salaRepetida) {
            throw new Exception("Otra sala tiene las mismas caracteristicas [nombre, tipo, teatroid, distSillasid]");
        }
        if (!salaDisponible) {
            throw new Exception("La sala ya existe, no disponible [Otra sala tiene el mismo nombre]");
        }

        guardada.get().setNombre(salaActualizada.getNombre());
        guardada.get().setTipo(TipoSala.ESTANDAR);
        guardada.get().setTeatro(salaActualizada.getTeatro());
        guardada.get().setDistribucionSillas(salaActualizada.getDistribucionSillas());

        return salaRepo.save(guardada.get());
    }

    private boolean esSalaExistente(Sala sala) {
        return salaRepo.verificarExistencia(sala.getNombre(),
                                            sala.getTipo(),
                                            sala.getTeatro().getId(),
                                            sala.getDistribucionSillas().getId())
                       .orElse(null) != null;
    }

    private boolean esSalaActualizadaDisponible(Integer idSala, String nombreSala) {
        return salaRepo.verificarDisponibilidadParaActualizadas(idSala, nombreSala).orElse(null) == null;
    }

    @Override
    public Funcion actualizarFuncion(Integer idFuncion, Funcion funcionActualizada) throws Exception {
        Optional<Funcion> guardada = funcionRepo.findById(idFuncion);
        boolean funcionExistente = esFuncionExistente(funcionActualizada);
        boolean funcionDisponible = esFuncionActualizadaDisponible(idFuncion, funcionActualizada);

        System.out.println("guardada: " + guardada);
        System.out.println("actualiz: " + funcionActualizada);

        if (guardada.isEmpty()) {
            throw new Exception("La funcion no existe");
        }
        if (funcionExistente) {
            throw new Exception("Otra funcion tiene las mismas caracteristicas [precio, horario, pelicula, sala]");
        }
        if (!funcionDisponible) {
            throw new Exception("La funcion no está disponible [Horario y Sala ya pertenecen a otra funcion]");
        }

        guardada.get().setPrecio(funcionActualizada.getPrecio());
        guardada.get().setHorario(funcionActualizada.getHorario());
        guardada.get().setPelicula(funcionActualizada.getPelicula());
        guardada.get().setSala(funcionActualizada.getSala());

        return funcionRepo.save(guardada.get());
    }

    private boolean esFuncionExistente(Funcion funcion) {
        return funcionRepo.verificarExistencia(funcion.getPrecio(),
                funcion.getHorario().getId(),
                funcion.getPelicula().getId(),
                funcion.getSala().getId()).orElse(null) != null;
    }

    private boolean esFuncionActualizadaDisponible(Integer idFuncion, Funcion funcion) {
        return funcionRepo.verificarDisponibilidadParaActualizadas(idFuncion, funcion.getHorario().getId(), funcion.getSala().getId())
                .orElse(null) == null;
    }

    @Override
    public DistribucionSillas actualizarDistribucionSillas(Integer idDistSillas, DistribucionSillas distribucionSillasActualizada) throws Exception {
        Optional<DistribucionSillas> guardado = distribucionSillasRepo.findById(idDistSillas);
        boolean distribucionSillasExiste = esDistribucionSillasExistente(distribucionSillasActualizada);

        if (guardado.isEmpty()) {
            throw new Exception("La distribucion de sillas no existe");
        }
        if(distribucionSillasExiste) {
            throw new Exception("La distribucion de sillas ya existe [Otra distribucion tiene las mismas filas, columnas, rutaEsquema, totalSillas]");
        }

        guardado.get().setFilas(distribucionSillasActualizada.getFilas());
        guardado.get().setColumnas(distribucionSillasActualizada.getColumnas());
        guardado.get().setRutaEsquema(distribucionSillasActualizada.getRutaEsquema());
        guardado.get().setTotalSillas(distribucionSillasActualizada.getTotalSillas());

        return distribucionSillasRepo.save(guardado.get());
    }



    // Eliminar
    @Override
    public void eliminarHorario(Integer idHorario) throws Exception {
        Optional<Horario> horario = horarioRepo.findById(idHorario);

        if (horario.isEmpty()) {
            throw new Exception("El horario [id:" + idHorario + "] no existe");
        }

        horarioRepo.delete(horario.get());
    }

    @Override
    public void eliminarSala(Integer idSala) throws Exception {
        Optional<Sala> sala = salaRepo.findById(idSala);

        if (sala.isEmpty()) {
            throw new Exception("La sala [id:" + idSala + "] no existe");
        }

        salaRepo.delete(sala.get());
    }

    @Override
    public void eliminarFuncion(Integer idFuncion) throws Exception {
        Optional<Funcion> funcion = funcionRepo.findById(idFuncion);

        if (funcion.isEmpty()) {
            throw new Exception("La funcion [id:" + idFuncion + "] no existe");
        }

        funcionRepo.delete(funcion.get());
    }

    @Override
    public void eliminarDistribucionSillas(Integer idDistribucionSillas) throws Exception {
        Optional<DistribucionSillas> dist = distribucionSillasRepo.findById(idDistribucionSillas);

        if (dist.isEmpty()) {
            throw new Exception("La distribucion [id:" + idDistribucionSillas + "] no existe");
        }

        distribucionSillasRepo.delete(dist.get());
    }


    // Listar
    @Override
    public List<Horario> listarHorarios() {
        return horarioRepo.findAll();
    }

    @Override
    public List<Sala> listarSalas() {
        return salaRepo.findAll();
    }

    @Override
    public List<Funcion> listarFunciones() {
        return funcionRepo.findAll();
    }

    @Override
    public List<Funcion> listarFuncionesPorPeliculaCiudad(Integer idPelicula, Integer idCiudad) throws Exception {
        List<Funcion> funciones = funcionRepo.obtenerFuncionesPorPeliculaCiudad(idPelicula, idCiudad);

        if(funciones.isEmpty()) {
            throw new Exception("La pelicula [id:"+idPelicula+"] en la ciudad [id:"+idCiudad+"] no tiene funciones");
        }

        return funciones;
    }

    @Override
    public List<DistribucionSillas> listarDistribucionSillas() {
        return distribucionSillasRepo.findAll();
    }


    // Obtener

    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Horario obtenerHorario(Integer idHorario) throws Exception{
        Optional<Horario> horario = horarioRepo.findById(idHorario);

        if(horario.isEmpty()) {
            throw new Exception("El horario [id:"+idHorario+ "] no existe");
        }

        return horario.get();
    }

    @Override
    public Sala obtenerSala(Integer idSala) throws Exception {
        Optional<Sala> sala = salaRepo.findById(idSala);

        if(sala.isEmpty()) {
            throw new Exception("La sala [id:"+idSala+ "] no existe");
        }

        return sala.get();
    }

    @Override
    public Funcion obtenerFuncion(Integer idFuncion) throws Exception {
        Optional<Funcion> funcion = funcionRepo.findById(idFuncion);

        if(funcion.isEmpty()) {
            throw new Exception("La funcion [id:"+idFuncion+ "] no existe");
        }

        return funcion.get();
    }

    @Override
    public DistribucionSillas obtenerDistribucionSillas(Integer idDistribucionSillas) throws Exception {
        Optional<DistribucionSillas> distribucionSillas = distribucionSillasRepo.findById(idDistribucionSillas);

        if(distribucionSillas.isEmpty()) {
            throw new Exception("La distribucion de sillas [id:"+idDistribucionSillas+ "] no existe");
        }

        return distribucionSillas.get();
    }
}
