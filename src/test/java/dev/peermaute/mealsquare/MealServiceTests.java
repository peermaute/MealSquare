package dev.peermaute.mealsquare;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MealServiceTests {

    //TODO: test edge cases

    @Autowired
    MealService mealService;

    @Autowired
    MealRepository mealRepository;

    Meal meal;

    @BeforeEach
    void setUp(){
        this.meal = new Meal();
    }
    @AfterEach
    void cleanUp(){
        mealRepository.delete(meal);
    }

    @Test
    void testNewMeal(){
        mealService.newMeal(meal);
        assertTrue(mealRepository.findById(meal.getId()).isPresent());
    }

    @Test
    void testDeleteMeal(){
        mealRepository.save(meal);
        assertTrue(mealRepository.findById(meal.getId()).isPresent());
        mealService.deleteMeal(meal.getId());
        assertFalse(mealRepository.findById(meal.getId()).isPresent());
    }

    @Test
    void testGetMeal(){
        mealRepository.save(meal);
        Meal meal2 = mealService.getMeal(meal.getId());
        assertEquals(meal, meal2);
        mealRepository.delete(meal);
    }

    @Test
    void testUpdate(){
        mealRepository.save(meal);
        assertNull(meal.getName());
        assertNull(meal.getCarbBase());
        assertTrue(meal.getTags() == null || meal.getTags().isEmpty());
        assertTrue(meal.getIngredients() == null || meal.getIngredients().isEmpty());

        Meal updMeal = new Meal();
        updMeal.setCarbBase("noodles");
        Map<String, String> ingredients = new HashMap<>();
        ingredients.put("banana", "1");
        ingredients.put("flour", "100g");
        updMeal.setIngredients(ingredients);
        updMeal.setName("Banana bread");
        updMeal.setTags(Arrays.asList(new String[]{"oven", "fruity"}));
        updMeal.setTime(30);

        mealService.updateMeal(meal.getId(), updMeal);

        meal = mealRepository.findById(meal.getId()).get();
        assertEquals(meal.getCarbBase(), updMeal.getCarbBase());
        assertEquals(meal.getName(), updMeal.getName());
        assertEquals(meal.getTags(), updMeal.getTags());
        assertEquals(meal.getIngredients(), updMeal.getIngredients());
        assertEquals(meal.getTime(), updMeal.getTime());
    }

    @Test
    void testFetchMealsByPropertiesName(){
        meal.setName("TestMeal123131123");
        mealRepository.save(meal);
        assertEquals(1, mealRepository.findMealsByProperties("TestMeal123131123", null, null, null, null, 0).size());
        Meal meal2 = new Meal();
        meal2.setName("TestMeal123131123");
        mealRepository.save(meal2);
        assertEquals(2, mealRepository.findMealsByProperties("TestMeal123131123", null, null, null, null, 0).size());
        mealRepository.delete(meal2);
    }

    @Test
    void testFetchMealsByPropertiesCarbBase(){
        meal.setCarbBase("Noodles12313132");
        mealRepository.save(meal);
        assertEquals(1, mealRepository.findMealsByProperties(null, "Noodles12313132", null, null, null, 0).size());
        Meal meal2 = new Meal();
        meal2.setCarbBase("Noodles12313132");
        mealRepository.save(meal2);
        assertEquals(2, mealRepository.findMealsByProperties(null, "Noodles12313132", null, null, null, 0).size());
        mealRepository.delete(meal2);
    }

    @Test
    void testFetchMealsByPropertiesIngredient(){
        meal.getIngredients().put("Carrots13452523", "3");
        mealRepository.save(meal);
        assertEquals(1, mealRepository.findMealsByProperties(null, null, "Carrots13452523", null, null, 0).size());
        Meal meal2 = new Meal();
        meal2.getIngredients().put("Carrots13452523", "3");
        mealRepository.save(meal2);
        assertEquals(2, mealRepository.findMealsByProperties(null, null, "Carrots13452523", null, null, 0).size());
        mealRepository.delete(meal2);
    }

    @Test
    void testFetchMealsByPropertiesDoesNotContain(){
        int initSize = mealRepository.findMealsByProperties("CarrotCake1234142", null, null, "Carrots13452523",  null, 0).size();
        meal.getIngredients().put("Carrots13452523", "3");
        meal.setName("CarrotCake1234142");
        mealRepository.save(meal);
        assertEquals(initSize, mealRepository.findMealsByProperties("CarrotCake1234142", null, null, "Carrots13452523",  null, 0).size());
        assertEquals(initSize + 1, mealRepository.findMealsByProperties("CarrotCake1234142", null, null,  null, null, 0).size());
    }

    @Test
    void testFetchMealsByPropertiesTag(){
        meal.getTags().add("Meditedi13112");
        mealRepository.save(meal);
        assertEquals(1, mealRepository.findMealsByProperties(null, null, null, null, "Meditedi13112", 0).size());
        Meal meal2 = new Meal();
        meal2.getTags().add("Meditedi13112");
        mealRepository.save(meal2);
        assertEquals(2, mealRepository.findMealsByProperties(null, null, null, null, "Meditedi13112", 0).size());
        mealRepository.delete(meal2);
    }

    @Test
    void testFetchMealsByPropertiesMaxPrepTime(){
        int size = mealRepository.findMealsByProperties(null, null, null, null, null, 100).size();
        meal.setTime(1);
        mealRepository.save(meal);
        assertEquals(size + 1, mealRepository.findMealsByProperties(null, null, null, null, null, 100).size());
    }

    /*
    @Test
    void testGetMealsUnder30min(){
        List<Meal> mealList = mealRepository.findMealsByProperties(null, null, null, null, 30);
        for(Meal meal: mealList){
            System.out.println(meal.getName());
        }
    }
     */
    //TODO: More tests for filters with more than one criteria
}
