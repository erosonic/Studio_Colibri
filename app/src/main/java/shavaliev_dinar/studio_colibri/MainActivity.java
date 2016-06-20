package shavaliev_dinar.studio_colibri;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

@SuppressWarnings( "deprecation")
public class MainActivity extends TabActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("TabHost");
        TabHost tabHost = getTabHost();
        TabHost.TabSpec tabSpec;

        tabHost.setup();
        tabSpec  = tabHost.newTabSpec("tag1");
        tabSpec.setContent(new Intent(this, News.class) );
        tabSpec.setIndicator("Новости");
        tabHost.addTab(tabSpec);

        tabHost.setup();
        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(new Intent(this, PhotoActivity.class) );
        tabSpec.setIndicator("Фотогалерея");
        tabHost.addTab(tabSpec);

        tabHost.setup();
        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(new Intent(this, Calendar.class) );
        tabSpec.setIndicator("Календарь");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)

        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
