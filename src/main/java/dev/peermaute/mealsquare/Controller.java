package dev.peermaute.mealsquare;

import dev.peermaute.mealsquare.authentication.FirebaseAuthenticationHandler;
import dev.peermaute.mealsquare.meals.Filter;
import dev.peermaute.mealsquare.meals.Meal;
import dev.peermaute.mealsquare.meals.MealService;
import dev.peermaute.mealsquare.users.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final MealService mealService;

    /**
     * The controller accesses the database indirectly through the AdminUserService.
     */
    private final AdminUserService adminUserService;

    /**
     * Handles authentication requests
     */
    private final FirebaseAuthenticationHandler authenticationHandler;

    @Autowired
    public Controller(MealService mealService, AdminUserService adminUserService, FirebaseAuthenticationHandler authenticationHandler){
        this.mealService = mealService;
        this.adminUserService = adminUserService;
        this.authenticationHandler = authenticationHandler;
    }

    /**
     * Welcome Page
     */
    @GetMapping(path = "")
    public ResponseEntity<?> getHome(){
        return new ResponseEntity<>("Welcome to MealSquare!\n" +
                "Visit https://github.com/peermaute/MealSquare for more info <3", HttpStatus.OK);
    }

    /**
     * Returns the meal with the given id.
     */
    @GetMapping(path = "/meals/{id}")
    public ResponseEntity<?> getMeal(@PathVariable String id, @RequestHeader("Authorization") String token){
        try{
            String bearerToken = authenticationHandler.getBearerToken(token);
            if (!authenticationHandler.verifyToken(bearerToken)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            mealService.getMeal(id);
            return new ResponseEntity<>(mealService.getMeal(id), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("GET Request failed:\n" + e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Creates a new meal in the database.
     * This request body should contain a Meal in JSON format.
     */
    @PostMapping(path = "/meals")
    public ResponseEntity<String> newMeal(@RequestBody Meal meal, @RequestHeader("Authorization") String token){
        try{
            String bearerToken = authenticationHandler.getBearerToken(token);
            if (!authenticationHandler.verifyToken(bearerToken)) {
                return new ResponseEntity<>("Creation failed - unauthorized", HttpStatus.UNAUTHORIZED);
            }
            meal.setCreatorId(authenticationHandler.getUid(bearerToken));
            mealService.newMeal(meal);
            return new ResponseEntity<>("Creation successful", HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>("Creation failed:\n" + e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes the meal with the given id from the database.
     */
    @DeleteMapping(path = "/meals/{id}")
    public ResponseEntity<String> deleteMeal(@PathVariable String id, @RequestHeader("Authorization") String token){
        try{
            String bearerToken = authenticationHandler.getBearerToken(token);
            if (!authenticationHandler.verifyToken(bearerToken)) {
                return new ResponseEntity<>("Deletion failed - unauthorized", HttpStatus.UNAUTHORIZED);
            }
            String userId = authenticationHandler.getUid(bearerToken);
            if(adminUserService.isAdminUser(userId) || mealService.getMeal(id).getCreatorId().equals(userId)){
                mealService.deleteMeal(id);
                return new ResponseEntity<>("Deletion successful", HttpStatus.OK);
            }
            return new ResponseEntity<>("Deletion failed - User unauthorized", HttpStatus.UNAUTHORIZED);
        }
        catch(Exception e){
            return new ResponseEntity<>("Deletion failed:\n" + e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates the meal with the given id. The request body should consist of a Meal in JSON format with the fields that should be updated.
     */
    @PutMapping(path = "/meals/{id}")
    public ResponseEntity<String> updateMeal(@PathVariable String id, @RequestBody Meal meal, @RequestHeader("Authorization") String token){
        try{
            String bearerToken = authenticationHandler.getBearerToken(token);
            if (!authenticationHandler.verifyToken(bearerToken)) {
                return new ResponseEntity<>("Update failed - unauthorized", HttpStatus.UNAUTHORIZED);
            }
            String userId = authenticationHandler.getUid(bearerToken);
            if(adminUserService.isAdminUser(userId) || mealService.getMeal(id).getCreatorId().equals(userId)){
                mealService.updateMeal(id, meal);
                return new ResponseEntity<>("Update successful", HttpStatus.OK);
            }
            return new ResponseEntity<>("Update failed - User unauthorized", HttpStatus.UNAUTHORIZED);
        }
        catch(Exception e){
            return new ResponseEntity<>("Update failed:\n" + e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates the meal with the given id. The request body should consist of a Meal in JSON format with the fields that should be updated.
     */
    @PostMapping(path = "/meals/{id}/image")
    public ResponseEntity<String> newMealImage(@PathVariable String id, @RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token){
        try{
            String bearerToken = authenticationHandler.getBearerToken(token);
            if (!authenticationHandler.verifyToken(bearerToken)) {
                return new ResponseEntity<>("Upload failed - unauthorized", HttpStatus.UNAUTHORIZED);
            }
            String userId = authenticationHandler.getUid(bearerToken);
            if(adminUserService.isAdminUser(userId) || mealService.getMeal(id).getCreatorId().equals(userId)){
                mealService.setImage(id, file);
                return new ResponseEntity<>("Upload successful", HttpStatus.OK);
            }
            return new ResponseEntity<>("Upload failed - User unauthorized", HttpStatus.UNAUTHORIZED);
        }
        catch(Exception e){
            return new ResponseEntity<>("Upload failed:\n" + e, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns the meals in the database that match the filter.
     */
    @PostMapping(path = "/meals/filters")
    public ResponseEntity<List<Meal>> getMealsFiltered(@RequestBody Filter filter, @RequestHeader("Authorization") String token){
        String bearerToken = authenticationHandler.getBearerToken(token);
        if (!authenticationHandler.verifyToken(bearerToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(mealService.fetchMealsByProperties(filter), HttpStatus.OK);
    }

    /**
     * Returns a meal plan (List of meals) for a number of days.
     * The number of days can be specified as a request parameter of the request ("days"). The default value is 5.
     */
    @PostMapping(path = "/meals/mealPlan")
    public ResponseEntity<?> getMealPlan(@RequestParam(name = "days", required = false, defaultValue = "5") Integer days, @RequestBody Filter filter, @RequestHeader("Authorization") String token){
        try{
            String bearerToken = authenticationHandler.getBearerToken(token);
            if (!authenticationHandler.verifyToken(bearerToken)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(mealService.getMealPlan(filter, days), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
