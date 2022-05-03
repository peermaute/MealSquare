package dev.peermaute.mealsquare;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A meal class.
 * The ingredients are defaulted for two persons. In the frontend this might be changed later.
 */
@Getter
@Setter
public class Meal {
    @Id
    private String id;

    private String name;

    private String carbBase;

    private Map<String, String> ingredients;

    private List<String> tags;

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
        if(name.contains(".") || carbBase.contains(".") || ingredientsContainDots() || tagsContainDots()){
            return true;
        }
        return false;
    }

    private boolean ingredientsContainDots() {
        for(Map.Entry<String, String> entry: ingredients.entrySet()){
            if(entry.getValue().contains(".") || entry.getKey().contains(".")){
                return true;
            }
        }
        return false;
    }

    private boolean tagsContainDots() {
        for(String curEntry: tags){
            if(curEntry.contains(".")){
                return true;
            }
        }
        return false;
    }
}
