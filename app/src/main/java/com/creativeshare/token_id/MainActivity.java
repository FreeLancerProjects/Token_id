package com.creativeshare.token_id;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.creativeshare.token_id.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

  binding.btnCopy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          if(token!=null){
          ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
          ClipData clip = ClipData.newPlainText("token", token);
          clipboard.setPrimaryClip(clip);}

      }
  });
  binding.btnSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          if(token!=null){
          Intent i = new Intent(android.content.Intent.ACTION_SEND);
          i.setType("text/plain");
          i.putExtra(android.content.Intent.EXTRA_TEXT, token);
          startActivity(i);
          }
      }
  });
updateToken();
    }
    private void updateToken() {
        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                           token = task.getResult().getToken();
                            binding.token.setText(token);
                            //Log.e("s",token);

                        }
                    }
                });
    }

}
