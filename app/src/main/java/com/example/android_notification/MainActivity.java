package com.example.android_notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * This Activity should resemble a Reminder App.
 * It is possible to write down the content of the reminder.
 * Setting the reminder shows an Android Notification to the user which contains the content.
 *
 * Layout File: activity_main.xml
 *
 * @author Lukas Plenk
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText content;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = findViewById(R.id.edit_content);

        button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    /**
     * Method for handling the interaction with the Button.
     * An Android Notification with the content of the EditText field should pop up.
     * @param view is the UI component that was clicked on.
     */
    @Override
    public void onClick(View view) {

        // Android Oreo and later versions require a NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // The NotificationChannel categorizes the Notifications of an app
            NotificationChannel notificationChannel = new NotificationChannel
                    ("Remind", "Reminder", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("Channel for Reminder Notifications");

            // A NotificationManager is necessary for creating a NotificationChannel
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Notification notification = new NotificationCompat.Builder(this, "Remind")
                .setSmallIcon(R.drawable.ic_calendar)
                .setContentText(content.getText().toString())
                // The priority is redundant but necessary for Builds lower than Oreo
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        // The Notification gets shown to the screen
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        // Every Notification with the same id can only exist one time, so this Notification will be overwritten by using the Button again
        managerCompat.notify(1, notification);
    }
}