package dev.peermaute.mealsquare.meals;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends MongoRepository<Meal, String>, MealCustomRepository {
}
