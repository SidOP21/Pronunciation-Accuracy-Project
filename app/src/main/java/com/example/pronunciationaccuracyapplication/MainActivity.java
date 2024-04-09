package com.example.pronunciationaccuracyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button btnSayIt, btnAccuracy;
    TextToSpeech tts;
    ImageView ivMic;
    TextView tvRecognised, tvAccuracyPoint;


    static final int REQUEST_CODE_SPEECH_INPUT = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // "context" must be an Activity, Service or Application object from your app.
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        PyObject pyObj = py.getModule("AccuracyForApp");


        editText = findViewById(R.id.inputtext);
        btnSayIt = findViewById(R.id.sayit);
        ivMic = findViewById(R.id.mic);
        tvRecognised = findViewById(R.id.speechrecognised);
        btnAccuracy = findViewById(R.id.accuracy);
        tvAccuracyPoint = findViewById(R.id.accuracyPoint);


        btnSayIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS) {

                            //tts.setLanguage(Locale.UK);
                            //tts.setLanguage(Locale.US);
                            tts.setLanguage(new Locale("hin", "IND"));
                            //tts.setLanguage(new Locale("tam", "IND"));
                            tts.speak(editText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
                        }
                    }
                });
            }
        });

        ivMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                } catch (Exception e) {
                    Toast
                            .makeText(MainActivity.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        btnAccuracy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PyObject obj = pyObj.callAttr("accuracy",editText.getText().toString(), tvRecognised.getText().toString());
                tvAccuracyPoint.setText(obj.toString());
            }
        });
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                tvRecognised.setText(
                        Objects.requireNonNull(result).get(0));
            }
        }
    }
}