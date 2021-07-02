package com.example.terms.reciver;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.terms.R;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title=intent.getStringExtra( "title" );
        NotificationCompat.Builder builder=new NotificationCompat.Builder( context,"1" )
                .setContentTitle("Alert for "+title)

                // .setDefaults( Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS|Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                //  .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })

                //LED
                //  .setLights( Color.RED, 1000, 500)

                //Ton
                // .setSound( Uri.parse("uri://sadfasdfasdf.mp3"))
                .setPriority( Notification.PRIORITY_MAX)
                .setSmallIcon( R.drawable.ic_launcher_background );
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from( context );
        notificationManagerCompat.notify( 200,builder.build() );
    }
}
