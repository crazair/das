package ru.anosvqa.vae.rest_api_retrofit_tests.desc;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;
import okhttp3.ResponseBody;
import java.util.List;

public interface GitHubService {

    String BASE_URL = "https://api.github.com/";

    @GET("users/{user}/repos")
    Call<List<Repository>> listRepos(@Path("user") String user);

    @GET("user/repos?per_page=100")
    Call<List<GithubRepo>> getRepos();

    @GET("/repos/{owner}/{repo}/issues")
    Call<List<GithubIssue>> getIssues(@Path("owner") String owner, @Path("repo") String repository);

    @POST
    Call<ResponseBody> postComment(@Url String url, @Body GithubIssue issue);
}