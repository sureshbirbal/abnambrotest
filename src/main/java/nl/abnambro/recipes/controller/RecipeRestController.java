package nl.abnambro.recipes.controller;

import nl.abnambro.recipes.exception.RecordNotFoundException;
import nl.abnambro.recipes.model.Recipe;
import nl.abnambro.recipes.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeRestController {

    private static final Logger logger = LoggerFactory.getLogger(RecipeRestController.class);

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<Recipe>> fetchAllRecipes() throws RecordNotFoundException {
        logger.info("Request to get all recipes.");
        return new ResponseEntity<>(recipeService.getAllRecipe(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe updated = recipeService.createRecipe(recipe);
        return new ResponseEntity<Recipe>(updated, new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> fetchTraderById(@PathVariable Integer id) throws RecordNotFoundException {
        logger.info("Request to get one recipe with id: {}", id);
        Recipe entity = recipeService.getRecipeById(id);
        if (entity == null) {
            throw new RecordNotFoundException("Record Not Found");
        }
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Recipe update(@RequestBody final Recipe entity) throws RecordNotFoundException{
        logger.info("Request to update a recipe");
        return recipeService.update(entity);
    }


    @DeleteMapping("/{id}")
    public HttpStatus deleteRecipeById(@PathVariable("id") Integer id)
            throws RecordNotFoundException {
        logger.info("Request to delete a recipe");
        recipeService.deleteRecipeById(id);
        return HttpStatus.OK;
    }

    @ExceptionHandler({RecordNotFoundException.class})
    ResponseEntity<String> handleNotFounds(Exception e) {
        logger.error(e.getMessage());
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
