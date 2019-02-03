package ru.anosvqa.vae.rest_api_tests.desc;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface GitHubService {

    @GET("users/{user}/repos")
    Call<List<Repository>> listRepos(@Path("user") String user);
}