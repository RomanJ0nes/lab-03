package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    City cityEdit;
    interface AddCityDialogListener {
        void addCity(City city);
        void refresh();
    }
    public void setEditCity(City city) {
        this.cityEdit = city;
    }
    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement add cityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/Edit a city")
                .setNegativeButton("Cancel"
                        , null)
                .setPositiveButton("Confirm"
                        , (dialog, which) -> {
                            String cityName = editCityName.getText().toString().trim();
                            String provinceName = editProvinceName.getText().toString().trim();

                            if (cityEdit == null) {
                                // Add new city
                                listener.addCity(new City(cityName, provinceName));

                            } else {

                                cityEdit.setName(cityName);
                                cityEdit.setProvince(provinceName);
                                listener.refresh();
                            }
                        })
                .create();
    }
}
