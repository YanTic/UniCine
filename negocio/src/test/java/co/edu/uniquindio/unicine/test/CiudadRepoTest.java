package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.repo.CiudadRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CiudadRepoTest {

    @Autowired
    private CiudadRepo ciudadRepo;

    @Test
    @Sql("classpath:dataset.sql")
    public void contarTeatrosPorCiudad(){
        List<Object[]> teatros = ciudadRepo.teatroPorCiudad();

        teatros.forEach(o->
                System.out.println(o[0]+ ": "+ o[1])
        );
    }
}
