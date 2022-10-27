package dev.peermaute.mealsquare.meals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for MealCustomRepository. Implements more complex queries.
 */
@Repository
public class MealCustomRepositoryImpl implements MealCustomRepository{
    MongoTemplate mongoTemplate;

    @Autowired
    public MealCustomRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Returns all entries in the database that match the criteria.
     * @param name
     * @param carbBase
     * @param ingredient
     * @param tag
     * @param maxPrepTime
     * @return
     */
    @Override
    public List<Meal> findMealsByProperties(String name, String carbBase, String ingredient, String doesNotContain,
                                               String tag, int maxPrepTime) {
        final Query query = new Query();
//     query.fields().include("id").include("name");
        final List<Criteria> criteria = new ArrayList<>();
        if (name != null && !name.isEmpty())
            criteria.add(Criteria.where("name").is(name));
        if (carbBase != null && !carbBase.isEmpty())
            criteria.add(Criteria.where("carbBase").is(carbBase));
        if (tag != null && !tag.isEmpty())
            criteria.add(Criteria.where("tags").in(tag));
        if (maxPrepTime > 0)
            criteria.add(Criteria.where("time").lte(maxPrepTime));
        List<Meal> mealList;
        if (!criteria.isEmpty()){
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
            mealList = mongoTemplate.find(query, Meal.class);
        }
        else{
            mealList = mongoTemplate.findAll(Meal.class);
        }
        return filterMealListWithIngredients(ingredient, doesNotContain, mealList);
    }

    private List<Meal> filterMealListWithIngredients(String ingredient, String doesNotContain, List<Meal> mealList) {
        boolean useIngredientFilter = ingredient != null && !ingredient.isEmpty();
        boolean useDoesNotContainFilter = doesNotContain != null && !doesNotContain.isEmpty();
        if(!useIngredientFilter && !useDoesNotContainFilter){
            return mealList;
        }
        List<Meal> filteredMealList = new ArrayList<>();
        for(Meal meal: mealList){
            if(shouldBeAddedToMealList(meal, useIngredientFilter, useDoesNotContainFilter, ingredient, doesNotContain)){
                filteredMealList.add(meal);
            }
        }
        return filteredMealList;
    }

    private boolean shouldBeAddedToMealList(Meal meal, boolean useIngredientFilter, boolean useDoesNotContainFilter, String ingredient, String doesNotContain) {
        boolean shouldBeAddedToMealList = false;
        if(useIngredientFilter && meal.getIngredients().containsKey(ingredient)){
            shouldBeAddedToMealList = true;
        }
        if(useDoesNotContainFilter && meal.getIngredients().containsKey(doesNotContain) == false){
            shouldBeAddedToMealList = true;
        }
        return shouldBeAddedToMealList;
    }

}
