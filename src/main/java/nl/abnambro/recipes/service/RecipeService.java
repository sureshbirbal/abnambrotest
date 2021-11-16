package nl.abnambro.recipes.service;

import nl.abnambro.recipes.exception.RecordNotFoundException;
import nl.abnambro.recipes.model.Ingredient;
import nl.abnambro.recipes.model.Recipe;
import nl.abnambro.recipes.repository.IngredientRepository;
import nl.abnambro.recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    public Recipe createRecipe(Recipe entity) {
            List<Ingredient> ingredients = new ArrayList<>();
            Recipe newRecipe = new Recipe();
            newRecipe.setName(entity.getName());
            newRecipe.setVegan(entity.isVegan());
            newRecipe.setCreated(entity.getCreated());
            newRecipe.setServings(entity.getServings());
            newRecipe.setInstructions(entity.getInstructions());

            for (Ingredient ingredient : entity.getIngredients()) {
                ingredient.setName(ingredient.getName());
                ingredients.add(ingredient);
            }
            newRecipe.setIngredients(ingredients);
            newRecipe = recipeRepository.save(newRecipe);
            return newRecipe;
        }


    public List<Recipe> getAllRecipe() {
        List<Recipe> recipeList = recipeRepository.findAll();
        if (recipeList.size() > 0) {
            return recipeList;
        } else {
            return new ArrayList<Recipe>();
        }
    }

    @GetMapping("/{id}")
    public Recipe getRecipeById(Integer id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()) {
            return recipe.get();
        }
        return null;
    }

    public Recipe update(Recipe recipe) throws RecordNotFoundException {
        Optional<Recipe> recipee = recipeRepository.findById(recipe.getId());
        if (!recipee.isPresent()) {
            throw new RecordNotFoundException(recipe.getName());
        }
        return recipeRepository.save(recipe);
    }

    public void deleteRecipeById(Integer id) throws RecordNotFoundException {
        Optional<Recipe> recipe = recipeRepository.findById(id);

        if (recipe.isPresent()) {
            recipeRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No Recipe record exist for given id");
        }
    }
}
