
package info.guardianproject.mrapp;

//import io.scal.secureshareui.lib.ChooseAccountFragment;
import java.util.ArrayList;
import java.util.List;

import info.guardianproject.mrapp.model.Auth;
import io.scal.secureshareui.controller.PublishController.OnPublishEventListener;
import io.scal.secureshareui.lib.ChooseAccountFragment;
import io.scal.secureshareui.model.PublishAccount;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class AccountsActivity extends BaseActivity {

	private ChooseAccountFragment caFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accounts);
		
		Bundle bundle = null;
		Intent intent = getIntent();
		bundle = new Bundle();
		bundle.putBoolean("isDialog", intent.getBooleanExtra("isDialog", false));
		bundle.putBoolean("inSelectionMode", intent.getBooleanExtra("inSelectionMode", false));
		
		addChooseAccountFragment(bundle);
	}

	public void addChooseAccountFragment(Bundle bundle) {
		FragmentManager fragManager = getSupportFragmentManager();
		FragmentTransaction fragTrans = fragManager.beginTransaction();
		
		caFragment = new ChooseAccountFragment(); 
		caFragment.setArguments(bundle);
		
		//TODO test data     
		Auth auth0 = new Auth(this, "Facebook", "facebook.com", "milucas22", "FaKEcreDENTIALS", null, null);
		Auth auth1 = new Auth(this, "Soundcloud", "soundcloud.com", "milucas22", "FaKEcreDENTIALS", null, null);
		Auth auth2 = new Auth(this, "Wordpress", "wordpress.com", "milucas22", "FaKEcreDENTIALS", null, null);
		
		List<PublishAccount> accounts = new ArrayList<PublishAccount>();
		
		accounts.add(auth0.convertToPublishAccountObject());
		accounts.add(auth1.convertToPublishAccountObject());
		accounts.add(auth2.convertToPublishAccountObject());
		
		caFragment.setPublishAccountsList(accounts);  // FIXME we should probably make AccountInfo parcelable and pass this through the bundle
		caFragment.setOnPublishEventListener(new OnPublishEventListener() {

			@Override
			public void onSuccess(PublishAccount publishAccount) {
				Toast.makeText(getApplicationContext(), publishAccount.getName() + ": Login Successful", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(PublishAccount publishAccount, String failureMessage) {
				Toast.makeText(getApplicationContext(), publishAccount.getName() + ": Login Failure - " + failureMessage , Toast.LENGTH_SHORT).show();
			}
		});

		fragTrans.add(R.id.fragmentLayout, caFragment);
		fragTrans.commit();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		caFragment.onActivityResult(requestCode, resultCode, data);
	} 
}
