package com.openclassrooms.realestatemanager.controllers.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    // FOR DESIGN
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.min_price_edit_text)
    EditText minPriceEditText;
    @BindView(R.id.max_price_edit_text)
    EditText maxPriceEditText;
    @BindView(R.id.min_surface_edit_text)
    EditText minSurfaceEditText;
    @BindView(R.id.max_surface_edit_text)
    EditText maxSurfaceEditText;
    @BindView(R.id.city_edit_text)
    EditText cityEditText;
    @BindView(R.id.photo_edit_text)
    EditText minPhotoNumberEditText;
    @BindView(R.id.check_box_school)
    CheckBox checkBoxSchool;
    @BindView(R.id.check_box_market)
    CheckBox checkBoxMarket;
    @BindView(R.id.check_box_bus)
    CheckBox checkBoxBus;
    @BindView(R.id.check_box_sport)
    CheckBox checkBoxSport;
    @BindView(R.id.check_box_monument)
    CheckBox checkBoxMonument;
    @BindView(R.id.check_box_park)
    CheckBox checkBoxPark;
    @BindView(R.id.enter_date_button)
    ImageView enterDateImageView;
    @BindView(R.id.selling_date_button)
    ImageView sellingDateImageView;
    @BindView(R.id.enter_date_user_choice_text_view)
    TextView enterDateUserChoiceTextView;
    @BindView(R.id.selling_date_user_choice_text_view)
    TextView sellingDateUserChoiceTextView;

    // FOR DATA
    private int day;
    private int month;
    private int year;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.configureToolBar();
        this.configureDatePicker();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_search;
    }

    // - Configure Toolbar
    private void configureToolBar() {
        toolbar.setTitle(getResources().getString(R.string.search_title));
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void configureDatePicker(){
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);

        enterDateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog(view);
            }
        });

        sellingDateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog(view);
            }
        });
    }

    private void DateDialog(final View view){
        // - Allow the user to pick the date he what from the datePicker widget
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String sDay = String.valueOf(day);
                String sMonth = String.valueOf(month+1);
                String sYear = String.valueOf(year);

                if (day < 10)
                    sDay = "0" + sDay;
                if (month < 10)
                    sMonth = "0" + sMonth;

                if (view.equals(sellingDateImageView))
                    sellingDateUserChoiceTextView.setText(sDay + "/" + sMonth + "/" + sYear);
                if (view.equals(enterDateImageView))
                    enterDateUserChoiceTextView.setText(sDay + "/" + sMonth + "/" + sYear);
            }
        };

        DatePickerDialog dpDialog = new DatePickerDialog(this, R.style.DatePickerTheme, listener, year, month, day);
        dpDialog.show();
    }

    @OnClick(R.id.search_button)
    public void submit(){

        int minPrice = -1;
        int maxPrice = -1;
        int minSurface = -1;
        int maxSurface = -1;
        int minPhotoNumber = -1;

        // Getter
        if(!this.minPriceEditText.getText().toString().equals(""))
            minPrice = Integer.parseInt(this.minPriceEditText.getText().toString());
        if(!this.maxPriceEditText.getText().toString().equals(""))
            maxPrice = Integer.parseInt(this.maxPriceEditText.getText().toString());
        if(!this.minSurfaceEditText.getText().toString().equals(""))
            minSurface = Integer.parseInt(this.minSurfaceEditText.getText().toString());
        if(!this.maxSurfaceEditText.getText().toString().equals(""))
            maxSurface = Integer.parseInt(this.maxSurfaceEditText.getText().toString());
        if(!this.minPhotoNumberEditText.getText().toString().equals(""))
            minPhotoNumber = Integer.parseInt(this.minPhotoNumberEditText.getText().toString());

        String city = this.cityEditText.getText().toString();

        ArrayList<String> poi = new ArrayList<>();
        if(this.checkBoxSchool.isChecked())
            poi.add(getResources().getString(R.string.poi_school));
        if(this.checkBoxMarket.isChecked())
            poi.add(getResources().getString(R.string.poi_market));
        if(this.checkBoxBus.isChecked())
            poi.add(getResources().getString(R.string.poi_bus));
        if(this.checkBoxSport.isChecked())
            poi.add(getResources().getString(R.string.poi_sport));
        if(this.checkBoxMonument.isChecked())
            poi.add(getResources().getString(R.string.poi_monument));
        if(this.checkBoxPark.isChecked())
            poi.add(getResources().getString(R.string.poi_park));

        int enterDate = -1;
        int sellingDate = -1;

        if(!this.enterDateUserChoiceTextView.getText().toString().equals(""))
            enterDate = Utils.changeDateFormat(this.enterDateUserChoiceTextView.getText().toString());

        if(!this.sellingDateUserChoiceTextView.getText().toString().equals(""))
            sellingDate = Utils.changeDateFormat(this.sellingDateUserChoiceTextView.getText().toString());

        // Result to set
        Intent data = new Intent();
        data.putExtra("minPrice", minPrice);
        data.putExtra("maxPrice", maxPrice);
        data.putExtra("minSurface", minSurface);
        data.putExtra("maxSurface", maxSurface);
        data.putExtra("city", city);
        data.putExtra("minPhotoNumber", minPhotoNumber);
        data.putStringArrayListExtra("poi", poi);
        data.putExtra("enterDate", enterDate);
        data.putExtra("sellingDate", sellingDate);
        setResult(RESULT_OK, data);

        this.finish();
    }

}
