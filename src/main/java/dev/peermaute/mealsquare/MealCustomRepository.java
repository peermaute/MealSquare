package dev.peermaute.mealsquare;

import java.util.List;

public interface MealCustomRepository {
    public List<Meal> findMealsByProperties(String name, String carbBase, String ingredient,
                                            String tag, int maxPrepTime);
}
