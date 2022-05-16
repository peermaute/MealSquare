package dev.peermaute.mealsquare;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A meal class.
 * The ingredients are defaulted for two persons. In the frontend this might be changed later.
 */
@Document
@Data
public class Meal {

    @Id
    private String id;

    /**
     * The name of the meal
     */
    private String name;

    /**
     * The carb base of the meal -  for example noodles, bread or rice.
     */
    private String carbBase;

    /**
     * The ingredients for the meal - note that these should be set to the amount for a meal for 2 persons.
     */
    private Map<String, String> ingredients;

    /**
     * tags for the meal to make the search experience better - like "mediterranean".
     */
    private List<String> tags;

    /**
     * Time you need for preparation and cooking - in minutes
     */
    private int time;

    public Meal(){
        ingredients = new HashMap<>();
        tags = new ArrayList<>();
    }

    //TODO: Maybe also special character? not only dots
    /**
     * MongoDb treats dots as special characters - might be able to reconfigure dots, but just checking and not allowing it is probably the safer option.
     * https://stackoverflow.com/questions/39785004/mongodb-escape-dots-in-map-key
     * @return
     */
    public boolean containsDots(){
        if(nameContainsDots() || carbBaseContainsDots() || ingredientsContainDots() || tagsContainDots()){
            return true;
        }
        return false;
    }

    private boolean nameContainsDots(){
        if(name == null){
            return false;
        }
        return name.contains(".");
    }

    private boolean carbBaseContainsDots(){
        if(carbBase == null){
            return false;
        }
        return carbBase.contains(".");
    }

    private boolean ingredientsContainDots() {
        if(ingredients == null){
            return false;
        }
        for(Map.Entry<String, String> entry: ingredients.entrySet()){
            if(entry.getValue().contains(".") || entry.getKey().contains(".")){
                return true;
            }
        }
        return false;
    }

    private boolean tagsContainDots() {
        if(tags == null){
            return false;
        }
        for(String curEntry: tags){
            if(curEntry.contains(".")){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(obj.getClass() != this.getClass() ){
            return false;
        }
        final Meal otherMeal = (Meal) obj;
        if(otherMeal.getId().equals(this.id)){
            return true;
        }
        return false;
    }
}
