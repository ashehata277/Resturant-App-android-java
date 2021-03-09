package com.example.recently;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context mContext;
    private ArrayList<offeritem> content = new ArrayList<offeritem>();

    public RecycleViewAdapter(Context mContext, ArrayList content) {
        this.mContext = mContext;
        this.content = content;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder=null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType)
        {
            case 0 :
            {
                View view0 = inflater.inflate(R.layout.offeritem,parent,false);
                holder = new viewHolder(view0);
                break;
            }
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        viewHolder holder0;
        switch (holder.getItemViewType())
        {
            case 0:
            {
                 holder0 = (viewHolder) holder;
                Picasso.get().load(content.get(position).getLink()).into(holder0.img);
                holder0.text.setText(content.get(position).getName().toString());
                break;
            }
            default:
                holder0 = (viewHolder) holder;
        }
        holder0.img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //add item to Card with Card.addtoCard method.
                Card.addtoCard(new CardItem(content.get(position).getName(),
                        content.get(position).getDescription(),
                        content.get(position).getPrice(),(long) 1,"None",
                        content.get(position).getPrice()));
                Toast.makeText(RecycleViewAdapter.this.mContext,"Added to Cart",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return content.get(position).getType();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    protected class viewHolder extends RecyclerView.ViewHolder
    {
        private ImageView img;
        private TextView text;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            img= itemView.findViewById(R.id.offerimage);
            text = itemView.findViewById(R.id.offertext);
        }
    }
}
