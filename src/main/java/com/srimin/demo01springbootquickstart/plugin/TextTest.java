package com.srimin.demo01springbootquickstart.plugin;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TextTest {
    private OkHttpClient client = new OkHttpClient();

    public String run(String url) throws IOException {
        Request.Builder requestBuilder = new Request.Builder();
//        requestBuilder.addHeader("__cfduid","d2233c035eb40f261436e4fe501ba1fe41584926235");
//        requestBuilder.addHeader("__gads","ID=99f57401020e3160:T=1584926237:S=ALNI_MZwM5pJ64CZnbwEpD9CoAduOYNOhg");
//        requestBuilder.addHeader("_ga","GA1.2.2051040888.1584926237");
//        requestBuilder.addHeader("_gid","GA1.2.169736300.1584926238");
        Request request = requestBuilder
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    public void parse (String stringResopnce){
        Document doc = Jsoup.parse(stringResopnce);
        Elements bookList = doc.select(".book-list");
        for (Element element : bookList) {
            Elements titleList = element.select("ul > li");
            System.out.println("=====分割=====");
            System.out.println(titleList.size());
            for (Element element1 : titleList) {
                String title = element1.select("a").attr("title");
                String href = element1.select("a").attr("href");
                System.out.println(title+"："+href);
            }
        }
    }
}
