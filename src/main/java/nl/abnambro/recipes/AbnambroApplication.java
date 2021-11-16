package nl.abnambro.recipes;

import nl.abnambro.recipes.model.Ingredient;
import nl.abnambro.recipes.model.Recipe;
import nl.abnambro.recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootApplication
public class AbnambroApplication {
    public static void main(String[] args) {
        SpringApplication.run(AbnambroApplication.class, args);
    }
}



