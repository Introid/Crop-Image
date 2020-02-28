package com.introid.cropimage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yalantis.ucrop.UCrop;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    final int CODE_IMG_GALLERY= 1;
    String SAMPLED_CROP_IMAGE= "cropImage";
   ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        imageView= findViewById(R.id.imageView);
        imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult( new Intent( ).
                        setAction( Intent.ACTION_GET_CONTENT )
                        .setType( "image/*" ),CODE_IMG_GALLERY
                );
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (resultCode == RESULT_OK && requestCode ==RESULT_OK) {
            Uri imageUri = data.getData();
              if (imageUri!=null){
                  startCrop( imageUri );
              }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Uri imageUriResult= UCrop.getOutput(data);
            if (imageUriResult!= null){
                imageView.setImageURI( imageUriResult );
            }
        }
    }

    private void startCrop(@NonNull Uri uri){
        String destinationFileName= SAMPLED_CROP_IMAGE;
        destinationFileName+=".jpg";

        UCrop uCrop=UCrop.of(uri, Uri.fromFile( new File(getCacheDir(),destinationFileName)));
        uCrop.withAspectRatio( 3,4 );
        uCrop.withMaxResultSize( 450,450 );
        uCrop.withOptions(getCropOptions() );
        uCrop.start( MainActivity.this );

    }
    private UCrop.Options getCropOptions(){
        UCrop.Options options= new UCrop.Options();
        options.setCompressionQuality( 70 );
        options.setHideBottomControls( false );
        options.setFreeStyleCropEnabled( true );

        options.setToolbarColor( getResources().getColor( R.color.colorAccent ) );
        options.setStatusBarColor( getResources().getColor( R.color.colorPrimary ) );
        options.setToolbarTitle( "Image crop" );
        return options;
    }


}
