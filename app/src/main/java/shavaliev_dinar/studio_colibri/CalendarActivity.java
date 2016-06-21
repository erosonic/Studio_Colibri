package shavaliev_dinar.studio_colibri;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    private CaldroidFragment calendar;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MM yyyy");
    public static final String DATABASE_NAME = "calendarData";
    private Map<String, Boolean> isDayBusyMap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
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
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    private CaldroidListener calendarListener = new CaldroidListener() {
        @Override
        public void onSelectDate(Date date, View view) {
            String dateString = dateFormatter.format(date);
            if (isDayBusyMap.containsKey(dateString) && isDayBusyMap.get(dateString)) {
                Toast.makeText(getApplicationContext(), "Простите, этот день занят! Выберите другой", Toast.LENGTH_SHORT).show();
            } else
                database.child(dateString).setValue(true);
        }

        @Override
        public void onCaldroidViewCreated() {
            super.onCaldroidViewCreated();
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


}