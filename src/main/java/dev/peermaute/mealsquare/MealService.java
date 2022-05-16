package dev.peermaute.mealsquare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {

    //TODO: Write Filters/methods

    private MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository){
        this.mealRepository = mealRepository;
    }

    public void newMeal(Meal meal) {
        if(meal == null){
            throw new IllegalArgumentException("Argument ist null!");
        }
        if(meal.containsDots()){
            throw new IllegalArgumentException("Meal attributes must not contain dots!");
        }
        mealRepository.save(meal);
    }

    public void deleteMeal(String id) {
        if(id == null){
            throw new IllegalArgumentException("ID must not be null");
        }
        if(mealRepository.findById(id).isPresent()){
            mealRepository.delete(mealRepository.findById(id).get());
        }
        else{
            throw new IllegalArgumentException("Meal not found in database for given id");
        }
    }

    public Meal getMeal(String id) {
        if(id == null){
            throw new IllegalArgumentException("ID must not be null");
        }
        if(mealRepository.findById(id).isPresent()){
            return mealRepository.findById(id).get();
        }
        else{
            throw new IllegalArgumentException("Meal not found in database for given id");
        }
    }

    public void updateMeal(String id, Meal meal) {
        if(id == null){
            throw new IllegalArgumentException("ID must not be null");
        }
        if(meal.getId() != null && meal.getId().equals(id) == false){
            throw new IllegalArgumentException("ID must not be null");
        }
        Meal updMeal;
        if(mealRepository.findById(id).isPresent()){
            updMeal = mealRepository.findById(id).get();
        }
        else{
            throw new IllegalArgumentException("No Meal with given ID in database");
        }
        if(meal.getIngredients() != null){
            updMeal.setIngredients(meal.getIngredients());
        }
        if(meal.getTags() != null){
            updMeal.setTags(meal.getTags());
        }
        if(meal.getName() != null){
            updMeal.setName(meal.getName());
        }
        if(meal.getCarbBase() != null){
            updMeal.setCarbBase(meal.getCarbBase());
        }
        if(meal.getTime() != 0){
            updMeal.setTime(meal.getTime());
        }
        mealRepository.save(updMeal);
    }

    public List<Meal> fetchStudentsByProperties(String name, String carbBase, String ingredient,
                                                String tag, int maxPrepTime){
        return mealRepository.findMealsByProperties(name, carbBase, ingredient, tag, maxPrepTime);
    }
}
