package dev.peermaute.mealsquare.read.file;

import dev.peermaute.mealsquare.meals.Meal;

import java.util.List;

public interface MealFileReader {
    List<Meal> readFile(String filePath);
}
