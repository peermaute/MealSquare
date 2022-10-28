package dev.peermaute.mealsquare.meals;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends MongoRepository<Meal, String>, MealCustomRepository {
    List<Meal> findByCreatorId(String creatorId);
}
