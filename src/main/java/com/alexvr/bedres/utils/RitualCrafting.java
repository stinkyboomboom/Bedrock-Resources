package com.alexvr.bedres.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import net.minecraft.client.Minecraft;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RitualCrafting {

    /**

     the recipe is 3 elements
        RESULT = <item/block registry name> | String
        PATTERN = LIST OF 7 STRINGS OF SEVEN CHARACTERS EACH
        LEYEND = LIST  OF STRINGS EACH STRING BEING 'CHARACTER=<item/block registry name>'

    */

    public static ArrayList generateRecipes(){
//        return new ArrayList(){{
//
//            add(DIAMOND_PICKAXE_UPGRADE);
//            add(GOLD_PICKAXE_UPGRADE);
//            add(IRON_PICKAXE_UPGRADE);
//            add(STONE_PICKAXE_UPGRADE);
//            add(WOOD_PICKAXE_UPGRADE);
//
//            add(DIAMOND_AXE_UPGRADE);
//            add(GOLD_AXE_UPGRADE);
//            add(IRON_AXE_UPGRADE);
//            add(STONE_AXE_UPGRADE);
//            add(WOOD_AXE_UPGRADE);
//
//            add(DIAMOND_SHOVEL_UPGRADE);
//            add(GOLD_SHOVEL_UPGRADE);
//            add(IRON_SHOVEL_UPGRADE);
//            add(STONE_SHOVEL_UPGRADE);
//            add(WOOD_SHOVEL_UPGRADE);
//
//            add(DIAMOND_SWORD_UPGRADE);
//            add(GOLD_SWORD_UPGRADE);
//            add(IRON_SWORD_UPGRADE);
//            add(STONE_SWORD_UPGRADE);
//            add(WOOD_SWORD_UPGRADE);
//
//            add(ACTIVE_HOE_UPGRADE);
//
//            add(ACTIVE_SPEED_UPGRADE);
//
//            add(ACTIVE_JUMP_UPGRADE);
//
//            add(CLEAR_UPGRADE);
//
//        }};
        ArrayList list = new ArrayList();
        try {
            FileReader reaser = new FileReader(Minecraft.getInstance().getClass().getClassLoader().getResource("data/bedres/recipes/ritual_recipes.json").getPath());
            Gson jsonParser = new Gson();
            JsonReader jsonReader = jsonParser.newJsonReader(reaser);
            try {
                return checkJson(jsonReader,list);
            } finally {
                jsonReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return list;
    }

    private static ArrayList checkJson(JsonReader reader,ArrayList list) throws IOException {

        reader.beginObject();
        while (reader.hasNext()) {
            list.add(readElements(reader,list));
        }
        reader.endObject();
        return list;
    }
    public static ArrayList readElements(JsonReader reader,ArrayList list) throws IOException {
        ArrayList list2 = new ArrayList();
        String result = "";
        ArrayList pattern = new ArrayList();
        ArrayList keys = new ArrayList();
        reader.nextName();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("result")) {
                result = reader.nextString();
            } else if (name.equals("pattern")) {
                pattern = readStringArray(reader);
            } else if (name.equals("key") && reader.peek() != JsonToken.NULL) {
                keys = readStringArray(reader);
            } else {
                reader.skipValue();
            }
        }
        list2.add(result);
        list2.add(pattern);
        list2.add(keys);
        reader.endObject();
        return list2;
    }

    public static ArrayList<String> readStringArray(JsonReader reader) throws IOException {
        ArrayList<String> doubles = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            doubles.add(reader.nextString());
        }
        reader.endArray();
        return doubles;
    }

//in the pattern the top is west, left is north adn right is south
//yes, i know
    private static  String  WIRE = "w=bedres:bedrock_scrapes";
    private static  String TOOLACHEMIZER = "r=bedres:" + References.ENDERIAN_INGOT_REGNAME;



    private static ArrayList<String> TOOL_UPGRADE = new ArrayList<String>() {{

        add("wwwpwww");
        add("w  w  w");
        add("w rwr w");
        add("pww wwp");
        add("w rwr w");
        add("w  w  w");
        add("wwwpwww");

    }};

    private static ArrayList getPassiveBoosMaterial(String regName,String Alchemizer){
        return new ArrayList<String>() {{

            add(WIRE);
            add(regName);
            add(Alchemizer);

        }};
    }

    private static ArrayList getUpgradeRecipe(String material, String tool, String alchemizeer){
        return new ArrayList() {{
            if (tool.equals("pickaxe")){
                add("bedres:" + material + "_pickUpgrade");

            }else {
                add("bedres:" + material + "_" + tool + "Upgrade");
            }
            add(TOOL_UPGRADE);
            if (material.equals("na")){
                add(getPassiveBoosMaterial("p=minecraft:" + tool, alchemizeer));
            }else {
                add(getPassiveBoosMaterial("p=minecraft:" + material + "_" + tool, alchemizeer));
            }
        }};
    }

    private static ArrayList getStatRecipe(String name, String item, String alchemizeer){
        return new ArrayList() {{

                add("bedres:" + name + "Upgrade");

            add(TOOL_UPGRADE);
            add(getPassiveBoosMaterial("p=minecraft:" + item, alchemizeer));

        }};
    }

    public static ArrayList CLEAR_UPGRADE = getUpgradeRecipe("na","stick",TOOLACHEMIZER);


    public static ArrayList DIAMOND_PICKAXE_UPGRADE = getUpgradeRecipe("diamond","pickaxe",TOOLACHEMIZER);
    public static ArrayList GOLD_PICKAXE_UPGRADE = getUpgradeRecipe("golden","pickaxe",TOOLACHEMIZER);
    public static ArrayList IRON_PICKAXE_UPGRADE = getUpgradeRecipe("iron","pickaxe",TOOLACHEMIZER);
    public static ArrayList STONE_PICKAXE_UPGRADE = getUpgradeRecipe("stone","pickaxe",TOOLACHEMIZER);
    public static ArrayList WOOD_PICKAXE_UPGRADE = getUpgradeRecipe("wooden","pickaxe",TOOLACHEMIZER);

    public static ArrayList DIAMOND_AXE_UPGRADE = getUpgradeRecipe("diamond","axe",TOOLACHEMIZER);
    public static ArrayList GOLD_AXE_UPGRADE = getUpgradeRecipe("golden","axe",TOOLACHEMIZER);
    public static ArrayList IRON_AXE_UPGRADE = getUpgradeRecipe("iron","axe",TOOLACHEMIZER);
    public static ArrayList STONE_AXE_UPGRADE = getUpgradeRecipe("stone","axe",TOOLACHEMIZER);
    public static ArrayList WOOD_AXE_UPGRADE = getUpgradeRecipe("wooden","axe",TOOLACHEMIZER);

    public static ArrayList DIAMOND_SHOVEL_UPGRADE = getUpgradeRecipe("diamond","shovel",TOOLACHEMIZER);
    public static ArrayList GOLD_SHOVEL_UPGRADE = getUpgradeRecipe("golden","shovel",TOOLACHEMIZER);
    public static ArrayList IRON_SHOVEL_UPGRADE = getUpgradeRecipe("iron","shovel",TOOLACHEMIZER);
    public static ArrayList STONE_SHOVEL_UPGRADE = getUpgradeRecipe("stone","shovel",TOOLACHEMIZER);
    public static ArrayList WOOD_SHOVEL_UPGRADE = getUpgradeRecipe("wooden","shovel",TOOLACHEMIZER);

    public static ArrayList DIAMOND_SWORD_UPGRADE = getUpgradeRecipe("diamond","sword",TOOLACHEMIZER);
    public static ArrayList GOLD_SWORD_UPGRADE = getUpgradeRecipe("golden","sword",TOOLACHEMIZER);
    public static ArrayList IRON_SWORD_UPGRADE = getUpgradeRecipe("iron","sword",TOOLACHEMIZER);
    public static ArrayList STONE_SWORD_UPGRADE = getUpgradeRecipe("stone","sword",TOOLACHEMIZER);
    public static ArrayList WOOD_SWORD_UPGRADE = getUpgradeRecipe("wooden","sword",TOOLACHEMIZER);

    public static ArrayList ACTIVE_HOE_UPGRADE = getUpgradeRecipe("diamond","hoe",TOOLACHEMIZER);

    public static ArrayList ACTIVE_SPEED_UPGRADE = new ArrayList() {{

        add("bedres:speedUpgrade");

        add(new ArrayList<String>() {{

            add("wwwbwww");
            add("w  w  w");
            add("w rwr w");
            add("aww wwc");
            add("w rwr w");
            add("w  w  w");
            add("wwwdwww");

        }});
        add(new ArrayList<String>() {{

            add(WIRE);
            add("r=bedres:" + References.ENDERIAN_INGOT_REGNAME);
            add("a=minecraft:sugar");
            add("b=minecraft:cookie");
            add("c=bedres:" + References.FLUXED_CUPCAKE_REGNAME);
            add("d=minecraft:redstone");

        }});

    }};

    public static ArrayList ACTIVE_JUMP_UPGRADE = new ArrayList() {{

        add("bedres:jumpUpgrade");

        add(new ArrayList<String>() {{

            add("wwwbwww");
            add("w  w  w");
            add("w rwr w");
            add("aww wwa");
            add("w rwr w");
            add("w  w  w");
            add("wwwbwww");

        }});
        add(new ArrayList<String>() {{

            add(WIRE);
            add("r=bedres:" + References.ENDERIAN_INGOT_REGNAME);
            add("a=minecraft:rabbit_foot");
            add("b=minecraft:piston");


        }});

    }};



}
