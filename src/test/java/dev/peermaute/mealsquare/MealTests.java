package dev.peermaute.mealsquare;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MealTests {

    Meal meal;

    @BeforeEach
    void setUp(){
        meal = new Meal();
    }

    @Test
    void testNameContainsDots(){
        meal.setName("bread.butter");
        assertTrue(meal.containsDots());
    }

    @Test
    void testCarbBaseContainsDots(){
        meal.setCarbBase("bread.");
        assertTrue(meal.containsDots());
    }

    @Test
    void testIngredientsContainDots(){
        Map<String, String> ingredients = new HashMap<>();
        ingredients.put("100.", "flour");
        meal.setIngredients(ingredients);
        assertTrue(meal.containsDots());
        meal.getIngredients().clear();
        meal.getIngredients().put("100g", "flou.r");
        assertTrue(meal.containsDots());
    }

    @Test
    void testTagsContainDots(){
        List<String> tags = new ArrayList<>();
        tags.add("medi.terranean");
        meal.setTags(tags);
        assertTrue(meal.containsDots());
    }

    @Test
    void testTagsNullContainDots(){
        assertFalse(meal.containsDots());
    }
}
