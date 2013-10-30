package ru.qzenn.mediaplayer;

import java.io.IOException;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    EditText urlText;
    MediaPlayer player;
    Button playBtn;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        playBtn = (Button)findViewById(R.id.playButton);
        playBtn.setOnClickListener(playAction);
        urlText = (EditText)findViewById(R.id.urlEditText);
        urlText.setText(R.string.default_url);
        player = new MediaPlayer();
    }
    
    private void buttonPress() {
        if(player.isPlaying()){
            player.stop();
            player.reset();
            runOnUiThread(new Runnable() {
                
                public void run() {
                    // TODO Auto-generated method stub
                    playBtn.setText(R.string.buttonText_Play);
                }
            });
        }
        else{
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                Uri fileUri = Uri.parse(urlText.getText().toString());
                player.setDataSource(getApplicationContext(), fileUri);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                player.prepare();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            player.start();
            runOnUiThread(new Runnable() {

                public void run() {
                    // TODO Auto-generated method stub
                    playBtn.setText(R.string.buttonText_Stop);
                }
            });

        }
    }

    private OnClickListener playAction = new OnClickListener(){
        public void onClick(View v){
            Thread playerThread = new Thread(playerJob);
            playerThread.start();
//            buttonPress();
        }
    };

    Runnable playerJob = new Runnable(){
        public void run(){
            buttonPress();
        }
    };
}