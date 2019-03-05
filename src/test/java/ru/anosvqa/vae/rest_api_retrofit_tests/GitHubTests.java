package ru.anosvqa.vae.rest_api_retrofit_tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.anosvqa.vae.rest_api_retrofit_tests.desc.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.anosvqa.vae.rest_api_retrofit_tests.desc.GitHubService.BASE_URL;

/**
 * http://square.github.io/retrofit/
 * http://www.vogella.com/tutorials/Retrofit/article.html#retrofit-authentication
 * https://habr.com/ru/post/428736/
 * https://habr.com/ru/post/314028/
 * */
public class GitHubTests {


    private static final String USER_NAME = "crazair";
    private static final String PASSWORD  = "12345";

    @Test
    @DisplayName("Пример теста на REST запрос с использованием типобезопасного HTTP-клиента Retrofit")
    public void testRepository() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)                                      //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())     //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        GitHubService gitHub = retrofit.create(GitHubService.class);    //Создаем объект, при помощи которого будем выполнять запросы
        Response response = gitHub.listRepos(USER_NAME).execute();

        List<Repository> repositories = (List<Repository>) response.body();

        assertAll(
                () -> assertEquals(response.code(), 200, "Код ответа доложен быть 200! Фактичеки: " + response.code()),
                () -> assertTrue(repositories.size() > 0, "Кол-во репозиториев должно быть > 0")
        );

        repositories.forEach( r -> System.out.println(r.getName()));
    }

    @Test
    @DisplayName("Второй тест")
    public void testRepository2() throws Exception {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(GithubRepo.class, new GithubRepoDeserialzer())
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder().header("Authorization", Credentials.basic(USER_NAME, PASSWORD));
                    Request newRequest = builder.build();

                    return chain.proceed(newRequest);
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GitHubService gitHub = retrofit.create(GitHubService.class);



        Response response = gitHub.getRepos().execute();

        System.out.println(response.code());
        System.out.println(response.message());

        ((List<GithubRepo>) response.body()).forEach(r -> System.out.println(r));
    }

    @Test
    @DisplayName("Третий тест")
    public void testRepository3() throws Exception {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(GithubRepo.class, new GithubRepoDeserialzer())
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder().header("Authorization", Credentials.basic(USER_NAME, PASSWORD));
            Request newRequest = builder.build();

            return chain.proceed(newRequest);
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GitHubService gitHub = retrofit.create(GitHubService.class);



        Response response = gitHub.getIssues("crazair", "checksites").execute();



        System.out.println(response.code());
        System.out.println(response.message());

        ((List<GithubIssue>) response.body()).forEach(r -> System.out.println(r));
    }

}
