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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


    private Offer getRequestHelper(int position) {
        return offersList.get(position);
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.offer_details_tv)
        TextView offerTitle;
//        @BindView(R.id.request_details_textView)
//        TextView requestDetails;
//        @BindView(R.id.who_is_the_help_for_textView)
//        TextView whoIsTheHelpFor;
//        @BindView(R.id.what_you_need_help_with_textView)
//        TextView whatYouNeedHelpWith;
//        @BindView(R.id.relevant_tags_textView)
//        TextView relevantTags;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            offerTitle.setText(getRequestHelper(position).getOfferDetails());
//            requestDetails.setText(getRequestHelper(position).getRequestDetails());
//            whoIsTheHelpFor.setText(getRequestHelper(position).getWhoIsTheHelpFor());
//            whatYouNeedHelpWith.setText(getRequestHelper(position).getWhatYouNeedHelpWith());
//            relevantTags.setText(getRequestHelper(position).getRelevantTags());
        }


        @OnClick
        public void onClick(View v) {
            offerClickListener.accept(getRequestHelper(getAdapterPosition()));
        }
    }

}
