package com.example.employeepassandvehiclerequisition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClsCustomRequests extends BaseAdapter {

    Context context;

    ClsDTRequests clsDTRequests;

    private static LayoutInflater inflater = null;

    public ClsCustomRequests(Context context, ClsDTRequests clsDTRequests) {
        this.context = context;

        this.clsDTRequests = clsDTRequests;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return (clsDTRequests.getRequestsList() == null) ? 0 : clsDTRequests.getRequestsList().size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder {
        TextView t_rowNum;
        TextView t_ControlNo;
        TextView t_OBDate;
        TextView t_Reason;
        TextView t_RequestedBy;
        TextView t_Driver;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ClsCustomRequests.Holder holder = new ClsCustomRequests.Holder();

        View rowView = inflater.inflate(R.layout.custom_requests, null);

        holder.t_rowNum = rowView.findViewById(R.id.t_rowNum);

        holder.t_rowNum.setText(clsDTRequests.getRequestsList().get(i).getRowNum());

        holder.t_ControlNo = rowView.findViewById(R.id.t_ControlNo);

        holder.t_ControlNo.setText(clsDTRequests.getRequestsList().get(i).getControlNo());

        holder.t_OBDate = rowView.findViewById(R.id.t_OBDate);

        holder.t_OBDate.setText(clsDTRequests.getRequestsList().get(i).getoBDate());

        holder.t_Reason = rowView.findViewById(R.id.t_Reason);

        holder.t_Reason.setText(clsDTRequests.getRequestsList().get(i).getReason());

        holder.t_RequestedBy = rowView.findViewById(R.id.t_RequestedBy);

        holder.t_RequestedBy.setText(clsDTRequests.getRequestsList().get(i).getRequestedBy());

        holder.t_Driver = rowView.findViewById(R.id.t_Driver);

        holder.t_Driver.setText(clsDTRequests.getRequestsList().get(i).getDriver());

        rowView.setTag(holder.t_ControlNo.getText().toString());

        return rowView;
    }
}
