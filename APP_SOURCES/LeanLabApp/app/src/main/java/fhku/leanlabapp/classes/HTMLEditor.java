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


    public HTMLEditor(final RichEditor editor, Button heading) {
        this.setEditor(editor);
        getEditor().setPlaceholder("Hier Beschreibung eingeben");
        getEditor().setEditorFontColor(Color.BLACK);
        getEditor().setEditorHeight(300);

        this.setHeading(heading);
        this.getHeading().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                getEditor().setUnderline();
                getEditor().setBold();


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
}

