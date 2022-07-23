package dev.peermaute.mealsquare;

import dev.peermaute.mealsquare.read.file.CSVMealFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CSVMealFileReaderTests {

    @Autowired
    MealRepository mealRepository;

    CSVMealFileReader csvMealFileReader;

    @BeforeEach
    void setUp(){
        csvMealFileReader = new CSVMealFileReader();
    }

    @Test
    void testRead(){
        List<Meal> mealList = csvMealFileReader.readFile("src/main/resources/MealCSV.CSV");
        System.out.println("re");
    }

    @Test
    void testCreateMealList(){
        List<String[]> lineList = new ArrayList<>();
        String[] line1 = new String[]{"name", "tags", "time", "carbBase"};
        String[] line2 = new String[]{"Tomato sauce noodles", "fast, easy", "20", "noodles"};
        String[] line3 = new String[]{"pizza", "mediterranean", "", ""};
        String[] line4 = new String[]{"curry", "", "", ""};
        lineList.add(line1);
        lineList.add(line2);
        lineList.add(line3);
        lineList.add(line4);
        List<Meal> mealList = csvMealFileReader.createMealList(lineList);
        assertEquals("pizza", mealList.get(1).getName());
        assertEquals(new ArrayList<>(Arrays.asList(new String[]{"fast", "easy"})), mealList.get(0).getTags());
        assertEquals(null, mealList.get(1).getCarbBase());
        assertEquals(99999, mealList.get(2).getTime());
    }

    /*
    @Test
    void testLoad(){
        List<Meal> mealList = csvMealFileReader.readFile("src/main/resources/MealCSV.CSV");
        mealRepository.saveAll(mealList);
    }
     */
}
