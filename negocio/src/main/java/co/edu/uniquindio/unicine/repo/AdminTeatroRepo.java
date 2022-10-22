package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.AdminTeatro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminTeatroRepo extends JpaRepository<AdminTeatro, String> {

    @Query("select at from AdminTeatro at where at.email = :email and at.contrasenia = :contrasenia")
    AdminTeatro comprobarAutenticacion(String email, String contrasenia);
}
