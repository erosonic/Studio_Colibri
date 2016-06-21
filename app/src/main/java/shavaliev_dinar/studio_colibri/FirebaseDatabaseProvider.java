package shavaliev_dinar.studio_colibri;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseProvider {
    private static volatile FirebaseDatabaseProvider instance;
    private FirebaseDatabase mDatabase;

    private FirebaseDatabaseProvider() {
    }


    // вот это называется Double Checked Locking singleton. Запомни как пример.
    // Если будешь работать с java - пригодится, если щас начнешь гуглить -
    // встретишь слишком много ненужной пока информации.
    public static FirebaseDatabaseProvider getInstance() {
        FirebaseDatabaseProvider localInstance = instance;
        if (localInstance == null) {
            synchronized (FirebaseDatabaseProvider.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new FirebaseDatabaseProvider();
                }
            }
        }
        return localInstance;
    }


}

