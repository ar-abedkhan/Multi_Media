package com.abedkhan.multimedia.SavedData;

import java.util.ArrayList;
import java.util.List;

public class CategorySavedData {
    public static List<String> categoryList = new ArrayList<>();

    public static void loadCategory() {
        categoryList.add("sci-fi");
        categoryList.add("horror");
        categoryList.add("history");
        categoryList.add("romance");
        categoryList.add("thriller");
        categoryList.add("mystery");
    }

    public static List<String> getCategorySavedData(){
        return categoryList;
    }
}
