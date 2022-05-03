package dev.peermaute.mealsquare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
public class Controller {

    private MealService mealService;

    @Autowired
    public Controller(MealService mealService){
        this.mealService = mealService;
    }

    @GetMapping(path = "/hello")
    public ResponseEntity<String> helloWorld(){
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }

    @GetMapping(path = "/meal/{id}")
    public ResponseEntity<?> getMeal(@PathVariable String id){
        try{
            mealService.getMeal(id);
            return new ResponseEntity<>(mealService.getMeal(id), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("GET Request failed:\n" + e, HttpStatus.BAD_REQUEST);
        }
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

    @DeleteMapping(path = "/meal/{id}")
    public ResponseEntity<String> deleteMeal(@PathVariable String id){
        try{
            mealService.deleteMeal(id);
            return new ResponseEntity<>("Deletion successful", HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Deletion failed:\n" + e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/meal/{id}")
    public ResponseEntity<String> updateMeal(@PathVariable String id, @RequestBody Meal meal){
        try{
            mealService.updateMeal(id, meal);
            return new ResponseEntity<>("Update successful", HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Update failed:\n" + e, HttpStatus.BAD_REQUEST);
        }
    }
}
