package com.anas.superhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Abdo Amin on 1/26/2018.
 */

public class RequestRecycleAdapter extends RecyclerView.Adapter<RequestRecycleAdapter.ViewHolder> {

//    private List<Medicine> medicineList;
    private MedicineClickListener mMedicineClickListener;
    private Context mContext;

    public RequestRecycleAdapter(Context context,
//                                 List<Medicine> medicines,
                                 MedicineClickListener medicineClickListener) {
//        this.medicineList = new ArrayList<>();
//        medicineList.addAll(medicines);
        mMedicineClickListener = medicineClickListener;
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
//        return medicineList.size();
        return 0;
    }


    public void addList(
//            List<Medicine> medicineListIncoming
    ) {
//        medicineList.clear();
//        medicineList.addAll(medicineListIncoming);
        notifyDataSetChanged();

    }


//    private Medicine getMedicine(int position) {
////        return medicineList.get(position);
//    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.name_textView)
        TextView medicineName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position) {
//            medicineName.setText(getMedicine(position).getName());
        }


        @OnClick
        public void onClick(View v) {
//            mMedicineClickListener.onMedicineClick(getMedicine(getAdapterPosition()));
        }
    }

    public interface MedicineClickListener {
//        void onMedicineClick(Medicine medicine);
    }
}
