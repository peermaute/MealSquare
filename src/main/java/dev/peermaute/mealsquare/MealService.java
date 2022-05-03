package dev.peermaute.mealsquare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealService {

    private MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository){
        this.mealRepository = mealRepository;
    }

    public void newMeal(Meal meal) {
        mealRepository.save(meal);
    }
}
