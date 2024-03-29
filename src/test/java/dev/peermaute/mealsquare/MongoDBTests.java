package dev.peermaute.mealsquare;

import dev.peermaute.mealsquare.meals.Meal;
import dev.peermaute.mealsquare.meals.MealRepository;
import dev.peermaute.mealsquare.users.AdminUser;
import dev.peermaute.mealsquare.users.AdminUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MongoDBTests {

    @Autowired
    MealRepository mealRepository;

    @Test
    void testConnection(){
        Meal meal = new Meal();
        mealRepository.save(meal);
        assertTrue(mealRepository.findById(meal.getId()).isPresent());
        mealRepository.delete(meal);
        assertFalse(mealRepository.findById(meal.getId()).isPresent());
    }

}
