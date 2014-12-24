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
import android.widget.NumberPicker;

public class CustomLegacyTimePickerDialog extends CustomTimePickerDialog {

    private static final String HOUR = "hour";
    private static final String MINUTE = "minute";
    private static final String IS_24_HOUR = "is24hour";

    private final TimePicker mTimePicker;
    private final CustomTimePickerDialog.OnTimeSetListener mCallback;
    private final Calendar mCalendar;
    private final java.text.DateFormat mDateFormat;

    int mInitialHourOfDay;
    int mInitialMinute;
    boolean mIs24HourView;

    /**
     * @param context Parent.
     * @param callBack How parent is notified.
     * @param hourOfDay The initial hour.
     * @param minute The initial minute.
     * @param is24HourView Whether this is a 24 hour view, or AM/PM.
     */
    public CustomLegacyTimePickerDialog(Context context,
            CustomTimePickerDialog.OnTimeSetListener callBack,
            int hourOfDay, int minute, boolean is24HourView) {
        this(context, com.android.internal.R.style.Theme_Dialog_Alert,
                callBack, hourOfDay, minute, is24HourView);
    }

    /**
     * @param context Parent.
     * @param theme the theme to apply to this dialog
     * @param callBack How parent is notified.
     * @param hourOfDay The initial hour.
     * @param minute The initial minute.
     * @param is24HourView Whether this is a 24 hour view, or AM/PM.
     */
    public CustomLegacyTimePickerDialog(Context context,
            int theme,
            CustomTimePickerDialog.OnTimeSetListener callBack,
            int hourOfDay, int minute, boolean is24HourView) {
        super(context, theme);
        mCallback = callBack;
        mInitialHourOfDay = hourOfDay;
        mInitialMinute = minute;
        mIs24HourView = is24HourView;

        mDateFormat = DateFormat.getTimeFormat(context);
        mCalendar = Calendar.getInstance();
        updateTitle(mInitialHourOfDay, mInitialMinute);

        setButton(context.getText(R.string.date_time_set), this);
        setButton2(context.getText(R.string.cancel), (OnClickListener) null);
        setIcon(R.drawable.ic_dialog_time);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.time_picker_dialog, null);
        setView(view);
        mTimePicker = (TimePicker) view.findViewById(R.id.timePicker);

        // initialize state
        mTimePicker.setCurrentHour(mInitialHourOfDay);
        mTimePicker.setCurrentMinute(mInitialMinute);
        mTimePicker.setIs24HourView(mIs24HourView);
        mTimePicker.setOnTimeChangedListener(this);

        NumberPicker mMinutePicker = (NumberPicker) mTimePicker.findViewById(R.id.minute);
        mMinutePicker.setRange(0, 11, DISPLAYED_MINS);
        mMinutePicker.setSpeed(100);
        mMinutePicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        mMinutePicker.setOnChangeListener(new NumberPicker.OnChangedListener() {
            public void onChanged(NumberPicker spinner, int oldVal, int newVal) {
                mCurrentMinute = newVal;
                onTimeChanged();
            }
        });
    }

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        updateTitle(hourOfDay, minute);
    }

    private void updateTitle(int hour, int minute) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hour);
        mCalendar.set(Calendar.MINUTE, minute);
        setTitle(mDateFormat.format(mCalendar.getTime()));
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt(HOUR, mTimePicker.getCurrentHour());
        state.putInt(MINUTE, mTimePicker.getCurrentMinute());
        state.putBoolean(IS_24_HOUR, mTimePicker.is24HourView());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int hour = savedInstanceState.getInt(HOUR);
        int minute = savedInstanceState.getInt(MINUTE);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);
        mTimePicker.setIs24HourView(savedInstanceState.getBoolean(IS_24_HOUR));
        mTimePicker.setOnTimeChangedListener(this);
        updateTitle(hour, minute);
    }
}