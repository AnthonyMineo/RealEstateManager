package com.openclassrooms.realestatemanager.controllers.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.View;

import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.Toast;


import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.models.local.immovables.Picture;
import com.openclassrooms.realestatemanager.models.local.immovables.Vicinity;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;

import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;
import com.openclassrooms.realestatemanager.views.PhotoAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import pub.devrel.easypermissions.EasyPermissions;

public class EditionActivity extends BaseActivity {

    // FOR DESIGN
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    // Global
    @BindView(R.id.selling_layout)
    LinearLayout sellingLayout;
    @BindView(R.id.check_box_selling)
    CheckBox sellingCheckBox;
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

    // Location
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

    // Description
    @BindView(R.id.description_edit_text)
    EditText descriptionEditText;

    // Points of interest
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

    // Gallery
    @BindView(R.id.activity_edition_recycler_view)
    RecyclerView recyclerView;

    // FOR DATA
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ImmoViewModel immoViewModel;
    private PhotoAdapter photoAdapter;
    private int mode;
    private String picturePlace;
    private final int PICK_IMAGE_REQUEST = 1;
    private final String TAG = "EditionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init Dagger
        this.configureDagger();

        this.initExtras();
        this.configureToolBar();
        this.configureRecyclerView();
        this.configureViewModel();
        this.configureUI(immoViewModel.getSelectedImmo().getValue());
        this.checkForPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 2 - Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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

    private void checkForPermissions(){
        if (!EasyPermissions.hasPermissions(this, READ_EXTERNAL_STORAGE_PERMS)) {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.popup_title_permission), RC_IMAGE_PERMS, READ_EXTERNAL_STORAGE_PERMS);
            return;
        }
    }

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
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    // - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // - Create adapter passing the list of Restaurants
        this.photoAdapter = new PhotoAdapter(ACTIVITY_EDITION_SOURCE);
        // - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.photoAdapter);
        // - Set layout manager to position the items
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.recyclerView.setLayoutManager(horizontalLayoutManager);

        ItemClickSupport.addTo(recyclerView, R.layout.photo_list_recycle_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    if(this.photoAdapter.getItemCount() > 1){
                        this.photoAdapter.deletePhoto(position);
                        if(mode == 0){
                            immoViewModel.getTempImmo().deleteFromGallery(position);
                        } else {
                            immoViewModel.getSelectedImmo().getValue().deleteFromGallery(position);
                        }
                    } else {
                        Toast.makeText(this, R.string.error_photo_number, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void configureViewModel(){
        immoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImmoViewModel.class);
        immoViewModel.initCurrentUser(USER_ID);
    }

    private void configureUI(Immo selectedImmo){
        if(this.mode == 1 && selectedImmo != null){

            this.sellingLayout.setVisibility(View.VISIBLE);
            this.sellingLayout.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.selling_layout_height);

            if(selectedImmo.getSellingDate() == -1){
                sellingCheckBox.setChecked(false);
            } else {
                sellingCheckBox.setChecked(true);
            }

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

            for(Picture pic : selectedImmo.getGallery()){
                this.photoAdapter.addData(pic);
            }
        }
    }

    private void configureCheckBox(Immo selectedImmo){
        for(String poi : selectedImmo.getPointsOfInterest()){
            // Switch don't like not final var
            if(poi.equals(getResources().getString(R.string.poi_school)))
                this.schoolCheckBox.setChecked(true);
            if(poi.equals(getResources().getString(R.string.poi_market)))
                this.marketCheckBox.setChecked(true);
            if(poi.equals(getResources().getString(R.string.poi_bus)))
                this.busCheckBox.setChecked(true);
            if(poi.equals(getResources().getString(R.string.poi_sport)))
                this.sportCheckBox.setChecked(true);
            if(poi.equals(getResources().getString(R.string.poi_monument)))
                this.monumentCheckBox.setChecked(true);
            if(poi.equals(getResources().getString(R.string.poi_park)))
                this.parkCheckBox.setChecked(true);
        }
    }

    @OnClick({R.id.activity_edition_gallery_outdoor, R.id.activity_edition_gallery_kitchen, R.id.activity_edition_gallery_bath,
            R.id.activity_edition_gallery_bed, R.id.activity_edition_gallery_living, R.id.activity_edition_gallery_other})
    public void addImageToGallery(View view){
        this.checkForPermissions();
        setPicturePlace(view);

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        Intent chooser = Intent.createChooser(cameraIntent, getResources().getString(R.string.chooser_text));
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { galleryIntent });
        startActivityForResult(chooser, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Picture newPic = new Picture(this.picturePlace, Utils.getRealFileNameFromURI(uri, this), uri.toString());

            if(mode == 0){
                immoViewModel.getTempImmo().addToGallery(newPic);
            } else {
                immoViewModel.getSelectedImmo().getValue().addToGallery(newPic);
            }

            this.photoAdapter.addData(newPic);
        }
    }

    private void setPicturePlace(View view){
        switch (view.getId()) {
            case R.id.activity_edition_gallery_outdoor:
                this.picturePlace = getResources().getString(R.string.gallery_outdoor);
                break;
            case R.id.activity_edition_gallery_kitchen:
                this.picturePlace = getResources().getString(R.string.gallery_kitchen);
                break;
            case R.id.activity_edition_gallery_bath:
                this.picturePlace = getResources().getString(R.string.gallery_bath);
                break;
            case R.id.activity_edition_gallery_bed:
                this.picturePlace = getResources().getString(R.string.gallery_bed);
                break;
            case R.id.activity_edition_gallery_living:
                this.picturePlace = getResources().getString(R.string.gallery_living);
                break;
            case R.id.activity_edition_gallery_other:
                this.picturePlace = getResources().getString(R.string.gallery_other);
                break;
        }
    }

    @OnClick(R.id.activity_edition_validation_button)
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

        List<String> poi = new ArrayList<>();
        if(this.schoolCheckBox.isChecked())
            poi.add(getResources().getString(R.string.poi_school));
        if(this.marketCheckBox.isChecked())
            poi.add(getResources().getString(R.string.poi_market));
        if(this.busCheckBox.isChecked())
            poi.add(getResources().getString(R.string.poi_bus));
        if(this.sportCheckBox.isChecked())
            poi.add(getResources().getString(R.string.poi_sport));
        if(this.monumentCheckBox.isChecked())
            poi.add(getResources().getString(R.string.poi_monument));
        if(this.parkCheckBox.isChecked())
            poi.add(getResources().getString(R.string.poi_park));

        Immo newImmo = null;

        if(mode == 0){
            newImmo = immoViewModel.getTempImmo();
        } else {
            newImmo = immoViewModel.getSelectedImmo().getValue();
        }

        newImmo.setType(type);
        newImmo.setPrice(price);
        newImmo.setSurface(surface);
        newImmo.setPieceNumber(pieceNumber);
        newImmo.setBathNumber(bathNumber);
        newImmo.setBedNumber(bedNumber);
        newImmo.setDescription(description);
        newImmo.setVicinity(vicinity);
        newImmo.setPointsOfInterest(poi);
        newImmo.setEnterDate(Utils.getTodayDateInt());
        newImmo.setAgentId(USER_ID);
        if(sellingCheckBox.isChecked()){
            newImmo.setSellingDate(Utils.getTodayDateInt());
            newImmo.setStatus(true);
        } else {
            newImmo.setSellingDate(-1);
            newImmo.setStatus(false);
        }

        ImmoAction(newImmo);
    }

    private void ImmoAction(Immo immo){
        // Important to reset TempImmo -> avoid working with the same informations each time you create an new immo preventing to overwrite instead of adding
        immoViewModel.setTempImmo(new Immo());

        if(this.mode == 0){
            immoViewModel.createImmo(immo);
        } else {
            immoViewModel.updateImmo(immo);
        }

        this.finish();
    }


}
