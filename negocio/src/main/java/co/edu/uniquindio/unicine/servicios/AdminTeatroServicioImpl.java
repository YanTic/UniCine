package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

        if (horarioExiste) {
            throw new Exception("El horario ya existe [Otro horario tiene los mismos horaInicio, horaFin, fechaInicio, fechaFin]");
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

    @Override
    public Sala crearSala(Sala sala) throws Exception {
        boolean salaExiste = esSalaExistente(sala.getNombre());

        if (salaExiste) {
            throw new Exception("La sala ya existe [Otra sala tiene el mismo nombre]");
        }

        return salaRepo.save(sala);
    }

    private boolean esSalaExistente(String nombreSala) {
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

    private boolean esHorarioDisponible(Horario horario) {
        return horarioRepo.verificarDisponibilidad(horario.getHora_inicio(),
                        horario.getHora_fin(),
                        horario.getFecha_inicio(),
                        horario.getFecha_fin())
                .orElse(null) != null;
    }

    @Override
    public DistribucionSillas crearDistribucionSillas(DistribucionSillas distribucionSillas) throws Exception {
        boolean distribucionSillasExiste = esDistribucionSillasExistente(distribucionSillas);

        if (distribucionSillasExiste) {
            throw new Exception("La distribucion de sillas ya existe");
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
    public Horario actualizarHorario(Horario horario) throws Exception {
        Optional<Horario> guardado = horarioRepo.findById(horario.getId());

        System.out.println("guardado: " + guardado.get());
        System.out.println("actualizado: " + horario);


        boolean horarioRepetido = esHorarioExistente(horario);

        if (guardado.isEmpty()) {
            throw new Exception("El horario no existe");
        }
        if (horarioRepetido) {
            throw new Exception("Otro horario tiene las mismas caracteristicas [horaInicio, horaFin, fechaInicio, fechaFin]");
        }

        return horarioRepo.save(horario);
    }

    @Override
    public Sala actualizarSala(Sala sala) throws Exception {
        Optional<Sala> guardada = salaRepo.findById(sala.getId());
        boolean salaRepetida = esSalaRepetida(sala);
        System.out.println("salaRepetida: " + salaRepetida);

        if (guardada.isEmpty()) {
            throw new Exception("La sala no existe");
        }
        if (salaRepetida) {
            throw new Exception("Otra sala tiene las mismas caracteristicas [nombre, tipo, teatroid, distSillasid]");
        }

        return salaRepo.save(sala);
    }

    private boolean esSalaRepetida(Sala sala) {
        return salaRepo.verificarExistencia(sala.getNombre(),
                        sala.getTipo(),
                        sala.getTeatro().getId(),
                        sala.getDistribucionSillas().getId())
                .orElse(null) != null;
    }

    @Override
    public Funcion actualizarFuncion(Funcion funcion) throws Exception {
        Optional<Funcion> guardada = funcionRepo.findById(funcion.getId());

        System.out.println("guardada: " + guardada);
        System.out.println("actualiz: " + funcion);

        if (guardada.isEmpty()) {
            throw new Exception("La funcion no existe");
        }

        return funcionRepo.save(funcion);
    }

    @Override
    public DistribucionSillas actualizarDistribucionSillas(DistribucionSillas distribucionSillas) throws Exception {
        Optional<DistribucionSillas> guardado = distribucionSillasRepo.findById(distribucionSillas.getId());

        if (guardado.isEmpty()) {
            throw new Exception("La distribucion de sillas no existe");
        }

        return distribucionSillasRepo.save(distribucionSillas);
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
