package dev.peermaute.mealsquare.read.file;

import dev.peermaute.mealsquare.meals.Meal;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Class to read csv files. Each line represents a meal, each column the different attributes (not including "id" and "ingredients").
 * NOTE: This class makes use of the european csv format with ';' as separator instead of ','.
 * <p>
 * Special attributes and their notation:
 * <p>
 * Ingredients:
 * The attribute "ingredients" displays a map. Thus, at this point it is not implemented yet to read this attribute inside a csv file.
 * <p>
 * Tags:
 * As this is the european csv format, just separate different tags with commas.
 */
public class CSVMealFileReader implements MealFileReader {

    /**
     * Separator to separate cells.
     */
    final String separator;

    /**
     * Separator to separate entries in lists (e.g. tag list)
     * NOTE: Must not be the same as "separator".
     */
    final String listSeparator;

    /**
     * Map to represent the order of the attributes in the file. So both attribute orders "name, time, carbBase, tags" and "time, carbBase, name, tags" can be processed.
     * The key is the index of the value in the first line of the file.
     */
    Map<Integer, String> attributeOrderMap;

    public CSVMealFileReader() {
        separator = ";";
        listSeparator = ",";
        attributeOrderMap = new HashMap<>();
    }

    public CSVMealFileReader(String separator, String listSeparator) {
        if(separator.equals(listSeparator)) {
            throw new IllegalArgumentException("Separator arguments must not be the same!");
        }
        this.separator = separator;
        this.listSeparator = listSeparator;
        attributeOrderMap = new HashMap<>();
    }

    @Override
    public List<Meal> readFile(String filePath) {
        List<String[]> lines = readLines(filePath);
        List<Meal> mealList = createMealList(lines);
        return mealList;
    }

    public List<Meal> createMealList(List<String[]> lines) {
        List<Meal> mealList = new ArrayList<>();
        for(int i = 0; i < lines.size(); i++){
            if(i == 0){
                initiateAttributeOrderMap(lines.get(i));
            }
            else{
                mealList.add(createMeal(lines.get(i)));
            }
        }
        return mealList;
    }

    private Meal createMeal(String[] line) {
        Meal meal = new Meal();
        for(int i = 0; i < line.length; i++){
            switch (attributeOrderMap.get(i)){
                case "carbBase":
                    meal.setCarbBase(line[i].equals("") ? null : line[i]);
                    break;
                case "time":
                    meal.setTime(line[i].equals("") ? 99999 : Integer.parseInt(line[i]));
                    break;
                case "tags":
                    meal.setTags(getTagList(line[i]));
                    break;
                case "name":
                    meal.setName(line[i].equals("") ? null : line[i]);
                    break;
            }
        }
        if(meal.getName() == null){
            throw new IllegalArgumentException("Meal name has to be set and can not be empty");
        }
        return meal;
    }

    private List<String> getTagList(String tagLine) {
        String[] tags = tagLine.split(listSeparator);
        List<String> tagList = new ArrayList<>();
        for(String tag: tags){
            tagList.add(tag.trim());
        }
        return tagList;
    }

    private void initiateAttributeOrderMap(String[] firstLine) {
        for(int i = 0; i < firstLine.length; i++){
            checkIfFieldsContainAttribute(firstLine[i]);
            attributeOrderMap.put(i, firstLine[i]);
        }
    }

    private void checkIfFieldsContainAttribute(String entry) {
        Field[] fields = Meal.class.getDeclaredFields();
        for(Field field: fields){
            if(entry.equals(field.getName())){
                return;
            }
        }
        throw new BadFormatException("Column attribute: " + entry +  " is not a field name");
    }

    private List<String[]> readLines(String filePath) {
        File file = new File(filePath);
        List<String[]> lines = new ArrayList<>();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while ((line = br.readLine()) != null) {
                lines.add(line.split(separator));
            }
            br.close();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return lines;
    }
}
