package co.edu.uniquindio.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NegocioApplication {

    public static void main(String[] args) {
        SpringApplication.run(NegocioApplication.class, args);
    }

    /*public static void main(String[] args) {
        Persona p1 = new Persona("123", "Julian", "julian@mail.com", "123124124");
        Persona p2 = new Persona("123", "Julian", "julian@mail.com", "123124124");

        System.out.println(p1);
        System.out.println(p2);

        System.out.println(p1==p2);
        System.out.println(p1.equals(p2)); // Sin @Override, este equals hace ==

    }*/

}
