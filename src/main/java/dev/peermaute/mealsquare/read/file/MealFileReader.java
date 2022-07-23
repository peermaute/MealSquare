package dev.peermaute.mealsquare.read.file;

import dev.peermaute.mealsquare.Meal;

import java.io.File;
import java.util.List;

public interface MealFileReader {
    List<Meal> readFile(String filePath);
}
