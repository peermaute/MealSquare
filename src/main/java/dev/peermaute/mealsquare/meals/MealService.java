package dev.peermaute.mealsquare.meals;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * MealService implements all methods to access the database.
 */
@Service
public class MealService {

    private MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    /**
     * Creates a new meal in the database.
     *
     * @param meal
     */
    public void newMeal(Meal meal) {
        if (meal == null) {
            throw new IllegalArgumentException("Argument ist null!");
        }
        if (meal.containsDots()) {
            throw new IllegalArgumentException("Meal attributes must not contain dots!");
        }
        mealRepository.save(meal);
    }

    /**
     * Deletes the meal with the given id from the database.
     *
     * @param id
     */
    public void deleteMeal(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        if (mealRepository.findById(id).isPresent()) {
            mealRepository.delete(mealRepository.findById(id).get());
        } else {
            throw new IllegalArgumentException("Meal not found in database for given id");
        }
    }

    /**
     * Returns the meal with the given id from the database.
     *
     * @param id
     * @return
     */
    public Meal getMeal(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        if (mealRepository.findById(id).isPresent()) {
            Meal meal = mealRepository.findById(id).get();
            meal.setCreatorId(null);
            return meal;
        } else {
            throw new IllegalArgumentException("Meal not found in database for given id");
        }
    }

    /**
     * Updates the meal with the given id in the database. The Meal argument contains all fields that should be updated.
     *
     * @param id
     * @param meal
     */
    public void updateMeal(String id, Meal meal) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        if (meal.getId() != null && meal.getId().equals(id) == false) {
            throw new IllegalArgumentException("IDs must be the same");
        }
        Meal updMeal;
        if (mealRepository.findById(id).isPresent()) {
            updMeal = mealRepository.findById(id).get();
        } else {
            throw new IllegalArgumentException("No Meal with given ID in database");
        }
        if (meal.getIngredients() != null) {
            updMeal.setIngredients(meal.getIngredients());
        }
        if (meal.getTags() != null) {
            updMeal.setTags(meal.getTags());
        }
        if (meal.getName() != null) {
            updMeal.setName(meal.getName());
        }
        if (meal.getCarbBase() != null) {
            updMeal.setCarbBase(meal.getCarbBase());
        }
        if (meal.getTime() != 0) {
            updMeal.setTime(meal.getTime());
        }
        mealRepository.save(updMeal);
    }

    /**
     * Sets the given file as new image for the meal.
     */
    public void setImage(String mealId, MultipartFile file) throws IOException {
        Meal meal;
        if (mealRepository.findById(mealId).isPresent()) {
            meal = mealRepository.findById(mealId).get();
        } else {
            throw new IllegalArgumentException("No Meal with given ID in database");
        }
        meal.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        mealRepository.save(meal);
    }

    /**
     * Filter search:
     * Searches the database for entries that match the criteria displayed by the arguments.
     */
    public List<Meal> fetchMealsByProperties(Filter filter) {
        List<Meal> mealList =  mealRepository.findMealsByProperties(filter.getName(), filter.getCarbBase(), filter.getIngredient(), filter.getDoesNotContain(), filter.getTag(), filter.getMaxPrepTime());
        clearCreatorIds(mealList);
        return mealList;
    }

    /**
     * Returns all Meals that the given user created.
     */
    public List<Meal> getMealsOfUser(String userId) {
        List<Meal> mealList = mealRepository.findByCreatorId(userId);
        clearCreatorIds(mealList);
        return mealList;
    }

    /**
     * Returns a list of meals (meal plan) for a given number of days that match the filter.
     */
    public List<Meal> getMealPlan(Filter filter, int days) {
        if (days < 1 || days >= 30) {
            throw new IllegalArgumentException("Days must be bigger than 0 and smaller than 30");
        }
        if (filter == null) {
            //TODO: Use Pagination
            List<Meal> mealList = mealRepository.findAll();
            List<Meal> randomisedMealList = getRandomMeals(mealList, days);
            clearCreatorIds(randomisedMealList);
            return randomisedMealList;
        }
        List<Meal> mealList = fetchMealsByProperties(filter);
        if (mealList.size() < days) {
            throw new IllegalArgumentException("Not enough recipes with given filters found for amount of days");
        }
        List<Meal> randomisedMealList = getRandomMeals(mealList, days);
        for(Meal meal: randomisedMealList){
            meal.setCreatorId(null);
        }
        return randomisedMealList;
    }

    private void clearCreatorIds(Collection<Meal> mealCollection){
        for(Meal meal: mealCollection){
            meal.setCreatorId(null);
        }
    }

    private List<Meal> getRandomMeals(List<Meal> mealList, int numberOfMeals) {
        final int[] randomNumbers = new Random().ints(0, mealList.size()).distinct().limit(numberOfMeals).toArray();
        List<Meal> returnList = new ArrayList<>();
        for (int i : randomNumbers) {
            returnList.add(mealList.get(i));
        }
        return returnList;
    }
}
