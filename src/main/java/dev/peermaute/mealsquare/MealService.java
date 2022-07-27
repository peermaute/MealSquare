package dev.peermaute.mealsquare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MealService implements all methods to access the database.
 */
@Service
public class MealService {

    //TODO: Write Filters/methods

    private MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository){
        this.mealRepository = mealRepository;
    }

    /**
     * Creates a new meal in the database.
     * @param meal
     */
    public void newMeal(Meal meal) {
        if(meal == null){
            throw new IllegalArgumentException("Argument ist null!");
        }
        if(meal.containsDots()){
            throw new IllegalArgumentException("Meal attributes must not contain dots!");
        }
        mealRepository.save(meal);
    }

    /**
     * Deletes the meal with the given id from the database.
     * @param id
     */
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

    /**
     * Returns the meal with the given id from the database.
     * @param id
     * @return
     */
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

    /**
     * Updates the meal with the given id in the database. The Meal argument contains all fields that should be updated.
     * @param id
     * @param meal
     */
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

    /**
     * Filter search:
     * Searches the database for entries that match the criteria displayed by the arguments.
     * @param filter
     * @return
     */
    public List<Meal> fetchStudentsByProperties(Filter filter){
        return mealRepository.findMealsByProperties(filter.getName(), filter.getCarbBase(), filter.getIngredient(), filter.getDoesNotContain(), filter.getTag(), filter.getMaxPrepTime());
    }
}
