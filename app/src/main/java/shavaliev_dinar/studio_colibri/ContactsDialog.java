package shavaliev_dinar.studio_colibri;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rey.material.app.DialogFragment;
import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;

public class ContactsDialog extends DialogFragment {

    private SendButtonListener buttonListener;
    private EditText contactsInput;
    private TextView title;
    private String titleValue;
    private Button sendButton;

    public void setTitleValue(String titleValue) {
        this.titleValue = titleValue;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_input_text, container, false);
        contactsInput = (EditText) v.findViewById(R.id.input_contacts);
        sendButton = (Button) v.findViewById(R.id.contacts_send_btn);
        title = (TextView) v.findViewById(R.id.contacts_dialog_title);
        title.setText(TextUtils.isEmpty(titleValue) ? "" : titleValue);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonListener != null)
                    buttonListener.sendButtonClicked(contactsInput.getText().toString());
            }
        });
        return v;
    }

    public void setButtonListener(SendButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public interface SendButtonListener {
        void sendButtonClicked(String contactsData);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        this.dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        this.dismiss();
    }
}
