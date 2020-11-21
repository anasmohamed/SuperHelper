package com.anas.superhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.superhelper.auth.models.Offer;
import com.anas.superhelper.auth.models.RequestHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class OfferRecycleAdapter extends RecyclerView.Adapter<OfferRecycleAdapter.ViewHolder> {
    private List<Offer> offersList;
    private Consumer<Offer> offerClickListener;
    private Context mContext;

    public OfferRecycleAdapter(Context context, List<Offer> offersList, Consumer<Offer> offerClickListener) {
        this.offersList = new ArrayList<>();
        this.offersList.addAll(offersList);
        this.offerClickListener = offerClickListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public OfferRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.offer_list_item, parent, false);
        return new OfferRecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferRecycleAdapter.ViewHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return offersList.size();
    }


    public void addList(List<Offer> incomingOfferHelperList) {
        offersList.clear();
        offersList.addAll(incomingOfferHelperList);
        notifyDataSetChanged();

    }


    private Offer getOffer(int position) {
        return offersList.get(position);
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.offer_details_tv)
        TextView offerDetails;
        @BindView(R.id.date_tv)
        TextView offer_date;
        @BindView(R.id.time_tv)
        TextView offerTime;
        @BindView(R.id.offer_sender_profile_image)
        CircleImageView offerSenderProfileImage;
        @BindView(R.id.offer_hour_price_tv)
        TextView offerHourPrice;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            offerDetails.setText(getOffer(position).getOfferDetails());
            offer_date.setText(getOffer(position).getOfferDate());
            offerTime.setText(getOffer(position).getOfferTime());
            Picasso.with(mContext).load(getOffer(position).getSenderProfileImageURl()).into(offerSenderProfileImage);
            offerHourPrice.setText(getOffer(position).getHourPrice());
        }


        @OnClick
        public void onClick(View v) {
            offerClickListener.accept(getOffer(getAdapterPosition()));
        }
    }

}
