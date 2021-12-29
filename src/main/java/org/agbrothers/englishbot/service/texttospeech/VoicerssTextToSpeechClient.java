package org.agbrothers.englishbot.service.texttospeech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

@Service
public class VoicerssTextToSpeechClient implements  TextToSpeechClient{

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public InputStream getAudioFile(String word) {
        String voicerssApi = "http://api.voicerss.org/?key=60e35db111144fcda2af3958169f6f11&hl=en-us&src=";
        ResponseEntity<byte[]> entity = restTemplate.getForEntity(voicerssApi + word, byte[].class);
        return new ByteArrayInputStream(Objects.requireNonNull(entity.getBody()));
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
