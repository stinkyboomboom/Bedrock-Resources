package com.alexvr.bedres;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {

    public static final String CATEGORY_GENERAL = "general";
    public static final String GRAVITY_BUBBLE = "gravity_bubble";
    public static final String PEDESTAL_CRAFTING = "pendestal_crafting";
    public static final String RITUAL_CRAFTING = "ritual_crafting";
    public static final String PLAYER_ABILITY = "player_ability";
    public static final String FLUX_CAPABILITY = "flux_cap";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER  = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;


    public static ForgeConfigSpec.IntValue GRAVITY_BUBBLE_MAX_X;
    public static ForgeConfigSpec.IntValue GRAVITY_BUBBLE_MAX_Y;
    public static ForgeConfigSpec.IntValue GRAVITY_BUBBLE_MAX_Z;
    public static ForgeConfigSpec.IntValue GRAVITY_BUBBLE_TICKS_PER_CHECK;

    public static ForgeConfigSpec.IntValue PEDESTAL_TICKS_PER_ITEM;

    public static ForgeConfigSpec.IntValue RITUAL_TICKS_PER_ITEM;

    public static ForgeConfigSpec.ConfigValue<String> DEF_PICK;
    public static ForgeConfigSpec.ConfigValue<String> DEF_AXE;
    public static ForgeConfigSpec.ConfigValue<String> DEF_SHOVEL;
    public static ForgeConfigSpec.ConfigValue<String> DEF_SWORD;
    public static ForgeConfigSpec.ConfigValue<String> DEF_HOE;
    public static ForgeConfigSpec.DoubleValue DEF_JUMP;
    public static ForgeConfigSpec.DoubleValue DEF_SPEED;

    public static ForgeConfigSpec.IntValue DEF_MAX_TIMER;
    public static ForgeConfigSpec.DoubleValue DEF_MAX_FLUX;
    public static ForgeConfigSpec.BooleanValue NEG_EFFECTS_ACTIVE;

    static {

        COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

        setupGravBubbleConfig();
        setupPedestalConfig();
        setupRitualConfig();
        setupPlayerAbilityConfig();
        setupFluxConfig();

        COMMON_BUILDER.pop();


        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupGravBubbleConfig() {
        COMMON_BUILDER.comment("Gravity Bubble settings").push(GRAVITY_BUBBLE);

        GRAVITY_BUBBLE_MAX_X = COMMON_BUILDER.comment("Maximum blocks to extend the gravity bubble by default on X.")
                .defineInRange("grav_max_x", 4, 1, 32);
        GRAVITY_BUBBLE_MAX_Y = COMMON_BUILDER.comment("Maximum blocks to extend the gravity bubble by default on Y.")
                .defineInRange("grav_max_y", 4, 1, 32);
        GRAVITY_BUBBLE_MAX_Z = COMMON_BUILDER.comment("Maximum blocks to extend the gravity bubble by default on Z.")
                .defineInRange("grav_max_z", 4, 1, 32);
        GRAVITY_BUBBLE_TICKS_PER_CHECK = COMMON_BUILDER.comment("How many ticks until the gravity bubble checks for players. (20 ticks = 1 second)")
                .defineInRange("ticks_per_check", 2, 1, Integer.MAX_VALUE);

        COMMON_BUILDER.pop();
    }

    private static void setupPedestalConfig() {
        COMMON_BUILDER.comment("Pedestal Crafting settings").push(PEDESTAL_CRAFTING);

        PEDESTAL_TICKS_PER_ITEM = COMMON_BUILDER.comment("Ticks per item the pedestal will take crafting. (20 ticks = 1 second)")
                .defineInRange("altar_ticks_per_craft", 30, 1, Integer.MAX_VALUE);


        COMMON_BUILDER.pop();
    }

    private static void setupRitualConfig() {
        COMMON_BUILDER.comment("Ritual Crafting settings").push(RITUAL_CRAFTING);

        RITUAL_TICKS_PER_ITEM = COMMON_BUILDER.comment("Ticks per item the ritual will take crafting. (20 ticks = 1 second)")
                .defineInRange("ritual_ticks_per_craft", 120, 1, Integer.MAX_VALUE);


        COMMON_BUILDER.pop();
    }

    private static void setupPlayerAbilityConfig() {
        COMMON_BUILDER.comment("Player Ability settings").push(PLAYER_ABILITY);

        DEF_PICK = COMMON_BUILDER.comment("Default Pickaxe on player.")
                .define("pick", "no");
        DEF_AXE = COMMON_BUILDER.comment("Default Axe on player.")
                .define("axe", "no");
        DEF_SHOVEL = COMMON_BUILDER.comment("Default Shovel on player.")
                .define("shovel", "no");
        DEF_SWORD = COMMON_BUILDER.comment("Default Sword on player.")
                .define("sword", "no");
        DEF_HOE = COMMON_BUILDER.comment("Default Hoe on player. (no or active)")
                .define("hoe", "no");
        DEF_JUMP = COMMON_BUILDER.comment("Default Jump Height (values used in game [0.2,0.015f,0.08f,0.15f,0.25f])")
                .defineInRange("jump", 0, 0, Double.MAX_VALUE);
        DEF_SPEED = COMMON_BUILDER.comment("Default Mining Speed Boost (in game it goes .3 at a time up to 1.35)")
                .defineInRange("speed", 0, 0, Double.MAX_VALUE);


        COMMON_BUILDER.pop();
    }

    private static void setupFluxConfig() {
        COMMON_BUILDER.comment("Bedrock-Flux Capabilities settings").push(FLUX_CAPABILITY);

        DEF_MAX_FLUX = COMMON_BUILDER.comment("Max Bedrock-Flux a player can have")
                .defineInRange("max_flu", 2500.0, 1, Double.MAX_VALUE);

        DEF_MAX_TIMER = COMMON_BUILDER.comment("Ticks per check for negative effect (20 ticks = 1 second, 1200 ticks = 1 minute)")
                .defineInRange("max_timer_for_check", 6000, 1, Integer.MAX_VALUE);

        NEG_EFFECTS_ACTIVE = COMMON_BUILDER.comment("Should bedrockFlux give debufs and negative effects")
                .define("neg_effects", true);


        COMMON_BUILDER.pop();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {


    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
    }

}
