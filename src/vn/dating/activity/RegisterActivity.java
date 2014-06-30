package vn.dating.activity;

import java.util.Arrays;
import java.util.List;

import vn.dating.R;
import vn.dating.adapter.NameValueArrayAdapter;
import vn.dating.adapter.NameValueItem;
import vn.dating.task.SignupTask;
import vn.dating.task.form.UserSignUpForm;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Activity which displays a signup screen to the user, offering registration as
 * well.
 */
public class RegisterActivity extends Activity {
	private static List<NameValueItem> MARITAL_STATUSES = Arrays.asList(new NameValueItem[] {
			new NameValueItem("Độc thân", "SINGLE"),
			new NameValueItem("Đang có người yêu", "COUPLE"),
			new NameValueItem("Đã có gia đình", "MARRIED"),
			new NameValueItem("Ly dị", "DIVORCE"),
			new NameValueItem("Độc thân", "SINGLE"),
			new NameValueItem("Ở góa", "RELICT")
		});
	
	private static List<NameValueItem> GENDERS = Arrays.asList(new NameValueItem[] {
			new NameValueItem("Nữ", "FEMALE"),
			new NameValueItem("Nam", "MALE"),
			new NameValueItem("Đồng tính nữ", "LESBIAN"),
			new NameValueItem("Đồng tính nam", "GAY")
		});

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";


	// Values for email and password at the time of the signup attempt.
	private String mEmail;
	private String mPassword;
	private String mGender;
	private String mAboutMe;
	private String mFullName;
	private String mMaritalStatus;
	
	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private EditText mFullNameView;
	private EditText mAboutMeView;
	private Spinner mGenderView;
	private Spinner mMaritalStatusView;
	
	private View mSignupFormView;
	private View mSignupStatusView;
	private TextView mSignupStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		// Set up the signup form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.signup || id == EditorInfo.IME_NULL) {
							attemptSignup();
							return true;
						}
						return false;
					}
				});

		mAboutMeView = (EditText) findViewById(R.id.aboutMe);
		mFullNameView = (EditText) findViewById(R.id.fullName);
		mGenderView = (Spinner) findViewById(R.id.gender);
		mGenderView.setAdapter(new NameValueArrayAdapter(this, android.R.layout.simple_spinner_item, GENDERS));
		
		mMaritalStatusView = (Spinner) findViewById(R.id.maritalStatus);
		mMaritalStatusView.setAdapter(new NameValueArrayAdapter(this, android.R.layout.simple_spinner_item, MARITAL_STATUSES));
		
		mSignupFormView = findViewById(R.id.signup_form);
		mSignupStatusView = findViewById(R.id.signup_status);
		mSignupStatusMessageView = (TextView) findViewById(R.id.signup_status_message);

		findViewById(R.id.sign_up_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptSignup();
					}
				});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
	    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
	    startActivityForResult(myIntent, 0);
	    return true;

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	/**
	 * Attempts to register the account specified by the signup form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual signup attempt is made.
	 */
	public void attemptSignup() {
		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the signup attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mGender = ((NameValueItem)mGenderView.getSelectedItem()).getValue();
		mAboutMe = mAboutMeView.getText().toString();
		mFullName = mFullNameView.getText().toString();
		mMaritalStatus = ((NameValueItem)mMaritalStatusView.getSelectedItem()).getValue();
		
		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_password_too_short));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt signup and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user signup attempt.
			mSignupStatusMessageView.setText(R.string.signup_progress_signing_up);
			showProgress(true);
			UserSignUpForm userSignupForm = new UserSignUpForm();
			userSignupForm.setAcceptAgreement(true);
			userSignupForm.setEmail(mEmail);
			userSignupForm.setPassword(mPassword);
			userSignupForm.setConfirmPassword(mPassword);
			userSignupForm.setGender(mGender);
			userSignupForm.setFullName(mFullName);
			userSignupForm.setAboutMe(mAboutMe);
			userSignupForm.setMaritalStatus(mMaritalStatus);
			SignupTask signupTask = new SignupTask(this, userSignupForm);
			signupTask.execute(new String[] {mEmail, mPassword});
		}
	}

	/**
	 * Shows the progress UI and hides the signup form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mSignupStatusView.setVisibility(View.VISIBLE);
			mSignupStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mSignupStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mSignupFormView.setVisibility(View.VISIBLE);
			mSignupFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mSignupFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mSignupStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mSignupFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	public void setMailError(String statusMessage) {
		mEmailView.setError(statusMessage);
		mEmailView.requestFocus();
	}
}
