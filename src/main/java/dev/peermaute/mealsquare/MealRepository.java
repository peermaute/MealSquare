package dev.peermaute.mealsquare;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MealRepository extends MongoRepository<Meal, String> {
}
