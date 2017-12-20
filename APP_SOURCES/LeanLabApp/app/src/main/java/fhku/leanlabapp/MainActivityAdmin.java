package fhku.leanlabapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fhku.leanlabapp.classes.Content;
import fhku.leanlabapp.classes.HTMLEditor;
import fhku.leanlabapp.classes.helper._HelperMethods;
import fhku.leanlabapp.interfaces.database.DbConnection;
import jp.wasabeef.richeditor.RichEditor;

public class MainActivityAdmin extends AppCompatActivity implements View.OnClickListener {

    RichEditor editor;
    HTMLEditor editorHtml;
    private String htmltext;
    private String product;
    private String productid;
    private String station;
    private String stationid;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private ImageView imagePreview;
    private VideoView videoPreview;
    private Button buttonCamera, buttonVideo;
    private Uri fileUri;
    private static final String IMAGE_DIRECTORY_NAME = "LEAN Lab Bibliothek";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        editorHtml = new HTMLEditor((RichEditor) findViewById(R.id.editor), (Button) findViewById(R.id.heading));
        editorHtml.getEditor().setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {

                htmltext = text;

                Log.i("editortest", htmltext);
                //saveHtml(editorHtml.getEditor().getHtml(), 1);
                //getHtml(1);
                //saveToForm(Content.Loaded_Contents.get(1).getContenttext());
            }
        });

        takeIntent();

        TextView stationview = (TextView)findViewById(R.id.station);
        TextView productview = (TextView)findViewById(R.id.product);

        stationview.setText(station);
        productview.setText(product);

        imagePreview = (ImageView) findViewById(R.id.imageView);
        videoPreview = (VideoView) findViewById(R.id.videoView);
        buttonCamera = (Button) findViewById(R.id.btnCamera);
        buttonVideo = (Button) findViewById(R.id.btnVideo);

        buttonCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        buttonVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                recordVideo();
            }
        });

        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Ihr Gerät unterstützt keine Kamera-Funktionen!",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "Sie haben die Fotoaufnahme abgebrochen", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Fotoaufnahme nicht möglich", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                previewVideo();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "Sie haben die Videoaufnahme abgebrochen", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Videoaufnahme nicht möglich", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void previewCapturedImage() {
        try {
            videoPreview.setVisibility(View.GONE);

            imagePreview.setVisibility(View.VISIBLE);

            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            imagePreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewVideo() {
        try {
            imagePreview.setVisibility(View.GONE);

            videoPreview.setVisibility(View.VISIBLE);
            videoPreview.setVideoPath(fileUri.getPath());
            videoPreview.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Failed to create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
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

    private void takeIntent(){
        Intent intent = getIntent();

        station = intent.getStringExtra("stationname");
        stationid = intent.getStringExtra("stationid");
        product = intent.getStringExtra("productname");
        productid = intent.getStringExtra("productid");

        Log.i("take_intent ", product + productid + station + stationid);
    }

}