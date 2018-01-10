package fhku.leanlabapp.interfaces.database;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

    public LoadImageTask(Listener listener) {

        mListener = listener;
    }

    public interface Listener{

        void onImageLoaded(Bitmap bitmap);
        void onError();
    }

    private Listener mListener;
    @Override
    protected Bitmap doInBackground(String... args) {

        try {

            return BitmapFactory.decodeStream((InputStream)new URL("http://192.168.12.115/LeanLabWorking/img/"+args[0]).getContent());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if (bitmap != null) {

            mListener.onImageLoaded(bitmap);

        } else {

            mListener.onError();
        }
    }

}