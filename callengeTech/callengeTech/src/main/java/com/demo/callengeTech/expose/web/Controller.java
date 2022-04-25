package com.demo.callengeTech.expose.web;

import com.demo.callengeTech.entity.AgeAverageResponse;
import com.demo.callengeTech.entity.PersonEntity;
import com.demo.callengeTech.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    @Autowired
    PersonRepository repository;

    @RequestMapping(value = "/creacliente",
            produces = { "application/stream+json", "application/stream+json;charset=UTF-8", "application/json" },
            consumes = { "application/json", "application/json;charset=UTF-8"},
            method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody PersonEntity input){

        log.info("          :: CREA CLIENTES ::       ");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = LocalDate.parse(input.getBirthDate(), formatter);
        Period edad = Period.between(fechaNacimiento, LocalDate.now());
        input.setAge(edad.getYears());

        return ResponseEntity.ok(repository.save(input));
    }

    @RequestMapping(value = "/listclientes",
            produces = { "application/stream+json", "application/stream+json;charset=UTF-8", "application/json" },
            consumes = { "application/json", "application/json;charset=UTF-8" },
            method = RequestMethod.GET)
    public ResponseEntity<List<PersonEntity>> listClientes(){

        log.info("          :: LISTCLIENTES ::       ");

        double[] list = repository.findAll()
                .stream()
                .mapToDouble(personEntity -> (double) personEntity.getAge())
                .toArray();

        return ResponseEntity.ok(repository.findAll()
                .stream()
                .peek(personEntity -> {
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

                    try {
                      Date death = formato.parse(personEntity.getBirthDate());

                      personEntity.setDeathDate(getYearDate(death, (int) calculateAgeAverage(list) + personEntity.getAge()));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                })
                .collect(Collectors.toList())
        );
    }

    @RequestMapping(value = "/kpideclientes",
            produces = { "application/stream+json", "application/stream+json;charset=UTF-8", "application/json" },
            consumes = { "application/json", "application/json;charset=UTF-8" },
            method = RequestMethod.GET)
    public ResponseEntity<AgeAverageResponse> kpideclientes(){

        log.info("     :: KPI CLIENTES ::       ");

        double[] list = repository.findAll()
                .stream()
                .mapToDouble(personEntity -> (double) personEntity.getAge())
                .toArray();

        return ResponseEntity.ok(calculateSD(list));

    }

    private AgeAverageResponse calculateSD(double numArray[]) {
        int length = numArray.length;

        AgeAverageResponse averageResponse = new AgeAverageResponse();

        double range = 0.0;
        double variance = 0.0;
        double mean = calculateAgeAverage(numArray);

        for(double num: numArray) {
            range += Math.pow((num - mean), 2);
            variance += range;
        }
        averageResponse.setAgeAverage(mean);
        averageResponse.setStandardDeviation(Math.sqrt(variance/length));
        return averageResponse;
    }

    private double calculateAgeAverage(double numArray[]){
        double sum = 0.0;
        int length = numArray.length;
        for(double num : numArray) {
            sum += num;
        }
        return sum/length;
    }

    private String getYearDate(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime().toString();
    }
}
