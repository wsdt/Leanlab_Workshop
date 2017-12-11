package fhku.leanlabapp.interfaces;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.Callable;


public class Dialog extends AlertDialog.Builder {
    private static Dialog lastInstance;

    // DIALOG TYPES - START
    /*public static final int DIALOG_YES_NO = 0;
    public static final int DIALOG_OK = 1;*/
    // DIALOG TYPES - END

    public static Dialog showDialog(@NonNull Context context, @NonNull String title, @NonNull String message, int icon/*, @Nullable final UserDefinedClass onClickFunctions*/) {
        //IMPORTANT: UserDefinedClass needs to implement called functions below! (create Interface)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            lastInstance = new Dialog(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            lastInstance = new Dialog((context));
        }

        lastInstance
                .setTitle(title)
                .setMessage(message)
                .setIcon(icon);

        /*switch (dialogtype) {
            case DIALOG_YES_NO:
                if (onClickFunctions == null) {
                    lastInstance.setPositiveButton(android.R.string.yes, null);
                    lastInstance.setNegativeButton(android.R.string.no, null);
                } else {
                    lastInstance.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ;
                        }
                    });
                    lastInstance.setNegativeButton(android.R.string.no, noListener);
                }
                break;
            default:
                Log.e("showDialog","Dialogtype '"+dialogtype+"' not found!");
        }*/

        return lastInstance;
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
