package supersymmetry.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import org.jetbrains.annotations.NotNull;
import supersymmetry.api.recipes.properties.HeightProperty;
import supersymmetry.api.recipes.properties.HeightProperty.HeightPropertyList;

/**
 * A custom recipeBuilder, for allowing us to put our own recipeProperty {@link HeightProperty} into a recipe
 */
public class HeightRecipeBuilder extends RecipeBuilder<HeightRecipeBuilder> {

    public HeightRecipeBuilder() {
    }

    public HeightRecipeBuilder(Recipe recipe, RecipeMap<HeightRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public HeightRecipeBuilder(RecipeBuilder<HeightRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public HeightRecipeBuilder copy() {
        return new HeightRecipeBuilder(this);
    }

    /**
     * Actually putting the recipe property into the propertyStorage
     * Can't put value directly since it's a pointer
     * So we just do a copy over
     */
    @Override
    public boolean applyProperty(@NotNull String key, Object value) {
        return super.applyProperty(key, value);
    }

    public HeightRecipeBuilder yLevelMin(int min) {
        return yLevel(min, 1000); //I assume that no one is crazy enough to increase world height to 1k
    }
    public HeightRecipeBuilder yLevelMax(int max) {
        return yLevel(-1000, max);
    }

    public HeightRecipeBuilder yLevel(int min, int max) {
        this.applyProperty(HeightProperty.getInstance(), new HeightProperty.HeightPropertyList(min,max));
        return this;
    }
}
