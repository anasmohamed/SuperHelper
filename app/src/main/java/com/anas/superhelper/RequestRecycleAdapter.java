package com.anas.superhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anas.superhelper.auth.models.RequestHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Abdo Amin on 1/26/2018.
 */

public class RequestRecycleAdapter extends RecyclerView.Adapter<RequestRecycleAdapter.ViewHolder> {

    private List<RequestHelper> requestHelperList;
    private  Consumer<RequestHelper>  requestHelperClickListener;
    private Context mContext;

    public RequestRecycleAdapter(Context context, List<RequestHelper> requestHelperList, Consumer<RequestHelper> requestHelperClickListener) {
        this.requestHelperList = new ArrayList<>();
        this.requestHelperList.addAll(requestHelperList);
        this.requestHelperClickListener = requestHelperClickListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return requestHelperList.size();
    }


    public void addList(List<RequestHelper> incomingRequestHelperList) {
        requestHelperList.clear();
        requestHelperList.addAll(incomingRequestHelperList);
        notifyDataSetChanged();

    }


    private RequestHelper getRequestHelper(int position) {
        return requestHelperList.get(position);
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.request_title_textView)
        TextView requestTitle;
        @BindView(R.id.request_details_textView)
        TextView requestDetails;
        @BindView(R.id.who_is_the_help_for_textView)
        TextView whoIsTheHelpFor;
        @BindView(R.id.what_you_need_help_with_textView)
        TextView whatYouNeedHelpWith;
        @BindView(R.id.relevant_tags_textView)
        TextView relevantTags;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
            requestTitle.setText(getRequestHelper(position).getRequestTitle());
            requestDetails.setText(getRequestHelper(position).getRequestDetails());
            whoIsTheHelpFor.setText(getRequestHelper(position).getWhoIsTheHelpFor());
            whatYouNeedHelpWith.setText(getRequestHelper(position).getWhatYouNeedHelpWith());
            relevantTags.setText(getRequestHelper(position).getRelevantTags());
        }


        @OnClick
        public void onClick(View v) {
            getRequestHelper(getAdapterPosition()).setUid(getAdapterPosition());
            requestHelperClickListener.accept(getRequestHelper(getAdapterPosition()));
        }
    }

}
