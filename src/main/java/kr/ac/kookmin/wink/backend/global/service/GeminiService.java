package kr.ac.kookmin.wink.backend.global.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kookmin.wink.backend.loadmap.domain.Loadmap;
import kr.ac.kookmin.wink.backend.loadmap.domain.LoadmapCircle;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final RestTemplate restTemplate;
    @Value("google.gemini.key")
    private String key;

    @Async
    public CompletableFuture<String> getLoadmapSummaryAsync(Loadmap loadmap) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent";
        url += "?key=" + key;
        String content = loadmapToContent(loadmap);
        String prompt = ". 30자 이내로 한 줄로 요약해줘";
        String request = "{\n" +
            "    \"contents\": [\n" +
            "        {\n" +
            "            \"parts\": [\n" +
            "                {\n" +
            "                    \"text\": \"" + content + prompt + "\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";
        String jsonString = restTemplate.postForEntity(url, request, String.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON 문자열을 JsonNode로 변환
            JsonNode rootNode = objectMapper.readTree(jsonString);

            // "candidates" 배열 가져오기
            JsonNode candidatesNode = rootNode.path("candidates").get(0);

            // "content" 객체 가져오기
            JsonNode contentNode = candidatesNode.path("content");

            // "parts" 배열 가져오기
            JsonNode partsNode = contentNode.path("parts").get(0);

            // "text" 값 가져오기
            String text = partsNode.path("text").asText();

            return CompletableFuture.completedFuture(text);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String loadmapToContent(Loadmap loadmap) {
        String str = "";
        str += ("제목 : " + loadmap.getTitle() + ", ");
        for (int i = 1; i <= loadmap.getLoadmapCircles().size(); i++) {
            LoadmapCircle lc = loadmap.getLoadmapCircles().get(i);
            str += ("(순서 : " + i + ", ");
            str += ("제목 : " + lc.getTitle() + ", ");
            str += ("날짜 : " + lc.getDate() + ", ");
            str += ("내용 : " + lc.getContent() + ", ");
            str += ("중요도 : " + lc.getLevel() + ")");
        }
        return str;
    }

}
