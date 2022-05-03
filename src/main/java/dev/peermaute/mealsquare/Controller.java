package dev.peermaute.mealsquare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@RestController
public class Controller {

    private MealService mealService;

    @Autowired
    public Controller(MealService mealService){
        this.mealService = mealService;
    }

    @PostMapping(path = "/meal")
    public ResponseEntity<String> newMeal(@RequestBody Meal meal){
        try{
            mealService.newMeal(meal);
            return new ResponseEntity<>("Creation successful", HttpStatus.CREATED);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Creation failed:\n" + e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/hello")
    public ResponseEntity<String> helloWorld(){
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
}
