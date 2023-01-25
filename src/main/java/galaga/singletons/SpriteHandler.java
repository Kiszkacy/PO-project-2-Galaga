package galaga.singletons;

import processing.core.PImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static galaga.util.EasyPrint.p;

public class SpriteHandler {

    private static final SpriteHandler instance = new SpriteHandler();
    private Map<String, ArrayList<PImage>> sprites;


    public static void init() {
        instance.sprites = new HashMap<>();
    }


    public static boolean registerSprite(String name, PImage sprite) {
        ArrayList<PImage> targetArray = instance.sprites.get(name);
        if (targetArray == null) {
            instance.sprites.put(name, new ArrayList<>());
            targetArray = instance.sprites.get(name);
        }
        targetArray.add(sprite); // TODO check if not already in
        return true;
    }


    public static ArrayList<PImage> getSprite(String name) {
        return instance.sprites.get(name);
    }

    // constructors

    private SpriteHandler() { // no one is able to create another instance of this object

    }

    // getters/setters

    public static Map<String, ArrayList<PImage>> getSprites() {
        return instance.sprites;
    }
}
