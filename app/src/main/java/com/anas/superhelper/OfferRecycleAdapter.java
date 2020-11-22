package com.anas.superhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.superhelper.auth.models.Offer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class OfferRecycleAdapter extends RecyclerView.Adapter<OfferRecycleAdapter.ViewHolder> {
    private final List<Offer> offersList;
    private final Consumer<Offer> offerClickListener;
    private final Context mContext;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
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

        @BindView(R.id.offer_status_value_tv)
        TextView offerStatus;
        @BindView(R.id.accept_offer_btn)
        Button acceptOfferBtn;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            offerDetails.setText(getOffer(position).getOfferDetails());
            offer_date.setText(getOffer(position).getOfferDate());
            offerTime.setText(getOffer(position).getOfferTime());
            Picasso.with(mContext).load(getOffer(position).getSenderProfileImageURl()).into(offerSenderProfileImage);
            offerHourPrice.setText(getOffer(position).getHourPrice() + " EGP");
            offerStatus.setText(getOffer(position).getStatus());
            if (getOffer(position).getStatus().equalsIgnoreCase("closed")||getOffer(position).getStatus().equalsIgnoreCase("accept")) {
                acceptOfferBtn.setVisibility(View.GONE);
            }
            if(getOffer(position).getSender().equalsIgnoreCase(firebaseUser.getUid()))
            {
                acceptOfferBtn.setVisibility(View.GONE);
            }
        }

        @OnClick({R.id.accept_offer_btn})
        void onAcceptOfferBtnClick() {
            getOffer(getAdapterPosition()).setOfferNumberInTheList(getAdapterPosition());
            offerClickListener.accept(getOffer(getAdapterPosition()));
        }

        @OnClick
        public void onClick(View v) {

        }
    }

}
