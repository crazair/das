package ru.anosvqa.vae.rest_api_tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.anosvqa.vae.rest_api_tests.desc.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * http://square.github.io/retrofit/
 * https://habr.com/ru/post/314028/
 * */
public class GitHubTests {

    private static final String BASE_URL = "https://api.github.com/";
    private static final String USER_NAME = "crazair";

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

}
