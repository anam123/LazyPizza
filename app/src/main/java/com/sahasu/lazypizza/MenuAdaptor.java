package com.sahasu.lazypizza;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MenuAdaptor extends RecyclerView.Adapter<MenuAdaptor.MyHolder>{

    private List<MenuInfo> menuData;
    private LayoutInflater inflater;

    public MenuAdaptor(List<MenuInfo> menuData, Context c)
    {
        this.inflater = LayoutInflater.from(c);
        this.menuData=menuData;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.menuitem,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        MenuInfo menu= menuData.get(position);
        holder.title.setText(menu.getOrder_name());
//        holder.icon.setImageResource(menu.getImageResId());
    }

    @Override
    public int getItemCount() {
        return menuData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private LinearLayout icon;
        private TextView cost;
        private TextView placeOrder;

        public MyHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.menuOrderName);
            icon = (LinearLayout) itemView.findViewById(R.id.menuIcon);
            cost = (TextView) itemView.findViewById(R.id.menuCost);
            placeOrder = (TextView) itemView.findViewById(R.id.menuPlaceOrder);

            placeOrder.setOnClickListener(this);

        }

        @Override
        public void onClick(final View v) {

            Toast.makeText(itemView.getContext(), "Clicked button at position : " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            final Intent intent = new Intent(itemView.getContext(), MarketPlaceOrder.class);

            final AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
            alert.setTitle("Enter Location");

            final TextView input = new TextView (itemView.getContext());
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String loc=input.getText().toString();
                    intent.putExtra("address",loc);
//                    MenuInfo info;
//                    info = menuData.get(getAdapterPosition());
//                    intent.putExtra("orderName", info.getOrder_name());
//                    intent.putExtra("cost", info.getCost());
//                    v.getContext().startActivity(intent);
                    final AlertDialog.Builder alert2 = new AlertDialog.Builder(itemView.getContext());
                    alert2.setTitle("Enter Super Coins");

                    final TextView input2 = new TextView (itemView.getContext());
                    input2.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                    alert2.setView(input2);

                    alert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String sc=input2.getText().toString();
                            MenuInfo info;
                            info = menuData.get(getAdapterPosition());
                            intent.putExtra("orderName", info.getOrder_name());
                            intent.putExtra("cost", info.getCost()+" +  "+sc);
                            v.getContext().startActivity(intent);

                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });

                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });
            alert.show();
        }
    }

}
