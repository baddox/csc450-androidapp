package cms.org;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class fillup extends Activity
{
	private EditText etOdometer;
	private EditText etGallons;
	private EditText etVin;
	private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fillup);
		
		etOdometer = (EditText)findViewById(R.id.txtMiles);
		etGallons = (EditText)findViewById(R.id.txtGallons);
		etVin = (EditText) findViewById(R.id.txtVin);
		
		settings = PreferenceManager.getDefaultSharedPreferences(this);
		
		View fillupButton = findViewById(R.id.btnFillUp);
		fillupButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v){
				// TODO Auto-generated method stub
				String baseurl = "http://cars.tshaddox.com/api/fillup";
				String username = "?username=";
				String password = "&password=";
				String vin = "&vin=";
				String odometer= "&odometer=";
				String gallons ="&gallons=";
				String fullurl ="";
				
				odometer += etOdometer.getText().toString().trim();
				gallons += etGallons.getText().toString().trim();
				vin += etVin.getText().toString().trim();
				
				username += settings.getString("username","user" );
				password += settings.getString("userpass", "password");
				
				if(etOdometer.getText().length() != 0 && etGallons.getText().length() != 0)
				{
		
					fullurl = baseurl + username + password + vin + odometer + gallons;
				
					System.out.println(fullurl.toString());
				
					try{
					
				
						HttpClient client = new DefaultHttpClient();
						HttpGet getmethod = new HttpGet(fullurl);
						HttpResponse response = client.execute(getmethod);
				
		
						BufferedReader in = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));
						StringBuilder str = new StringBuilder();
						String line="";
				

						while ((line = in.readLine()) != null)
						{
							str.append(line);
						}
				
						System.out.println("Returned = " + str.toString());
					
						in.close();
						
						/*Context mContext = getBaseContext();
						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
						builder.setMessage(str.toString())
						       .setCancelable(false)
						       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						                dialog.cancel();
						           }
						       });
						builder.show();*/
						
						
						//TextView t = (TextView) findViewById(R.id.output);
						//t.setText(str.toString());
						
						Toast.makeText(getBaseContext(), str.toString(), Toast.LENGTH_LONG).show();
						
					etOdometer.setText("");
					etGallons.setText("");
					}
					catch(Exception ex)
					{
						System.out.println("error " + ex.getMessage());
					}
					
				}//end if
				
				
			}
		
		});
	}
	
}
