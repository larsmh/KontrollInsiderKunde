package com.insider.kontrollkunde;

import java.util.ArrayList;

import com.insider.kontrollkunde.model.Customer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

public class QualityDialog extends DialogFragment{

	Customer cust;
	ArrayList<Mail> emailList;
	public QualityDialog(Customer cust, ArrayList<Mail> emailList) {
		this.emailList = emailList;
		this.cust = cust;
		
	}
	
	  @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setItems(R.array.quality_report_types, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
			    	Intent intent = new Intent(getActivity(), QualityReportActivity.class);
			    	intent.putExtra("choice", ""+which);
			    	intent.putExtra("customerObject", cust);
			    	startActivity(intent);
				}
			});
	      
	        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	  }
	  
	 
}
