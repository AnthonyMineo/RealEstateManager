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

public class EditionActivity extends BaseActivity {

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
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init Dagger
        this.configureDagger();

        this.initExtras();
        this.configureToolBar();
        this.configureViewModel();
        this.configureUI(immoViewModel.getSelectedImmo());
    }

    // --------------------
    // GETTERS
    // --------------------

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_edition;
    }

    // --------------------
    // ACTIONS
    // --------------------

    private void configureDagger(){
        AndroidInjection.inject(this);
    }

    private void initExtras(){
        this.mode = getIntent().getExtras().getInt("editionMode");
    }

    // - Configure Toolbar
    private void configureToolBar() {
        if(this.mode == 0){
            toolbar.setTitle(getResources().getString(R.string.activity_edition_creation_title));
        } else {
            toolbar.setTitle(getResources().getString(R.string.activity_edition_editing_title));
        }
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

    private void configureUI(Immo selectedImmo){
        if(this.mode == 1 && selectedImmo != null){
            this.typeEditText.setText(selectedImmo.getType());
            this.priceEditText.setText(String.valueOf(selectedImmo.getPrice()));
            this.surfaceEditText.setText(String.valueOf(selectedImmo.getSurface()));
            this.pieceNumberEditText.setText(String.valueOf(selectedImmo.getPieceNumber()));
            this.bathNumberEditText.setText(String.valueOf(selectedImmo.getBathNumber()));
            this.bedNumberEditText.setText(String.valueOf(selectedImmo.getBedNumber()));
            this.addressEditText.setText(selectedImmo.getVicinity().getAddress());
            this.cptAddressEditText.setText(selectedImmo.getVicinity().getCptAddress());
            this.cityEditText.setText(selectedImmo.getVicinity().getCity());
            this.postalCodeEditText.setText(selectedImmo.getVicinity().getPostalCode());
            this.countryEditText.setText(selectedImmo.getVicinity().getCountry());
            this.descriptionEditText.setText(selectedImmo.getDescription());

            this.configureCheckBox(selectedImmo);
        }
    }

    private void configureCheckBox(Immo selectedImmo){
        for(String poi : selectedImmo.getPointsOfInterest()){
            switch (poi){
                case "School":
                    this.schoolCheckBox.setChecked(true);
                    break;
                case "Market":
                    this.marketCheckBox.setChecked(true);
                    break;
                case "Bus" :
                    this.busCheckBox.setChecked(true);
                    break;
                case "Sport" :
                    this.sportCheckBox.setChecked(true);
                    break;
                case "Monument" :
                    this.monumentCheckBox.setChecked(true);
                    break;
                case "Park" :
                    this.parkCheckBox.setChecked(true);
                    break;
            }
        }
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

        Immo newImmo = new Immo(type, price, surface, pieceNumber, bathNumber, bedNumber, description, vicinity, Utils.getTodayDate(), USER_ID);
        ImmoAction(newImmo);
    }

    private void ImmoAction(Immo immo){
        if(this.mode == 0){
            immoViewModel.createImmo(immo);
        } else {
            immoViewModel.updateImmo(immo);
        }

    }


}
