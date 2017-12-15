package fhku.leanlabapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import fhku.leanlabapp.classes.Content;
import fhku.leanlabapp.classes.HTMLEditor;
import fhku.leanlabapp.classes.helper._HelperMethods;
import fhku.leanlabapp.interfaces.database.DbConnection;
import jp.wasabeef.richeditor.RichEditor;

public class MainActivityAdmin extends AppCompatActivity implements View.OnClickListener {

    RichEditor editor;
    HTMLEditor editorHtml;
    private Button takePictureButton;
    private Button takeVideoButton;
    private ImageView imageView;
    private VideoView videoView;
    private Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        editorHtml = new HTMLEditor((RichEditor) findViewById(R.id.editor), (Button) findViewById(R.id.heading));
        editorHtml.getEditor().setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                saveHtml(editorHtml.getEditor().getHtml(), 1);
                getHtml(1);
                saveToForm(Content.Loaded_Contents.get(1).getContenttext());
            }
        });

        takePictureButton = (Button)findViewById(R.id.picture);
        takeVideoButton = (Button)findViewById(R.id.video);
        imageView = (ImageView)findViewById(R.id.imgview);
        videoView = (VideoView)findViewById(R.id.vidview);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePictureButton.setEnabled(false);
            takeVideoButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureButton.setEnabled(true);
                takeVideoButton.setEnabled(true);
            }
        }
    }

    public void takeVideo(View view) {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, 101);
        }
    }

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            videoView.setVideoURI(videoUri);
        }
        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageView.setImageURI(file);
        }
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "LeanLabDirectory");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }



    private void saveHtml(String text, int contentId) {
        try {
            DbConnection.sendRequestForResult_ASYNC(new String[]{"sql_statement=UPDATE Content set Contenttext='" + _HelperMethods.escapeHTML(text) + "' WHERE ContentID=" + contentId + ";"}, "post", false,this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getHtml(int contentId) {
        Content tmp= new Content(1);
        try {
            Content.Loaded_Contents = tmp.MapJsonRowsToObject((DbConnection.sendRequestForResult_ASYNC(new String[]{"sql_statement=SELECT Content WHERE ContentID=" + contentId + ";"}, "get", false,this)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToForm(String form) {

        editorHtml.getEditor().setHtml(form);

    }

    @Override
    public void onClick(View v) {

    }
}