package com.srimin.demo01springbootquickstart.controller;

import com.srimin.demo01springbootquickstart.dto.AccessTokenDTO;
import com.srimin.demo01springbootquickstart.dto.GitHubUser;
import com.srimin.demo01springbootquickstart.mapper.UserMapper;
import com.srimin.demo01springbootquickstart.model.User;
import com.srimin.demo01springbootquickstart.model.UserExample;
import com.srimin.demo01springbootquickstart.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GitHubUser githubUser = githubProvider.getGithubUser(accessToken);
        System.out.println("githubUser："+githubUser);
        if (githubUser != null && githubUser.getId() != null)
        {
            UserExample userExample = new UserExample();
            userExample.createCriteria()
                    .andAccountIdEqualTo(String.valueOf(githubUser.getId()));
            List<User> users = userMapper.selectByExample(userExample);
            User user = users.get(0);
            if (user == null){
                user = new User();
                user.setToken(UUID.randomUUID().toString());
                user.setName(githubUser.getName());
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setBio(githubUser.getBio());
                user.setAvatarUrl(githubUser.getAvatarUrl());
                userMapper.insert(user);
            }else{
                user.setToken(UUID.randomUUID().toString());
                user.setGmtModified(System.currentTimeMillis());
                user.setBio(githubUser.getBio());
                user.setAvatarUrl(githubUser.getAvatarUrl());
                UserExample example = new UserExample();
                example.createCriteria()
                        .andAccountIdEqualTo(user.getAccountId());
                userMapper.updateByExampleSelective(user, example);
            }
            response.addCookie(new Cookie("token",user.getToken()));
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
