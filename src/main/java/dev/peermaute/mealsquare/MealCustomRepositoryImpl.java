package dev.peermaute.mealsquare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MealCustomRepositoryImpl implements MealCustomRepository{
    MongoTemplate mongoTemplate;

    @Autowired
    public MealCustomRepositoryImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Meal> findMealsByProperties(String name, String carbBase, String ingredient,
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
            criteria.add(Criteria.where("time").lt(maxPrepTime));
        List<Meal> mealList;
        if (!criteria.isEmpty()){
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
            mealList = mongoTemplate.find(query, Meal.class);
        }
        else{
            mealList = mongoTemplate.findAll(Meal.class);
        }
        if(ingredient != null && !ingredient.isEmpty()){
            //Really not performant. Find out later how to create a query for key set of map.
            List<Meal> filteredMealList = new ArrayList<>();
            for(Meal meal: mealList){
                if(meal.getIngredients().containsKey(ingredient)){
                    filteredMealList.add(meal);
                }
            }
            return filteredMealList;
        }
        return mealList;
    }
}