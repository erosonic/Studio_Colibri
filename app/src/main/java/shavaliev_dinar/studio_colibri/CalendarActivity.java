package shavaliev_dinar.studio_colibri;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity {

    private DatabaseReference database;
    private LinearLayout loadingLayout;
    private CaldroidFragment calendar;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MM yyyy");
    public static final String DATABASE_NAME = "calendarData";
    public static final String INTENT_DATE_FIELD = "DATE_SELECTED";
    private Map<String, Boolean> isDayBusyMap;
    private String lastTappedDay = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        loadingLayout = (LinearLayout) findViewById(R.id.calendar_loading_layout);
        database = FirebaseDatabase.getInstance().getReference(DATABASE_NAME);
        showCaldroid();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isDayBusyMap = new HashMap<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    isDayBusyMap.put(data.getKey(), Boolean.valueOf(data.getValue().toString()));
                    try {
                        calendar.setBackgroundDrawableForDate(new ColorDrawable(Color.RED),
                                dateFormatter.parse(data.getKey()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } finally {
                        calendar.refreshView();
                        loadingLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loadingLayout.setVisibility(View.GONE);
            }
        });
    }

    private void showCaldroid() {
        calendar = new CaldroidFragment();
        calendar.setCaldroidListener(calendarListener);
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        calendar.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.calendar_container, calendar).commit();
    }

    private void showContactsDialog(String title, ContactsDialog.SendButtonListener listener) {
        ContactsDialog dialog = new ContactsDialog();
        dialog.setTitleValue(title);
        dialog.setButtonListener(listener);
        dialog.show(getSupportFragmentManager(), "ContactsDialog");
    }

    private CaldroidListener calendarListener = new CaldroidListener() {
        @Override
        public void onSelectDate(Date date, View view) {
            final String dateString = dateFormatter.format(date);
            lastTappedDay = dateString;
            if (isDayBusyMap != null &&
                    isDayBusyMap.containsKey(dateString) &&
                    isDayBusyMap.get(dateString)) {
                Toast.makeText(getApplicationContext(), "Простите, этот день занят! Выберите другой",
                        Toast.LENGTH_SHORT).show();
            } else {
                showContactsDialog(dateString, new ContactsDialog.SendButtonListener() {
                    @Override
                    public void sendButtonClicked(String contactsData) {
                        sendEmail(dateString, contactsData);
                    }
                });
            }
        }

        @Override
        public void onCaldroidViewCreated() {
            super.onCaldroidViewCreated();
            loadingLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public void onChangeMonth(int month, int year) {
            super.onChangeMonth(month, year);
        }

        @Override
        public void onLongClickDate(Date date, View view) {
            super.onLongClickDate(date, view);
        }
    };


    private void sendEmail(String date, String contacts) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"dinar.shavaliev@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Запись");
        i.putExtra(INTENT_DATE_FIELD, date);
        i.putExtra(Intent.EXTRA_TEXT, "Запись на день: " + date + ". \nКонтакты: " + contacts);
        try {
            startActivityForResult(Intent.createChooser(i, "Послать сообщение..."), 333);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Нет почтовых клиентов!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!TextUtils.isEmpty(lastTappedDay))
            database.child(lastTappedDay).setValue(true);
        super.onActivityResult(requestCode, resultCode, data);
    }
}