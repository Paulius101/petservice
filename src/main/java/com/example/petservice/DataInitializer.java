package com.example.petservice;

import com.example.petservice.entity.PetService;
import com.example.petservice.repository.PetServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PetServiceRepository petServiceRepository;

    public DataInitializer(PetServiceRepository petServiceRepository) {
        this.petServiceRepository = petServiceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (petServiceRepository.count() == 0) {
            PetService service1 = new PetService();
            service1.setName("Viešbutis");
            service1.setDescription("Jaukus kambarėlis jūsų šuns saugumui bei jaukumui;\n" +
                    "Specialisto sudaryta individuali dienotvarkė;\n" +
                    "Komfortiškas laikas atvežti bei pasiimti\u200B\u200B augintinį: 9:00-18:00val.\n" +
                    "Reguliarūs pasivaikščiojimai lauke palaikyti jūsų šuns fizinę bei protinę veiklą;\n" +
                    "Jūsų šuns foto reportažas, kad jaustumėtės ramūs;\n" +
                    "Kasdieninė kailio priežiūra.");
            service1.setPrice(30.0);

            PetService service2 = new PetService();
            service2.setName("SPA");
            service2.setDescription("Maudymas;\n" +
                    "Džiovinimas;\n" +
                    "Kailio kirpimas;\n" +
                    "Šukavimas;\n" +
                    "Higiena;\n" +
                    "Nagų kirpimas.");
            service2.setPrice(60.0);

            PetService service3 = new PetService();
            service3.setName("Dresūra");
            service3.setDescription("SUTEIKSIME DRESAVIMO ĮGŪDŽIŲ, kuriuos galėsite taikyti kasdien bendraudami su savo augintiniu.\n" +
                    "Šunų dresūra vyksta uždaroje mūsų viešbučio aikštelėje, o esant prastam orui – vidinėse patalpose. Be to, esant reikalui dresūros pamokas galime perkelti į Jūsų namus ar į bet kurią kitą vietą Vilniaus mieste.");
            service3.setPrice(40.0);

            petServiceRepository.save(service1);
            petServiceRepository.save(service2);
            petServiceRepository.save(service3);

            System.out.println("Pet services data initialized in the database.");
        }
    }
}
