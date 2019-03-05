package ru.anosvqa.vae.rest_api_retrofit_tests.desc;

public class GithubRepo {

    String name;
    String owner;
    String url;

    @Override
    public String toString() {
        return "GithubRepo{" +
                "name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
