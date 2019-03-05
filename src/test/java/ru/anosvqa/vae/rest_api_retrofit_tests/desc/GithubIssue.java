package ru.anosvqa.vae.rest_api_retrofit_tests.desc;

import com.google.gson.annotations.SerializedName;

public class GithubIssue {

    String id;
    String title;
    String comments_url;

    @SerializedName("body")
    String comment;

    @Override
    public String toString() {
        return "GithubIssue{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", comments_url='" + comments_url + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
