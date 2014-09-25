package fr.oversimple.soldemoneo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import fr.oversimple.apdu.ApduAnswer;
import fr.oversimple.apdu.ApduCommand;
import fr.oversimple.apdu.ApduReadRecord;
import fr.oversimple.apdu.BytesOperations;

public class MainActivity extends Activity {

		/* Graphic components */
		private TextView ammountTextView;
	
		/* NFC INTERN */
		private NfcAdapter nfcAdapter;
		private PendingIntent pendingIntent;
		private ApduCardReader nfcReader;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			
			Log.d("On create", "Creating");
			
			
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	        ammountTextView = (TextView) findViewById(R.id.ammountTextview);

	 
	        /* Internal Adapter */
	        // We take the default adapter
			nfcAdapter = NfcAdapter.getDefaultAdapter(this);
			
			if(nfcAdapter != null && nfcAdapter.isEnabled()){
				pendingIntent = PendingIntent.getActivity(this, 0,
						new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
						PendingIntent.FLAG_CANCEL_CURRENT);
			} else if(!nfcAdapter.isEnabled()){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.contactles_enable_nfc);
				builder.setMessage(R.string.contactles_enable_nfc_question);
				builder.setPositiveButton(R.string.contactles_enable_nfc_question_answer_yes, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
						startActivity(intent);
					}
				});
				
				builder.setNegativeButton(R.string.contactles_enable_nfc_question_answer_no,  new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				
				AlertDialog alert = builder.create();
				alert.show();
				
			} else {
				Log.d(MainActivity.class.toString(),"No Internal Nfc Adapter present");
			}
			
			Log.d("On create", "Created");
			
		}

		@Override
		protected void onResume() {
			super.onResume();

			//The nfc was already enable before the onCreate activity.
			if (nfcAdapter != null && nfcAdapter.isEnabled() && pendingIntent != null) {
				nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);

			}
			
			//The nfc is enable by the settings screen
			if(nfcAdapter != null && nfcAdapter.isEnabled() && pendingIntent == null){
				pendingIntent = PendingIntent.getActivity(this, 0,
						new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
						PendingIntent.FLAG_CANCEL_CURRENT);
				nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
			}
		}

		@Override
		protected void onNewIntent(Intent intent) {
			super.onNewIntent(intent);
			setIntent(intent);
			
			Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibe.vibrate(50);
			
			Log.d("NFC intent", "intent catched");
			if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())
					|| (NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent()
							.getAction()))) {
				Tag nfcTag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
				String[] techs = nfcTag.getTechList();
				for (String s : techs) {
					if (s.equals("android.nfc.tech.IsoDep")) {
						nfcReader = new NfcCardReader(IsoDep.get(nfcTag));
						try {
							nfcReader.reset();
							nfcReader.send(new ApduCommand(new byte[]{0,(byte)0xA4,4,0x0C,6,(byte) 0xa0, 0, 0, 0, 0x69, 0x00}));
							ApduAnswer answer = nfcReader.send(new ApduReadRecord((byte) 0x01, (byte) 0xC4));
							
							if(answer.hasData()) {
								byte[] ammountInBytes = new byte[3];
								//09-25 15:33:30.200: D/NFC reader(2161): 0007100100000030009000

								System.arraycopy(answer.getData(), 0, ammountInBytes, 0, 3);
								ammountTextView.setText(Integer.parseInt(BytesOperations.convert(ammountInBytes))/100f + " €");
							}
							
						} catch(Exception e){
							
						}
					}
				}
			}
		}


}
