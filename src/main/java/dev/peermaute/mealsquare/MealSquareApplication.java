package dev.peermaute.mealsquare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class MealSquareApplication implements CommandLineRunner {

    private MealRepository mealRepository;

    @Autowired
    public MealSquareApplication(MealRepository mealRepository){
        this.mealRepository = mealRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(MealSquareApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}
