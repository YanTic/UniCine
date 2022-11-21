package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.AdminGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminGeneralRepo extends JpaRepository<AdminGeneral, String> {

    @Query("select adm from AdminGeneral adm where adm.email = :email and adm.contrasenia = :contrasenia")
    AdminGeneral comprobarAutenticacion(String email, String contrasenia);

    Optional<AdminGeneral> findByEmail(String email);
}
