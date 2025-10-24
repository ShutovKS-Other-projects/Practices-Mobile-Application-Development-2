package ru.mirea.shutov.amlcryptocheck.presentation;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.mirea.shutov.amlcryptocheck.R;

public class HistoryFragment extends Fragment {

    private MainViewModel mainViewModel;
    private HistoryAdapter historyAdapter;
    private EditText editTextWalletAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(MainViewModel.class);

        editTextWalletAddress = view.findViewById(R.id.editTextWalletAddress);
        RecyclerView recyclerViewHistory = view.findViewById(R.id.recyclerViewHistory);

        historyAdapter = new HistoryAdapter(requireContext());
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewHistory.setAdapter(historyAdapter);

        mainViewModel.getHistoryToDisplay().observe(getViewLifecycleOwner(), historyList -> {
            if (historyList != null) {
                historyAdapter.setHistoryList(historyList);
            }
        });

        view.findViewById(R.id.buttonCheckRisk).setOnClickListener(v -> {
            String address = editTextWalletAddress.getText().toString().trim();
            if (!TextUtils.isEmpty(address)) {
                mainViewModel.onCheckRiskClicked(address);
                editTextWalletAddress.setText("");
            } else {
                Toast.makeText(requireContext(), "Please enter a wallet address", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.buttonScan).setOnClickListener(v ->
                Toast.makeText(requireContext(), "Scan feature coming soon!", Toast.LENGTH_SHORT).show());
    }
}