package dev.peermaute.mealsquare;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Meal {
    @Id
    private String id;

    private String name;

    //TODO: not int but String (100gram, 1 Apple)
    //TODO: Configure dot replacement
    private Map<String, Integer> ingredients;
    //TODO: carb base
    private List<String> tags;

    //TODO: number of people

    public Meal(){
        ingredients = new HashMap<>();
        tags = new ArrayList<>();
    }

}
