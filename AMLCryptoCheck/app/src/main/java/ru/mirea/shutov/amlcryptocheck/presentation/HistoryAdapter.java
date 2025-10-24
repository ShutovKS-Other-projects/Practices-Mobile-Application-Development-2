package ru.mirea.shutov.amlcryptocheck.presentation;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import ru.mirea.shutov.amlcryptocheck.R;
import ru.mirea.shutov.domain.models.WalletCheck;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<WalletCheck> historyList = new ArrayList<>();
    private Context context;

    public HistoryAdapter(Context context) {
        this.context = context;
    }

    public void setHistoryList(List<WalletCheck> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        WalletCheck item = historyList.get(position);
        holder.textViewAddress.setText(item.getAddress());

        int riskScore = item.getRiskScore();
        if (riskScore > 75) {
            holder.textViewRisk.setText("High Risk");
            holder.textViewRisk.setTextColor(ContextCompat.getColor(context, R.color.risk_high));
        } else if (riskScore > 40) {
            holder.textViewRisk.setText("Medium Risk");
            holder.textViewRisk.setTextColor(ContextCompat.getColor(context, R.color.risk_medium));
        } else {
            holder.textViewRisk.setText("Low Risk");
            holder.textViewRisk.setTextColor(ContextCompat.getColor(context, R.color.risk_low));
        }

        String iconUrl = item.getCurrencyIconUrl();
        Log.d("HistoryAdapter", "Icon URL: " + iconUrl);
        if (!TextUtils.isEmpty(iconUrl)) {
            Log.d("HistoryAdapter", "Loading icon from URL: " + iconUrl);
            Picasso.get()
                    .load(iconUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .into(holder.imageViewIcon);
        } else {
            holder.imageViewIcon.setImageResource(R.drawable.ic_placeholder);
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewIcon;
        TextView textViewAddress;
        TextView textViewRisk;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewIcon = itemView.findViewById(R.id.imageViewCurrencyIcon);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewRisk = itemView.findViewById(R.id.textViewRisk);
        }
    }
}