package supersymmetry.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HeightProperty extends RecipeProperty<HeightProperty.HeightPropertyList> {

    public static final String KEY = "ylevel";

    private static HeightProperty INSTANCE;

    private HeightProperty() {
        super(KEY, HeightPropertyList.class);
    }

    public static HeightProperty getInstance() {
        if (INSTANCE == null)
            INSTANCE = new HeightProperty();
        return INSTANCE;
    }

    public static String getHeightsForRecipe() {
        StringBuilder builder = new StringBuilder();
        if (HeightPropertyList.minYLevel > -1000 && HeightPropertyList.maxYLevel < 1000)
            builder.append("Y > " + HeightPropertyList.minYLevel + ", Y < " + HeightPropertyList.maxYLevel);
        else if (HeightPropertyList.minYLevel > -1000)
            builder.append("Y > " + HeightPropertyList.minYLevel);
        else builder.append("Y < " + HeightPropertyList.maxYLevel);
        return builder.toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        //HeightPropertyList propertyValue = castValue(value);
        String heightLimits = I18n.format(getHeightsForRecipe());
        minecraft.fontRenderer.drawString(I18n.format("susy.recipe.heights", heightLimits), x, y, color);
    }

    public static class HeightPropertyList {

        public static int minYLevel = 1000;
        public static int maxYLevel = -1000;

        public HeightPropertyList(int min, int max) {
            minYLevel = min;
            maxYLevel = max;
        }

        public boolean checkHeight(int ylevel) {
            boolean valid = true;
            if (ylevel > maxYLevel || ylevel < minYLevel) valid = false;
            return valid;
        }
    }
}
