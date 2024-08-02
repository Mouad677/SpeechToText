package com.example.speechtotext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button bt_record;
    EditText ed1;
    TextView txt_github;
    public static final int RECORD_AUDIO_REQUEST_CODE = 1;
    private SpeechRecognizer speechRecognizer;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RECORD_AUDIO_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }else {
            Toast.makeText(this, "Permission requiered to record the audio!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_record = findViewById(R.id.bt_record);
        ed1 = findViewById(R.id.ed_txt);
        txt_github = findViewById(R.id.txt_github);

        txt_github.setOnClickListener(v ->{
            Uri uri = Uri.parse("https://github.com/Mouad677");
            Intent gitIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(gitIntent);
        });

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_REQUEST_CODE);
        }
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");

        bt_record.setOnLongClickListener(v -> {
            speechRecognizer.startListening(intent);
            return true;
        });

        bt_record.setOnTouchListener((v, event) ->{
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                   bt_record.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_red_dark));
                   speechRecognizer.startListening(intent);
                   return true;
                case MotionEvent.ACTION_UP:
                    bt_record.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_green_dark));
                    speechRecognizer.stopListening();
                    return true;
            }
            return false;
        });

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {
                Toast.makeText(MainActivity.this, "Error Occured!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(matches != null && !matches.isEmpty()){
                    ed1.setText(matches.get(0));
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });



    }
}