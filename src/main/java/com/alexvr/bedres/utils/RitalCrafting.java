package com.alexvr.bedres.utils;

import java.util.ArrayList;

class RitalCrafting {



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

    public static ArrayList ACTIVE_SPEED_UPGRADE = getUpgradeRecipe("na","speed",TOOLACHEMIZER);

    public static ArrayList ACTIVE_JUMP_UPGRADE = getUpgradeRecipe("na","jump",TOOLACHEMIZER);

    public static ArrayList ACTIVE_GRAVITY_UPGRADE = getUpgradeRecipe("na","gravity",TOOLACHEMIZER);


}
