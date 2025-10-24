package ru.mirea.shutov.amlcryptocheck.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.shutov.amlcryptocheck.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private TextView textViewUserEmail;
    private Button buttonLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(ProfileViewModel.class);
        textViewUserEmail = view.findViewById(R.id.textViewUserEmail);
        buttonLogout = view.findViewById(R.id.buttonLogout);

        profileViewModel.getUserEmail().observe(getViewLifecycleOwner(), email ->
                textViewUserEmail.setText(email));

        profileViewModel.getLogoutEvent().observe(getViewLifecycleOwner(), loggedOut -> {
            if (loggedOut != null && loggedOut) {
                navigateToLogin();
            }
        });

        buttonLogout.setOnClickListener(v -> {
            profileViewModel.logout();
        });

        profileViewModel.loadUserData();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}