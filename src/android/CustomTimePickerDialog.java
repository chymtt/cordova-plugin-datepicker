/**
 * @author Do Anh Tu 2014
 * Rewrote the plug-in at https://github.com/VitaliiBlagodir/cordova-plugin-datepicker
 */

package com.plugin.datepicker;

import com.android.internal.R;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public abstract class CustomeTimePickerDialog extends AlertDialog implements OnClickListener, OnTimeChangedListener {
    public final static String[] DISPLAYED_MINS = { "0", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" };

    public interface OnTimeSetListener {

        /**
         * @param view The view associated with this listener.
         * @param hourOfDay The hour that was set.
         * @param minute The minute that was set.
         */
        void onTimeSet(TimePicker view, int hourOfDay, int minute);
    }

    public void onClick(DialogInterface dialog, int which) {
        if (mCallback != null) {
            mTimePicker.clearFocus();
            mCallback.onTimeSet(mTimePicker, mTimePicker.getCurrentHour(),
                    mTimePicker.getCurrentMinute());
        }
    }

    public void updateTime(int hourOfDay, int minutOfHour) {
        mTimePicker.setCurrentHour(hourOfDay);
        mTimePicker.setCurrentMinute(minutOfHour);
    }

    public abstract void onTimeChanged(TimePicker view, int hourOfDay, int minute);

}