package com.didaagency.adopteunelivraison.utils.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.didaagency.adopteunelivraison.R;
import com.didaagency.adopteunelivraison.databinding.LayoutPhoneNumberBinding;
import com.didaagency.adopteunelivraison.utils.AppUtils;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.listeners.OnCountryPickerListener;


public class CustomPhoneNumber extends RelativeLayout implements View.OnClickListener {

    private String Phone;
    private CountryPicker picker;
    private LayoutPhoneNumberBinding binding;

    public CustomPhoneNumber(Context context) {
        super(context);
    }

    public CustomPhoneNumber(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPhoneNumber(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        binding = LayoutPhoneNumberBinding.inflate(LayoutInflater.from(getContext()), this, false);
        binding.ivFlag.setOnClickListener(this);
        binding.tvCode.setOnClickListener(this);
        addView(binding.getRoot());
        initCode();
    }


    public void initCode() {
        CountryPicker.Builder builder = new CountryPicker.Builder().with(getContext()).listener(onCountryPickerListener);
        picker = builder.build();
//        Country country = picker.getCountryByName("Pakistan"); //Get country by its name
        Country country = picker.getCountryByName("France"); //Get country by its name
        if (country != null) {
            int flag = country.getFlag();  // returns android resource id of flag or -1, if none is associated
            String dialCode = country.getDialCode();
            binding.tvCode.setTag(country.getCode());
            binding.tvCode.setText(dialCode);
            binding.ivFlag.setImageResource(flag);
        }
    }

    public void setText(String text){
        binding.etPhoneNumber.setText(text);
    }
    public void setError(String error){
        binding.etPhoneNumber.setError(error);
    }

    OnCountryPickerListener onCountryPickerListener = new OnCountryPickerListener() {
        @Override
        public void onSelectCountry(Country country) {
            if (country.getName().equalsIgnoreCase("Pakistan")) {
                binding.etPhoneNumber.setHint(getResources().getString(R.string.hint_mobile_number_pak));
            } else {
                binding.etPhoneNumber.setHint(getResources().getString(R.string.hint_general));
            }
            if (getContext() instanceof AppCompatActivity) {
                AppUtils.Companion.hideSoftKeyboard((AppCompatActivity) getContext());
            }
            binding.tvCode.setTag(country.getCode());
            binding.tvCode.setText(country.getDialCode());
            binding.ivFlag.setImageResource(country.getFlag());
        }
    };

    public void showCountryDialog() {
        if (getContext() instanceof AppCompatActivity)
            picker.showBottomSheet((AppCompatActivity) getContext());
    }

    public boolean isValidNumber() {
        Phone = binding.etPhoneNumber.getText().toString().replaceFirst("^0+(?!$)", "");
        String country_code = binding.tvCode.getText().toString();
        Phone = Phone.replace(" ", "");
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber swissNumberProto = null;
        boolean isValid = false;
        try {
            if (!Phone.equals("")) {
                if (binding.tvCode.getTag() != null) {
                    swissNumberProto = phoneUtil.parse(country_code + Phone, binding.tvCode.getTag().toString());
                } else {
                    swissNumberProto = phoneUtil.parse(country_code + Phone, "");
                }
                isValid = phoneUtil.isValidNumber(swissNumberProto); // returns
                Phone = binding.tvCode.getText() + Phone; // true
            }
        } catch (NumberParseException e1) {
        }
//        if (!isValid) {
//            return "Please enter a valid phone number";
//        }
        return isValid;
    }
    public String getNumber() {
        Phone = binding.etPhoneNumber.getText().toString().replaceFirst("^0+(?!$)", "");
        String country_code = binding.tvCode.getText().toString();
        Phone = Phone.replace(" ", "");
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber swissNumberProto = null;
        boolean isValid = false;
        try {
            if (!Phone.equals("")) {
                if (binding.tvCode.getTag() != null) {
                    swissNumberProto = phoneUtil.parse(country_code + Phone, binding.tvCode.getTag().toString());
                } else {
                    swissNumberProto = phoneUtil.parse(country_code + Phone, "");
                }
                isValid = phoneUtil.isValidNumber(swissNumberProto); // returns
                Phone = binding.tvCode.getText() + Phone; // true
            }
        } catch (NumberParseException e1) {
        }
        if (!isValid) {
            return "Please enter a valid phone number";
        }
        return "";
    }

    public String phoneNumber() {
        return Phone;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_flag:
            case R.id.tv_code:
                showCountryDialog();
                break;
        }
    }
}
