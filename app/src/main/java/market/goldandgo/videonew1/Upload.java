package market.goldandgo.videonew1;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Adapter.Spinneradapter1;
import market.goldandgo.videonew1.Fragment.Fragment_Movie;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.Utils.Myalertdialog;
import market.goldandgo.videonew1.Utils.imageCroper.CropImage;
import market.goldandgo.videonew1.Utils.imageCroper.CropImageView;

/**
 * Created by Go Goal on 7/4/2017.
 */

public class Upload extends AppCompatActivity {

    RelativeLayout edit1,edit2,edit3;
    ImageView cover;
    static Zawgyitextview mtitle,mdetail,mlink;
    static Boolean ivcon=false,titlecon=false,detailcon=false,urlcon=false;
    TextView upload;
    private final int GALLERY_ACTIVITY_CODE = 200;
    private final int RESULT_CROP = 400;
    static  AppCompatActivity ac;
    Spinner sp;

    ArrayList<get> clist;
    private File imageFile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movieupload_detail);

        ac=this;

        cover= (ImageView) findViewById(R.id.iv);
        edit1= (RelativeLayout) findViewById(R.id.mtitle_btn);
        edit2= (RelativeLayout) findViewById(R.id.mdetailbtn);
        edit3= (RelativeLayout) findViewById(R.id.murlbtn);
        sp = (Spinner) findViewById(R.id.spinner2);

        mtitle= (Zawgyitextview) findViewById(R.id.title);
        mdetail= (Zawgyitextview) findViewById(R.id.moviedetail);
        mlink= (Zawgyitextview) findViewById(R.id.movieurl);
        upload= (TextView) findViewById(R.id.upload);
        upload.setEnabled(false);

        clist= Fragment_Movie.getcatelist();

        Spinneradapter1 adapteree = new Spinneradapter1(getApplicationContext(), clist);
        sp.setAdapter(adapteree);


        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Quit();


            }
        });

        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT>=24){
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(400, 180)
                            .setFixAspectRatio(true)
                            .start(Upload.this);
                }else{
                  //  contentUri=Uri.fromFile(f);
                    Intent gallery_Intent = new Intent(getApplicationContext(), GalleryUtil.class);
                startActivityForResult(gallery_Intent, GALLERY_ACTIVITY_CODE);
                }
//                Intent gallery_Intent = new Intent(getApplicationContext(), GalleryUtil.class);
//                startActivityForResult(gallery_Intent, GALLERY_ACTIVITY_CODE);

            }
        });

        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(getApplicationContext(),Biged.class);
                it.putExtra("type","title");
                it.putExtra("text",mtitle.getText().toString());
                startActivity(it);
            }
        });

        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(getApplicationContext(),Biged.class);
                it.putExtra("type","detail");
                it.putExtra("text",mdetail.getText().toString());
                startActivity(it);
            }
        });

        edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(getApplicationContext(),Biged.class);
                it.putExtra("type","url");
                it.putExtra("text",mlink.getText().toString());
                startActivity(it);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it=new Intent(getApplicationContext(),Previewmovie.class);
                it.putExtra("a",mtitle.getText().toString());
                it.putExtra("b",mdetail.getText().toString());
                it.putExtra("c",mlink.getText().toString());
                it.putExtra("d",filepath);
                it.putExtra("e",clist.get(sp.getSelectedItemPosition()).getMid());
                startActivity(it);
            }
        });



    }

    private void Quit() {
        if (ivcon || titlecon || detailcon || urlcon){

            Myalertdialog.WILLBEDELETE(ac);

        }else{
            finish();
        }
    }


    static Bitmap selectedBitmap;
    static String filepath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String picturePath = data.getStringExtra("picturePath");
                performCrop(picturePath);
            }
        }

        if (requestCode == RESULT_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                selectedBitmap = extras.getParcelable("data");
                filepath=createfileandgetpath(selectedBitmap);

                Bitmap b= BitmapFactory.decodeFile(filepath);
                BitmapDrawable ob = new BitmapDrawable(getResources(), b);
                cover.setBackgroundDrawable(ob);
                ivcon=true;
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String path = resultUri.getPath();// "/mnt/sdcard/FileName.mp3"
                filepath = path;
////


                imageFile = new File(path);

                Picasso.with(this).load(imageFile).error(R.mipmap.ic_launcher).into(cover);
                ivcon = true;
//
            }
        }
    }


    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri=null;

            if(Build.VERSION.SDK_INT>=24){
                contentUri = FileProvider.getUriForFile(ac,BuildConfig.APPLICATION_ID + ".provider",f);//Uri.fromFile(f);
            }else{
                contentUri=Uri.fromFile(f);
            }


            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 20);
            cropIntent.putExtra("aspectY", 9);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 400);
            cropIntent.putExtra("outputY", 180);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private String createfileandgetpath(Bitmap Bitmap) {
        File f = new File(getCacheDir(), "a.png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

//Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f.toString();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ivcon && titlecon && detailcon && urlcon){

            upload.setTextColor(Color.WHITE);
            upload.setEnabled(true);
        }

    }

    public static void settitle(String s) {
        mtitle.setText(s);
        titlecon=true;
    }

    public static void setdetail(String s) {
        mdetail.setText(s);
        detailcon=true;
    }

    public static void seturl(String s) {

        if (s.startsWith("https://m.facebook.com/story.php?")){
            mlink.setText(s);
            urlcon=true;
        }else{
            Toast.makeText(ac,"Invalid URl",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==4){
            Quit();
        }

        return true;

    }

    public static void clost() {
        ac.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ivcon=false;
        titlecon=false;
        detailcon=false;
        urlcon=false;
    }
}
