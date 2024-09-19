package supersymmetry.api.recipes.properties;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.recipeproperties.GasCollectorDimensionProperty;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import supersymmetry.common.metatileentities.multi.electric.MetaTileEntityBlender;

import java.util.ArrayList;
import java.util.List;

public class BiomeProperty extends RecipeProperty<BiomeProperty.BiomePropertyList> {

    public static final String KEY = "biome";

    private static BiomeProperty INSTANCE;

    private BiomeProperty() {
        super(KEY, BiomePropertyList.class);
    }

    public static BiomeProperty getInstance() {
        if (INSTANCE == null)
            INSTANCE = new BiomeProperty();
        return INSTANCE;
    }

    /**
     * Just converting a list<Biome> to a String
     */
    private static String getBiomesForRecipe(List<Biome> value) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < value.size(); i++) {
            builder.append(value.get(i).getBiomeName());
            if (i != value.size() - 1)
                builder.append(", ");
        }
        String str = builder.toString();

        if (str.length() >= 13) {
            str = str.substring(0, 10) + "..";
        }
        return str;
    }

    /**
     * For drawing infos (mostly texts) in JEI recipe page
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        BiomePropertyList list = castValue(value);

        if (list.whiteListBiomes.size() > 0)
            minecraft.fontRenderer.drawString(I18n.format("susy.recipe.biomes",
                    getBiomesForRecipe(castValue(value).whiteListBiomes)), x, y, color);
        if (list.blackListBiomes.size() > 0)
            minecraft.fontRenderer.drawString(I18n.format("susy.recipe.biomes_blocked",
                    getBiomesForRecipe(castValue(value).blackListBiomes)), x, y, color);
    }

    /**
     * This class is for storing information of desired/undesired biomes
     *
     * @see GasCollectorDimensionProperty
     * We can actually use biome id for this, I originally wanted this to support {@link BiomeDictionary}, but I gave up.
     * @see Biome#getIdForBiome(Biome)
     * @see Biome#getBiomeForId(int)
     */
    public static class BiomePropertyList {

        public static BiomePropertyList EMPTY_LIST = new BiomePropertyList();

        public List<Biome> whiteListBiomes = new ArrayList<>();
        public List<Biome> blackListBiomes = new ArrayList<>();

        public void addBiome(Biome biome, boolean toBlacklist) {
            if (toBlacklist) {
                blackListBiomes.add(biome);
                whiteListBiomes.remove(biome);
            } else {
                whiteListBiomes.add(biome);
                blackListBiomes.remove(biome);
            }
        }

        public void merge(BiomePropertyList list) {
            this.whiteListBiomes.addAll(list.whiteListBiomes);
            this.blackListBiomes.addAll(list.blackListBiomes);
        }

        /**
         * Checking whether a biome satisfies the requirement, one can change the logic here if he wants
         *
         * @see MetaTileEntityBlender.BiomeRecipeLogic#checkBiomeRequirement(Recipe)
         */
        public boolean checkBiome(Biome biome) {
            boolean valid = true;
            if (this.blackListBiomes.size() > 0) valid = !this.blackListBiomes.contains(biome);
            if (this.whiteListBiomes.size() > 0) valid = this.whiteListBiomes.contains(biome);
            return valid;
        }
    }
}
