package com.srimin.demo01springbootquickstart.provider;

import com.alibaba.fastjson.JSON;
import com.srimin.demo01springbootquickstart.dto.AccessTokenDTO;
import com.srimin.demo01springbootquickstart.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        System.out.println("正在请求accessToken...");
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            //str = access_token=5c20f20c461a6fb1321d56a28bc5e640fa69171b&scope=user&token_type=bearer
            return str.split("&")[0].split("=")[1];//分割得到token
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GitHubUser getGithubUser (String accessTokenDTO){
        System.out.println("正在请求githubUser...");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+ accessTokenDTO)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            GitHubUser gitHubUser = JSON.parseObject(str, GitHubUser.class);
            return gitHubUser;
        } catch (Exception e) {
        }
        return null;
    }
}
