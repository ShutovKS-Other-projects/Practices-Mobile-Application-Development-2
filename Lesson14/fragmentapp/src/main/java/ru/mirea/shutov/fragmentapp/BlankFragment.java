package ru.mirea.shutov.fragmentapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BlankFragment extends Fragment {

    private static final String TAG = "BlankFragment";
    private static final String ARG_STUDENT_NUMBER = "student_number";

    public BlankFragment() {
    }

    public static BlankFragment newInstance(int studentNumber) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STUDENT_NUMBER, studentNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            int studentNumber = getArguments().getInt(ARG_STUDENT_NUMBER);

            Log.d(TAG, "Номер студента из аргумента: " + studentNumber);

            TextView textView = view.findViewById(R.id.fragment_text);
            textView.setText("Мой номер в списке " + studentNumber);
        }
    }
}