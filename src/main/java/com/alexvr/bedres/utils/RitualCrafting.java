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
        PATTERN = LIST OF 7 STRINGS OF SEVEN CHARACTERS EACH [In the pattern the top is west, left is north and right is south... Yes, i know]
        LEYEND = LIST  OF STRINGS EACH STRING BEING 'CHARACTER=<item/block registry name>'

    */



    public static ArrayList generateRecipes(){
        ArrayList list = new ArrayList();
        try {
            FileReader reaser = new FileReader(Minecraft.getInstance().getClass().getClassLoader().getResource("data/bedres/recipes/ritual/ritual_recipes.json").getPath());
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




}
