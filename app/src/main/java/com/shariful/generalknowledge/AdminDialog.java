package com.shariful.generalknowledge;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdminDialog extends AppCompatDialogFragment {
    EditText pinNumber;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      String pin=pinNumber.getText().toString();
                      if(pin.equals("1234"))
                      {
                          Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                          Intent intent =new Intent(getActivity(),NoticeActivity.class);
                          startActivity(intent);
                      }
                    }
                });

        pinNumber=view.findViewById(R.id.pinID);


        return  builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }

    public interface AdminDialogListener{

        void applyTexts(String pinNumber);

    }


}
