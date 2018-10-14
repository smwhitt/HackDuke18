package e.smwhitt.myco2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }


    public void createNewCar(View view) {
        Intent newCar = new Intent(this, Vehicles.class);
        startActivity(newCar);
    }
}
