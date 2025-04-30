package com.app.smartcalendarapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    EditText editName, editEmail;
    Switch switchTheme;
    Button btnSave;
    SharedPreferences sharedPreferences;

    public static final String PREFS_NAME = "user_prefs";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_THEME = "dark_mode";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editName = view.findViewById(R.id.editName);
        editEmail = view.findViewById(R.id.editEmail);
        switchTheme = view.findViewById(R.id.switchTheme);
        btnSave = view.findViewById(R.id.btnSave);

        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Load user data and theme preferences
        loadUserData();

        // Handle theme switch toggle
        // Toggle the theme based on the switch's state
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the theme preference
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_THEME, isChecked);
            editor.apply();

            // Apply the selected theme globally
            if (isChecked) {
                // Dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                // Light mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            // Recreate the activity to apply the theme change
            getActivity().recreate();
        });


        // Handle saving user data
        btnSave.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_NAME, name);
            editor.putString(KEY_EMAIL, email);
            editor.apply();

            Toast.makeText(getContext(), "Profile saved", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void loadUserData() {
        String name = sharedPreferences.getString(KEY_NAME, "");
        String email = sharedPreferences.getString(KEY_EMAIL, "");
        boolean isDarkMode = sharedPreferences.getBoolean(KEY_THEME, false);

        editName.setText(name);
        editEmail.setText(email);
        switchTheme.setChecked(isDarkMode);
    }
}
