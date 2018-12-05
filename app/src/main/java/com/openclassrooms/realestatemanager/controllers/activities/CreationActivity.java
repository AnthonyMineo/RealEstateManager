package com.openclassrooms.realestatemanager.controllers.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.models.local.immovables.Vicinity;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class CreationActivity extends BaseActivity {

    // FOR DESIGN
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.type_edit_text)
    EditText typeEditText;
    @BindView(R.id.price_edit_text)
    EditText priceEditText;
    @BindView(R.id.surface_edit_text)
    EditText surfaceEditText;
    @BindView(R.id.piece_number_edit_text)
    EditText pieceNumberEditText;
    @BindView(R.id.bath_number_edit_text)
    EditText bathNumberEditText;
    @BindView(R.id.bed_number_edit_text)
    EditText bedNumberEditText;
    @BindView(R.id.address_edit_text)
    EditText addressEditText;
    @BindView(R.id.cpt_address_edit_text)
    EditText cptAddressEditText;
    @BindView(R.id.city_edit_text)
    EditText cityEditText;
    @BindView(R.id.postal_code_edit_text)
    EditText postalCodeEditText;
    @BindView(R.id.country_edit_text)
    EditText countryEditText;
    @BindView(R.id.description_edit_text)
    EditText descriptionEditText;
    @BindView(R.id.check_box_school)
    CheckBox schoolCheckBox;
    @BindView(R.id.check_box_market)
    CheckBox marketCheckBox;
    @BindView(R.id.check_box_bus)
    CheckBox busCheckBox;
    @BindView(R.id.check_box_sport)
    CheckBox sportCheckBox;
    @BindView(R.id.check_box_monument)
    CheckBox monumentCheckBox;
    @BindView(R.id.check_box_park)
    CheckBox parkCheckBox;

    // FOR DATA
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ImmoViewModel immoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init Dagger
        this.configureDagger();

        this.configureToolBar();
        this.configureViewModel();
    }

    // --------------------
    // GETTERS
    // --------------------

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_creation;
    }

    // --------------------
    // ACTIONS
    // --------------------

    private void configureDagger(){
        AndroidInjection.inject(this);
    }

    // - Configure Toolbar
    private void configureToolBar() {
        toolbar.setTitle(getResources().getString(R.string.activity_creation_title));
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void configureViewModel(){
        immoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImmoViewModel.class);
        immoViewModel.initCurrentUser(USER_ID);
    }

    @OnClick(R.id.activity_creation_validation_button)
    public void submit(){
        String type = this.typeEditText.getText().toString();
        int price = Integer.valueOf(this.priceEditText.getText().toString());
        int surface = Integer.valueOf(this.surfaceEditText.getText().toString());
        int pieceNumber = Integer.valueOf(this.pieceNumberEditText.getText().toString());
        int bathNumber = Integer.valueOf(this.bathNumberEditText.getText().toString());
        int bedNumber = Integer.valueOf(this.bedNumberEditText.getText().toString());
        String description = this.descriptionEditText.getText().toString();

        String address = this.addressEditText.getText().toString();
        String cptAddress = this.cptAddressEditText.getText().toString();
        String city = this.cityEditText.getText().toString();
        String postalCode = this.postalCodeEditText.getText().toString();
        String country = this.countryEditText.getText().toString();
        Vicinity vicinity = new Vicinity(address, cptAddress, city, postalCode, country);

        Immo newImmoToCreate = new Immo(type, price, surface, pieceNumber, bathNumber, bedNumber, description, vicinity, Utils.getTodayDate(), USER_ID);
        ImmoCreation(newImmoToCreate);
    }

    private void ImmoCreation(Immo immo){
        immoViewModel.createImmo(immo);
    }


}
