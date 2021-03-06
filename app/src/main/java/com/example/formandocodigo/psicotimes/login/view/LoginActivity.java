package com.example.formandocodigo.psicotimes.login.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.formandocodigo.psicotimes.R;
import com.example.formandocodigo.psicotimes.login.net.entity.ApiError;
import com.example.formandocodigo.psicotimes.login.net.entity.RegisterResponse;
import com.example.formandocodigo.psicotimes.login.net.ApiService;
import com.example.formandocodigo.psicotimes.login.net.RetrofitBuilder;
import com.example.formandocodigo.psicotimes.login.repository.LoginRepositoryImpl;
import com.example.formandocodigo.psicotimes.utils.Converts;
import com.example.formandocodigo.psicotimes.post.SplashScreenActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.txt_name)
    TextInputLayout txtName;
    @BindView(R.id.txt_email)
    TextInputLayout txtEmail;
    @BindView(R.id.txt_birth_date)
    TextInputLayout txtBirthDate;
    @BindView(R.id.txt_use_time)
    TextInputLayout txtUseTime;
    @BindView(R.id.txt_dni)
    TextInputLayout txtDni;
    @BindView(R.id.spinner_sex)
    Spinner spiSex;
    @BindView(R.id.spinner_occupation)
    Spinner spiOccupation;
    @BindView(R.id.rbt_yes)
    RadioButton rbtYes;
    @BindView(R.id.rbt_no)
    RadioButton rbtNo;
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;

    @BindView(R.id.txt_birth_date_edit)
    TextInputEditText txtBirthDatePicker;

    String sex, state;
    Boolean isWorking = false;

    AwesomeValidation validator;

    LoginRepositoryImpl repository;

    ApiService service;
    Call<RegisterResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        repository = new LoginRepositoryImpl(this);

        fillSpinner();

        if (repository.isExistsPreferences()) {
            Intent intent = new Intent(LoginActivity.this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
        }

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                updateLabel(calendar);
            }
        };

        txtBirthDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(LoginActivity.this, date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        service = RetrofitBuilder.createService(ApiService.class);
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        setupRules();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null)  {
            call.cancel();
            call = null;
        }
    }

    @OnClick(R.id.btn_start)
    void onButtonStartClick(View e) {
        showProgressBar();
        selectsSpinner();
        selectedWorking();
        String name = txtName.getEditText().getText().toString();
        String email = txtEmail.getEditText().getText().toString();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String getDate = txtBirthDate.getEditText().getText().toString();
        String dni = txtDni.getEditText().getText().toString();

        txtName.setError(null);
        txtEmail.setError(null);
        txtBirthDate.setError(null);
        txtUseTime.setError(null);
        txtDni.setError(null);

        validator.clear();

        if (validator.validate()) {
            Integer timeUse = Integer.parseInt(txtUseTime.getEditText().getText().toString());
            Date birthDate = new Date();
            try {
                birthDate = format.parse(getDate);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            call = service.register(name, email, birthDate, dni, sex, state, isWorking, timeUse);
            call.enqueue(new Callback<RegisterResponse>() {

                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    Log.w(TAG, "onResponse: " + response);

                    if (response.isSuccessful()) {
                        Log.w(TAG, "onResponse: " + response.body());
                        repository.signIn(response.body());

                        startActivity(new Intent(LoginActivity.this, SplashScreenActivity.class));
                    } else {
                        handleErrors(response.errorBody());
                    }

                    hideProgressBar();
                }
                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());

                    hideProgressBar();
                }
            });
        }
        hideProgressBar();
    }

    public void setupRules() {
        validator.addValidation(this, R.id.txt_name, RegexTemplate.NOT_EMPTY, R.string.error_field_required);
        validator.addValidation(this, R.id.txt_email, Patterns.EMAIL_ADDRESS, R.string.error_invalid_email);
        validator.addValidation(this, R.id.txt_birth_date, RegexTemplate.NOT_EMPTY, R.string.error_field_required);
        validator.addValidation(this, R.id.txt_use_time, RegexTemplate.NOT_EMPTY, R.string.error_field_required);
        validator.addValidation(this, R.id.txt_use_time, "[0-9]", R.string.error_field_number);
    }

    private void handleErrors(ResponseBody response) {
        ApiError apiError = Converts.convertErrors(response);

        for (Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()) {
            if (error.getKey().equals("name")) {
                txtName.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("email")) {
                txtEmail.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("birth_date")) {
                txtBirthDate.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("time_use")) {
                txtUseTime.setError(error.getValue().get(0));
            }
        }
    }

    @Override
    public void showProgressBar() {
        loginProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        loginProgress.setVisibility(View.GONE);
    }

    private void selectsSpinner() {
        sex = spiSex.getSelectedItem().toString();
        state = spiOccupation.getSelectedItem().toString();
    }

    private void selectedWorking() {
        if (rbtYes.isSelected()) {
            isWorking = true;
        } else if (rbtNo.isSelected()) {
            isWorking = false;
        }
    }

    private void fillSpinner() {

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_item, getResources().getStringArray(R.array.sex)
        );

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_item, getResources().getStringArray(R.array.occupations)
        );
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        spiOccupation.setAdapter(spinnerAdapter);

        stringArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spiSex.setAdapter(stringArrayAdapter);
    }

    private void updateLabel(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        txtBirthDatePicker.setText(format.format(calendar.getTime()));
    }

}
