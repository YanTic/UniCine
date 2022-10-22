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

        if(admin == null){
            throw new Exception("Los datos son incorrectos");
        }

        return admin;
    }


    // Crear
    @Override
    public Horario crearHorario(Horario horario) throws Exception {
        boolean horarioExiste = esHorarioExistente(horario);

        if(horarioExiste){
            throw new Exception("El horario ya existe [Otro horario tiene los mismos horaInicio, horaFin, fecha]");
        }

        return horarioRepo.save(horario);
    }

    private boolean esHorarioExistente(Horario horario) {
        return horarioRepo.verificarExistencia(horario.getHora_inicio(), horario.getHora_fin(), horario.getFecha()).orElse(null) != null ;
    }

    @Override
    public Sala crearSala(Sala sala) throws Exception {
        boolean salaExiste = esSalaExistente(sala.getNombre());

        if(salaExiste) {
            throw new Exception("La sala ya existe [Otra sala tiene el mismo nombre]");
        }

        return salaRepo.save(sala);
    }

    private boolean esSalaExistente(String nombreSala) {
        return salaRepo.findByNombre(nombreSala).orElse(null) != null;
    }

    @Override
    public Funcion crearFuncion(Funcion funcion) throws Exception {
        boolean funcionExiste = esFuncionExistente(funcion.getId());

        if(funcionExiste) {
            throw new Exception("La funcion ya existe");
        }

        return funcionRepo.save(funcion);
    }

    private boolean esFuncionExistente(Integer idFuncion) {
        return funcionRepo.findById(idFuncion).orElse(null) != null;
    }

    @Override
    public DistribucionSillas crearDistribucionSillas(DistribucionSillas distribucionSillas) throws Exception {
        boolean distribucionSillasExiste = esDistribucionSillasExistente(distribucionSillas.getId());

        if(distribucionSillasExiste) {
            throw new Exception("La distribucion de sillas ya existe");
        }

        return distribucionSillasRepo.save(distribucionSillas);
    }

    private boolean esDistribucionSillasExistente(Integer idDistri) {
        return distribucionSillasRepo.findById(idDistri).orElse(null) != null;
    }


    // Actualizar
    @Override
    public Horario actualizarHorario(Horario horario) throws Exception {
        Optional<Horario> guardado = horarioRepo.findById(horario.getId());

        if(guardado.isEmpty()) {
            throw new Exception("El horario no existe");
        }

        return horarioRepo.save(horario);
    }

    @Override
    public Sala actualizarSala(Sala sala) throws Exception {
        Optional<Sala> guardada = salaRepo.findById(sala.getId());

        if(guardada.isEmpty()) {
            throw new Exception("La sala no existe");
        }

        return salaRepo.save(sala);
    }

    @Override
    public Funcion actualizarFuncion(Funcion funcion) throws Exception {
        Optional<Funcion> guardada = funcionRepo.findById(funcion.getId());

        if(guardada.isEmpty()) {
            throw new Exception("La funcion no existe");
        }

        return funcionRepo.save(funcion);
    }

    @Override
    public DistribucionSillas actualizarDistribucionSillas(DistribucionSillas distribucionSillas) throws Exception {
        Optional<DistribucionSillas> guardado = distribucionSillasRepo.findById(distribucionSillas.getId());

        if(guardado.isEmpty()) {
            throw new Exception("La distribucion de sillas no existe");
        }

        return distribucionSillasRepo.save(distribucionSillas);
    }


    // Eliminar
    @Override
    public void eliminarHorario(Integer idHorario) {

    }

    @Override
    public void eliminarSala(Integer idSala) {

    }

    @Override
    public void eliminarFuncion(Integer idFuncion) {

    }

    @Override
    public void eliminarDistribucionSillas(Integer idDistribucionSillas) {

    }


    // Listar
    @Override
    public List<Horario> listarHorarios() {
        return null;
    }

    @Override
    public List<Sala> listarSala() {
        return null;
    }

    @Override
    public List<Funcion> listarFuncion() {
        return null;
    }

    @Override
    public List<DistribucionSillas> listarDistribucionSillas() {
        return null;
    }


    // Obtener
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
