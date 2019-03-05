package ru.anosvqa.vae.rest_api_retrofit_tests.desc;

import com.google.gson.*;

import java.lang.reflect.Type;

public class GithubRepoDeserialzer implements JsonDeserializer<GithubRepo> {

    @Override
    public GithubRepo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        GithubRepo githubRepo = new GithubRepo();

        JsonObject repoJsonObject = json.getAsJsonObject();
        githubRepo.name = repoJsonObject.get("name").getAsString();
        githubRepo.url = repoJsonObject.get("url").getAsString();

        JsonElement ownerJsonElement = repoJsonObject.get("owner");
        JsonObject ownerJsonObject = ownerJsonElement.getAsJsonObject();
        githubRepo.owner = ownerJsonObject.get("login").getAsString();

        return githubRepo;
    }
}