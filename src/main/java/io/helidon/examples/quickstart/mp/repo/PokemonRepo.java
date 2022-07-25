package io.helidon.examples.quickstart.mp.repo;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import io.helidon.examples.quickstart.mp.pojo.Pokemon;
import io.helidon.examples.quickstart.mp.util.CreateUtil;


public class PokemonRepo {
    private ConcurrentHashMap<String, Pokemon> pokemonRepo = new ConcurrentHashMap<>();
    // This heap can be used in case if we need to reduce the latency and
	/*
	 * PriorityQueue<Pokemon> maxQueue = new PriorityQueue<>((t1, t2) ->
	 * t2.getArge() - t1.getArge()); PriorityQueue<Pokemon> minQueue = new
	 * PriorityQueue<>(Comparator.comparingInt(Pokemon::getArge));
	 */

    public PokemonRepo() {
        Pokemon pokemon = CreateUtil.createBasePokemon();

        pokemonRepo.put(pokemon.getId(), pokemon);
    }

    public Pokemon getPokemon_id(String id) {
        if (pokemonRepo.size() > 0) {
            return pokemonRepo.get(id);
        }
        return null;
    }

    public String addPokemon(Pokemon basePokemon) {
        String id = null;
        if (basePokemon != null) {
            Pokemon pokemon = (Pokemon) basePokemon;
            if (pokemon.getId() != null) {
                id = pokemon.getId();
                pokemonRepo.put(id, pokemon);
            }
        }
        return id;
    }

    public Pokemon maxArgePokemon() {
        Optional<Pokemon> max = pokemonRepo.values().parallelStream().max((b, a) -> b.getArge() - a.getArge());
        if (max.isPresent()) {
            return max.get();
        } else {
            return null;
        }
    }

    public Pokemon minArgePokemon() {
        Optional<Pokemon> min = pokemonRepo.values().parallelStream().min((a,b) -> a.getArge() - b.getArge());
        if (min.isPresent()) {
            return min.get();
        } else {
            return null;
        }
    }

    public List<Pokemon> search(Character name) {
        if(name !=null) {
            List<Pokemon> sortedList = pokemonRepo.values().parallelStream().
                    filter(a -> a.getName() != null && a.getName().charAt(0) == name).
                    sorted(Comparator.comparingInt(Pokemon::getArge)).collect(Collectors.toList());
            return sortedList;
        }else{
            return null;
        }
    }


}

