package dev.peermaute.mealsquare;

import dev.peermaute.mealsquare.meals.Filter;
import dev.peermaute.mealsquare.meals.Meal;
import dev.peermaute.mealsquare.meals.MealRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

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

    //TODO: Write new tests for controller (with authorization)

    /*
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

     */

    /*
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


    @Test
    void testGetMealsFiltered(){
        final String name = "131231234142223123";
        final Filter filter = new Filter(name, null, null, null, null, 20);
        meal.setTime(10);
        meal.setName(name);
        mealRepository.save(meal);
        ResponseEntity<List<Meal>> response = controller.getMealsFiltered(filter);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        List<Meal> mealList = response.getBody();
        assert mealList != null;
        assertTrue(mealList.contains(meal));
    }

    @Test
    void testGetMealPlan(){
        final Filter filter = new Filter(null, null, null, null, null, 60);
        final int days = 7;
        ResponseEntity<?> response = controller.getMealPlan(days, filter);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        if(response.getBody() instanceof List<?> list){
            assertEquals(7, list.size());
        }
        else{
            fail();
        }
    }

    @Test
    void testGetMealPlanNegativeDays(){
        final Filter filter = new Filter(null, null, null, null, null, 60);
        final int days = -1;
        ResponseEntity<?> response = controller.getMealPlan(days, filter);
        assertFalse(response.getStatusCode().is2xxSuccessful());
    }

     */
}
