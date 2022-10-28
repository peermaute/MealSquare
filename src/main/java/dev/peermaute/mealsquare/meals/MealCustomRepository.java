package dev.peermaute.mealsquare.meals;

import java.util.List;

/**
 * This custom repository is indirectly added to the MealRepository and allows more complex queries.
 */
public interface MealCustomRepository {
    List<Meal> findMealsByProperties(String name, String carbBase, String ingredient, String doesNotContain,
                                            String tag, int maxPrepTime);

}
