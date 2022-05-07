package dev.peermaute.mealsquare;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
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

        mealService.updateMeal(meal.getId(), updMeal);

        meal = mealRepository.findById(meal.getId()).get();
        assertEquals(meal.getCarbBase(), updMeal.getCarbBase());
        assertEquals(meal.getName(), updMeal.getName());
        assertEquals(meal.getTags(), updMeal.getTags());
        assertEquals(meal.getIngredients(), updMeal.getIngredients());
    }
}
