package dev.peermaute.mealsquare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller creates a REST API to access the MealSquare database.
 */
@CrossOrigin("*")
@RestController
public class Controller {

    /**
     * The controller accesses the database indirectly through the MealService.
     */
    private MealService mealService;

    @Autowired
    public Controller(MealService mealService){
        this.mealService = mealService;
    }

    /**
     * Returns the meal with the given id.
     * @param id
     * @return
     */
    @GetMapping(path = "/meals/{id}")
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

    /**
     * Creates a new meal in the database.
     * This request body should contain a Meal in JSON format.
     * @param meal
     * @return
     */
    @PostMapping(path = "/meals")
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

    /**
     * Deletes the meal with the given id from the database.
     * @param id
     * @return
     */
    @DeleteMapping(path = "/meals/{id}")
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

    /**
     * Updates the meal with the given id. The request body should consist of a Meal in JSON format with the fields that should be updated.
     * @param id
     * @param meal
     * @return
     */
    @PutMapping(path = "/meals/{id}")
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

    /**
     * Returns the meals in the database that match the filter.
     * @param filter
     * @return
     */
    @PostMapping(path = "/meals/filters")
    public ResponseEntity<List<Meal>> getMealsFiltered(@RequestBody Filter filter){
        return new ResponseEntity<>(mealService.fetchStudentsByProperties(filter), HttpStatus.OK);
    }

    /**
     * Returns a meal plan (List of meals) for a number of days.
     * The number of days can be specified as a request parameter of the request ("days"). The default value is 5.
     */
    @PostMapping(path = "/meals/mealPlan")
    public ResponseEntity<?> getMealPlan(@RequestParam(name = "days", required = false, defaultValue = "5") Integer days, @RequestBody Filter filter){
        try{
            return new ResponseEntity<>(mealService.getMealPlan(filter, days), HttpStatus.OK);
        }
        catch (IllegalArgumentException iae){
            return new ResponseEntity<>(iae.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
