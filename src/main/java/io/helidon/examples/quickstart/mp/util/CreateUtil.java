package io.helidon.examples.quickstart.mp.util;

import java.util.concurrent.ThreadLocalRandom;

import io.helidon.examples.quickstart.mp.pojo.Pokemon;

public class CreateUtil {

    public static Pokemon createBasePokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(getRandomId());
        pokemon.setName(Constants.YELLOWNAME);
        pokemon.setType(Constants.TYPE);
        pokemon.setArge(getRandom());
        return pokemon;
    }
    
    public static Pokemon createPokemon(String id , String name , String type, int arge) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(id);
        pokemon.setName(name);
        pokemon.setType(type);
        pokemon.setArge(arge);
        return pokemon;
    }


    private static int getRandom() {
        return ThreadLocalRandom.current().nextInt();
    }

    private static String getRandomId() {
        return String.valueOf(ThreadLocalRandom.current().nextInt());
    }
}

