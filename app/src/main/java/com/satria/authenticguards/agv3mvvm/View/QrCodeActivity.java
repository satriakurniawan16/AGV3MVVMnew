package com.satria.authenticguards.agv3mvvm.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.zxing.Result;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.utils.JsonUtil;

import org.json.JSONObject;

import java.util.Objects;

public class QrCodeActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private FirebaseUser mFirebaseUser;
    private static final int RC_PERMISSION = 10;
    private boolean mPermissionGranted;
    final int REQUEST_CODE_CAMERA = 999;
    private String name, address, phone, web, product;
    private String GENIUNE_CODE = "success";
    private String token = "";
    private String rvalid;
    private String history;
    String GCODE = "";
    String size,color,material,price,distributor,expiredDate,img,brand,company,email;
    ProgressDialog pDialog;
    private double longitude, latitude;
    CodeScannerView scannerView;
    JsonUtil jsonUtil=new JsonUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        scannerView=findViewById(R.id.scanner_view);
        mFirebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        customizeFirebase();
        customizeScanner();
        getLocation();

    }

    private void customizeFirebase(){
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser!=null;
        firebaseUser.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<GetTokenResult> task) {
                token=Objects.requireNonNull(task.getResult().getToken());
                String url="https://admin.authenticguards.com/api/getuser?token=" + token + "&appid=003";
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                Volley.newRequestQueue(QrCodeActivity.this).add(jsonObjectRequest);
            }
        });
    }
    private void customizeScanner(){
        mCodeScanner=new CodeScanner(this,scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                QrCodeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToneGenerator toneGenerator=new ToneGenerator(AudioManager.STREAM_ALARM,100);
                        toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD,200);
                        Vibrator v=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                            v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
                        }else{
                            v.vibrate(300);
                        }
                        displayLoader();
                        String resultCode=result.getText();
                        int length=resultCode.length();
                        if (mFirebaseUser!=null){
                            jsonUtil.validation_codeQR(getApplicationContext(),result.getText(),token,latitude,longitude,pDialog);
                        }else {
                            startActivity(new Intent(QrCodeActivity.this,LoginActivity.class));
                        }

                    }
                });
            }
        });
        mCodeScanner.setErrorCallback(error -> runOnUiThread(()-> Toast.makeText(this, "Scan Error", Toast.LENGTH_SHORT).show()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = false;
                requestPermissions(new String[] {Manifest.permission.CAMERA}, RC_PERMISSION);
            } else {
                mPermissionGranted = true;
            }
        } else {
            mPermissionGranted = true;
        }
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }
    protected void getLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.d("lol", "latitude"+latitude+"longitude"+longitude);
                }
            }
        });
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(QrCodeActivity.this);
        pDialog.setMessage("Scanning...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPermissionGranted) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCodeScanner.releaseResources();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = true;
                mCodeScanner.startPreview();
            } else {
                mPermissionGranted = false;
            }
        }
    }
}
