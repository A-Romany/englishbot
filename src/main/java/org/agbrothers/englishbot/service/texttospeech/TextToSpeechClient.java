package org.agbrothers.englishbot.service.texttospeech;

import java.io.InputStream;


 public interface TextToSpeechClient {
    InputStream getAudioFile(String word);
}
