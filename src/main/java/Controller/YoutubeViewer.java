package Controller;

import com.google.api.services.youtube.model.SearchResult;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import models.YouTubeAPIExample;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class YoutubeViewer {
    @FXML
    private WebView webView;

    private static final String API_KEY = "AIzaSyAW5EuWzi6d8kzNVvul8lQSS0NKvnHx-LM";

    public void initialize() {
        try {
            YouTubeAPIExample youtubeAPIExample = new YouTubeAPIExample(API_KEY);
            List<SearchResult> searchResults = youtubeAPIExample.searchVideos("Java Programming");
            if (!searchResults.isEmpty()) {
                SearchResult firstResult = searchResults.get(0);
                String videoId = firstResult.getId().getVideoId();
                String videoUrl = "https://www.youtube.com/watch?v=xk4_1vDrzzo" + videoId;
                //webView.getEngine().load(videoUrl);
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

}
