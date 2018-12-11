package com.openclassrooms.realestatemanager.controllers.activities;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.models.local.immovables.Picture;
import com.openclassrooms.realestatemanager.models.local.immovables.Vicinity;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;
import com.openclassrooms.realestatemanager.utils.LocalStorageHelper;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;
import com.openclassrooms.realestatemanager.views.PhotoAdapter;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

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
    @BindView(R.id.activity_edition_recycler_view)
    RecyclerView recyclerView;

    // FOR DATA
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ImmoViewModel immoViewModel;
    private PhotoAdapter photoAdapter;
    private Executor executor = Executors.newSingleThreadExecutor();
    private int mode;
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
        this.configureUI(immoViewModel.getSelectedImmo());
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

    // - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // - Create adapter passing the list of Restaurants
        this.photoAdapter = new PhotoAdapter(1);
        // - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.photoAdapter);
        // - Set layout manager to position the items
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        this.recyclerView.setLayoutManager(horizontalLayoutManager);

        ItemClickSupport.addTo(recyclerView, R.layout.photo_list_recycle_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Log.i("editionActivity", "You click on : " + photoAdapter.getPhoto(position));
                });
    }

    private void configureViewModel(){
        immoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImmoViewModel.class);
        immoViewModel.initCurrentUser(USER_ID);
    }

    private void configureUI(LiveData<Immo> selectImmo){
        if(this.mode == 1 && selectImmo != null){
            Immo selectedImmo = selectImmo.getValue();

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

    @OnClick(R.id.activity_edition_gallery_button)
    @AfterPermissionGranted(RC_IMAGE_PERMS)
    public void addImageToGallery(){
        if (!EasyPermissions.hasPermissions(this, PERMS)) {
            EasyPermissions.requestPermissions(this, "test", RC_IMAGE_PERMS, PERMS);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Picture newPic = new Picture("", Utils.getRealPathFromURI(uri, this), Utils.getRealFileNameFromURI(uri, this), uri.toString());

            immoViewModel.getTempImmo().addToGallery(newPic);
            this.photoAdapter.addData(newPic);
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

        Immo newImmo = immoViewModel.getTempImmo();

        for(Picture pic : newImmo.getGallery()){
            // create path to the new intern file
            File file = LocalStorageHelper.createOrGetFile(getFilesDir(), pic.getFileName());
            String truePath = file.getPath();
            truePath = truePath.substring(0, truePath.length()-pic.getFileName().length());
            pic.setPath(truePath);

        }

        newImmo.setType(type);
        newImmo.setPrice(price);
        newImmo.setSurface(surface);
        newImmo.setPieceNumber(pieceNumber);
        newImmo.setBathNumber(bathNumber);
        newImmo.setBedNumber(bedNumber);
        newImmo.setDescription(description);
        newImmo.setVicinity(vicinity);
        newImmo.setEnterDate(Utils.getTodayDate());
        newImmo.setAgentId(USER_ID);

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

    }


}
