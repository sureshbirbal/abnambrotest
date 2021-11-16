package nl.abnambro.recipes;

import nl.abnambro.recipes.model.Ingredient;
import nl.abnambro.recipes.model.Recipe;
import nl.abnambro.recipes.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommandRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommandRunner.class);

    @Autowired
    RecipeRepository recipeRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Ingredient> list = new ArrayList<>();
        Ingredient ingredientOne = new Ingredient();
        ingredientOne.setName("1 cup water");
        Ingredient ingredientTwo = new Ingredient();
        ingredientTwo.setName("4 cups bread flour");
        Ingredient ingredientThree = new Ingredient();
        ingredientThree.setName("3 tablespoons white sugar");
        Ingredient ingredientFour = new Ingredient();
        ingredientFour.setName("1 teaspoon salt");
        Ingredient ingredientFive = new Ingredient();
        ingredientFive.setName("2 tablespoons vegetable oil");
        Ingredient ingredientSix = new Ingredient();
        ingredientSix.setName("1 tablespoon instant yeast");
        Ingredient ingredientSeven = new Ingredient();
        ingredientSeven.setName("4 quarts water");

        list.add(ingredientOne);
        list.add(ingredientTwo);
        list.add(ingredientThree);
        list.add(ingredientFour);
        list.add(ingredientFive);
        list.add(ingredientSix);
        list.add(ingredientSeven);

        String instructions =
                "Combine 1 1/4 cup water, flour, sugar, 1 teaspoon salt, vegetable oil, and yeast in the mixing bowl of a stand mixer.";

        Recipe recipe = new Recipe();
        recipe.setName("Real Homemade Bagels");
        recipe.setCreated(LocalDateTime.now());
        recipe.setVegan(true);
        recipe.setServings(4);
        recipe.setIngredients(list);
        recipe.setInstructions(instructions);
        recipeRepository.save(recipe);

        // Second Recipe
        List<Ingredient> list1 = new ArrayList<>();
        Ingredient ingredientSecondOne = new Ingredient();
        ingredientSecondOne.setName("4 cup warm water");

        Ingredient ingredientSecondTwo = new Ingredient();
        ingredientSecondTwo.setName("1 teaspoon salt");

        Ingredient ingredientSecondThree = new Ingredient();
        ingredientSecondThree.setName("1 pinch white sugar");

        Ingredient ingredientSecondFour = new Ingredient();
        ingredientSecondFour.setName("4 cups all-purpose flour");


        list1.add(ingredientSecondOne);
        list1.add(ingredientSecondTwo);
        list1.add(ingredientSecondThree);
        list1.add(ingredientSecondFour);

        String instructions1 =
                "Mix warm water, salt, and sugar together in a bowl. Put flour in a large mixing bowl; work in 3 1/2 tablespoons ghee using your fingertips\n";

        Recipe recipe1 = new Recipe();
        recipe1.setName("Roti Canai/Paratha");
        recipe1.setCreated(LocalDateTime.now());
        recipe1.setVegan(true);
        recipe1.setServings(8);
        recipe1.setIngredients(list1);
        recipe1.setInstructions(instructions1);
        recipeRepository.save(recipe1);

    }
}