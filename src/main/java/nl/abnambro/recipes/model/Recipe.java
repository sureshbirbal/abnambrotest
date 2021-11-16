package nl.abnambro.recipes.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="Recipe_id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="created")
    private LocalDateTime created;
    @Column(name="vegan")
    private boolean vegan;
    @Column(name="servings")
    private int servings;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "Recipe_id",referencedColumnName = "Recipe_id")
    private List<Ingredient> ingredients;
    @Column(name="instructions")

    private String instructions;

    public Recipe() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
