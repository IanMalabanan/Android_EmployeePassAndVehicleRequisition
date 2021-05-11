package com.example.employeepassandvehiclerequisition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClsCustomDeliveryRequests extends BaseAdapter {

    Context context;

    ClsDTRequests clsDTRequests;

    private static LayoutInflater inflater = null;

    public ClsCustomDeliveryRequests(Context context, ClsDTRequests clsDTRequests) {
        this.context = context;

        this.clsDTRequests = clsDTRequests;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return (clsDTRequests.getDeliveryRequestsList() == null) ? 0 : clsDTRequests.getDeliveryRequestsList().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView t_DeliveryrowNum;
        TextView t_DeliveryDestination;
        TextView t_rowDeliveryID;
        TextView t_DeliveryOBDate;
        TextView t_DeliveryDriver;
        TextView t_DeliveryCrew1;
        TextView t_DeliveryCrew2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ClsCustomDeliveryRequests.Holder holder = new ClsCustomDeliveryRequests.Holder();

        View rowView = inflater.inflate(R.layout.custom_deliveryrequests, null);

        holder.t_DeliveryrowNum = rowView.findViewById(R.id.t_rowNum);

        holder.t_DeliveryrowNum.setText(clsDTRequests.getDeliveryRequestsList().get(i).getRowNum());

        holder.t_DeliveryDestination = rowView.findViewById(R.id.t_DeliveryDestination);

        holder.t_DeliveryDestination.setText(clsDTRequests.getDeliveryRequestsList().get(i).getDestination());

        holder.t_DeliveryOBDate = rowView.findViewById(R.id.t_DeliveryOBDate);

        holder.t_DeliveryOBDate.setText(clsDTRequests.getDeliveryRequestsList().get(i).getoBDate());

        holder.t_DeliveryDriver = rowView.findViewById(R.id.t_DeliveryDriver);

        holder.t_DeliveryDriver.setText(clsDTRequests.getDeliveryRequestsList().get(i).getDriver());

        holder.t_DeliveryCrew1 = rowView.findViewById(R.id.t_DeliveryCrew1);

        holder.t_DeliveryCrew1.setText(clsDTRequests.getDeliveryRequestsList().get(i).getCrew1());

        holder.t_DeliveryCrew2 = rowView.findViewById(R.id.t_DeliveryCrew2);

        holder.t_DeliveryCrew2.setText(clsDTRequests.getDeliveryRequestsList().get(i).getCrew2());

        holder.t_rowDeliveryID = rowView.findViewById(R.id.t_rowDeliveryID);

        holder.t_rowDeliveryID.setText(clsDTRequests.getDeliveryRequestsList().get(i).getRequestid());

        rowView.setTag(holder.t_rowDeliveryID.getText().toString());

        return rowView;
    }
}
