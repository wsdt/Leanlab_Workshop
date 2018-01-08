package fhku.leanlabapp.interfaces;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import fhku.leanlabapp.R;


public class Dialog extends AlertDialog.Builder {
    private static Dialog lastInstance;


    public static Dialog createDialog(@NonNull Context context, @NonNull String title, @NonNull String message, int icon) {
        //IMPORTANT: UserDefinedClass needs to implement called functions below! (create Interface)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setLastInstance(new Dialog(context, android.R.style.Theme_Material_Dialog_Alert));
        } else {
            setLastInstance(new Dialog(context));
        }

        getLastInstance() //get current dialog
                .setTitle(title)
                .setMessage(message)
                .setIcon(icon);
        return getLastInstance(); //return last instance (this one)
    }


    private Dialog(@NonNull Context context, @NonNull int themeResId) {
        super(context, themeResId);
    }

    private Dialog(@NonNull Context context) {
        super(context);
    }


    // GETTER / SETTER ----------------------------
    public static Dialog getLastInstance() {
        return lastInstance;
    }

    public static void setLastInstance(Dialog lastInstance) {
        Dialog.lastInstance = lastInstance;
    }
}
