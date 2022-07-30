package dev.peermaute.mealsquare;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ControllerTests {

    @Autowired
    Controller controller;

    @Autowired
    MealRepository mealRepository;

    Meal meal;

    @BeforeEach
    void setUp(){
        meal = new Meal();
        mealRepository.save(meal);
    }

    @AfterEach
    void cleaUp(){
        mealRepository.delete(meal);
    }

    @Test
    void testGetMeal(){
        ResponseEntity<?> response = controller.getMeal(meal.getId());
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(meal, response.getBody());
    }

    @Test
    void testGetMealUnknownId(){
        ResponseEntity<?> response = controller.getMeal("1287381763871682313128");
        assertFalse(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testNewMeal(){
        Meal newMeal = new Meal();
        ResponseEntity<?> response = controller.newMeal(newMeal);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(mealRepository.findById(meal.getId()).isPresent());
        mealRepository.delete(newMeal);
    }

    @Test
    void testNewMealNull(){
        ResponseEntity<?> response = controller.newMeal(null);
        assertFalse(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testDeleteMeal(){
        Meal newMeal = new Meal();
        mealRepository.save(newMeal);
        ResponseEntity<?> response = controller.deleteMeal(newMeal.getId());
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertFalse(mealRepository.findById(newMeal.getId()).isPresent());
    }

    @Test
    void testDeleteMealNull(){
        ResponseEntity<?> response = controller.deleteMeal(null);
        assertFalse(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testUpdateMeal(){
        String newName = "Eggs";
        meal.setName(newName);
        assertNotEquals(newName, mealRepository.findById(meal.getId()).get().getName());
        ResponseEntity<?> response = controller.updateMeal(meal.getId(), meal);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(newName, mealRepository.findById(meal.getId()).get().getName());
    }

    @Test
    void testUpdateMealIdNull(){
        ResponseEntity<?> response = controller.updateMeal(null, meal);
        assertFalse(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testUpdateMealMealNull(){
        ResponseEntity<?> response = controller.updateMeal(meal.getId(), null);
        assertFalse(response.getStatusCode().is2xxSuccessful());
    }
}
