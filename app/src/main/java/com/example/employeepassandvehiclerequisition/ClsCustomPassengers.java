package com.example.employeepassandvehiclerequisition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClsCustomPassengers extends BaseAdapter {

    Context context;

    ClsDTPassengers clsDTPassengers;

    private static LayoutInflater inflater = null;

    public ClsCustomPassengers(Context context, ClsDTPassengers clsDTPassengers) {
        this.context = context;

        this.clsDTPassengers = clsDTPassengers;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return (clsDTPassengers.getPassengersArrayList() == null) ? 0 : clsDTPassengers.getPassengersArrayList().size();
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
        TextView t_RowNumPassengerOut;
        TextView t_PassengerName;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ClsCustomPassengers.Holder holder = new ClsCustomPassengers.Holder();

        View rowView = inflater.inflate(R.layout.custom_passengers, null);

        holder.t_RowNumPassengerOut = rowView.findViewById(R.id.t_RowNumPassengerOut);

        holder.t_RowNumPassengerOut.setText(clsDTPassengers.getPassengersArrayList().get(i).getRowNum());

        holder.t_PassengerName = rowView.findViewById(R.id.t_PassengerName);

        holder.t_PassengerName.setText(clsDTPassengers.getPassengersArrayList().get(i).getPassengerName());

        rowView.setTag(holder.t_PassengerName.getText().toString());

        return  rowView;
    }
}
