package fhku.leanlabapp.classes;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by Jojo on 27.11.2017.
 */

public class HTMLEditor {

    private RichEditor editor;
    private Button heading;
    private Button red;
    int i = 0;


    public HTMLEditor(final RichEditor editor, Button heading, Button red) {
        this.setEditor(editor);
        getEditor().setPlaceholder("Hier Beschreibung eingeben");
        getEditor().setEditorFontColor(Color.BLACK);
        getEditor().setTextColor(Color.BLACK);
        getEditor().setEditorHeight(300);

        this.setHeading(heading);
        this.getHeading().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                getEditor().setUnderline();
                getEditor().setBold();


            }
        });

        this.setRed(red);
        this.getRed().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i%2==0){
                    getEditor().setTextColor(Color.RED);
                }
                if (i%2==1){
                    getEditor().setTextColor(Color.BLACK);
                }
                i++;


            }
        });

    }


    public RichEditor getEditor() {
        return editor;
    }

    public void setEditor(RichEditor editor) {
        this.editor = editor;
    }

    public Button getHeading() {
        return heading;
    }

    public void setHeading(Button heading) {
        this.heading = heading;
    }

    public Button getRed() {
        return red;
    }

    public void setRed(Button red) {
        this.red = red;
    }
}

