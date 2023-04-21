package net.chatgpt.testing.http;

import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.chatgpt.testing.data.RequestCreator.GSON;

public class HttpUtil {

    public static Header[] DEFAULT_JSON_HEADERS = new Header[] {
            new BasicHeader("accept", "application/json"),
            new BasicHeader("content-type", "application/json")
    };

    private HttpUtil() { }

    public static String get(String url, Header... headers) throws IOException {
        HttpGet get = new HttpGet(url);
        if(headers != null) {
            get.setHeaders(headers);
        }

        return executeAndGetResponse(get);
    }

    public static String post(String url) throws IOException {
        return post(url, null);
    }

    public static String post(String url, StringEntity entity, Header... headers) throws IOException {
        HttpPost post = new HttpPost(url);
        if(entity != null) {
            post.setEntity(entity);
        }

        if(headers != null) {
            post.setHeaders(headers);
        }

        return executeAndGetResponse(post);
    }

    private static String executeAndGetResponse(HttpRequestBase request) throws IOException {
        String responseString = null;
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseString = EntityUtils.toString(entity);
            }
        }

        return responseString;
    }

    public static StringEntity mapToStringEntity(Map<String, Object> map) {
        String requestBodyJson = GSON.toJson(map);
        return new StringEntity(requestBodyJson, ContentType.APPLICATION_JSON);
    }

    public static Header[] createHeadersArray(Map<String, String> headersMap) {
        List<Header> headersList = new ArrayList<>();
        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            headersList.add(new BasicHeader(entry.getKey(), entry.getValue()));
        }

        return headersList.toArray(new Header[0]);
    }
}