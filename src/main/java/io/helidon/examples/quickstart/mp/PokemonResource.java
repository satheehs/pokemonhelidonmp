package io.helidon.examples.quickstart.mp;

import java.util.Collections;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import io.helidon.examples.quickstart.mp.pojo.Pokemon;
import io.helidon.examples.quickstart.mp.repo.PokemonRepo;
import io.helidon.examples.quickstart.mp.util.CreateUtil;

@Path("/pokemon")
@ApplicationScoped
public class PokemonResource {

	private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

	private final PokemonRepo pokemonRepo = new PokemonRepo();

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPokemon(@PathParam("id") String id) {
		Pokemon pokemon = pokemonRepo.getPokemon_id(id);
		return Response.ok(pokemon).build();
	}

	@Path("/save")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateGreeting(JsonObject jsonObject) {

		if (!jsonObject.containsKey("id")) {
			JsonObject entity = JSON.createObjectBuilder().add("error", "No greeting provided").build();
			return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}

		String id = jsonObject.getString("id");
		String name = jsonObject.getString("name");
		String type = jsonObject.getString("type");
		int arge = jsonObject.getInt("arge");

		pokemonRepo.addPokemon(CreateUtil.createPokemon(id, name, type, arge));

		return Response.ok("Created succesfully").build();
	}

	@GET
	@Path("/maxArgePokemon")
	@Produces(MediaType.APPLICATION_JSON)
	public Response maxArgePokemon() {
		return Response.ok(pokemonRepo.maxArgePokemon()).build();
	}

	@GET
	@Path("/minArgePokemon")
	@Produces(MediaType.APPLICATION_JSON)
	public Response minArgePokemon() {
		return Response.ok(pokemonRepo.minArgePokemon()).build();
	}

	@GET
	@Path("/firstCharMatcher/{firstChar}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response firstCharMatcher(@PathParam("firstChar") Character character) {
		return Response.ok(pokemonRepo.search(character)).build();
	}

}
