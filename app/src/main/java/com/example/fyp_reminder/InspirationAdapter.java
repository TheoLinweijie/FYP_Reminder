package com.example.fyp_reminder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class InspirationAdapter extends RecyclerView.Adapter<InspirationAdapter.InspirationEventViewHolder>
{

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    Context context;
    ArrayList<ReminderEvent> reminderEvents;
    public InspirationAdapter (Context c, ArrayList<ReminderEvent> reminderEventList)
    {
        context = c;
        reminderEvents = reminderEventList;
    }

    public  class InspirationEventViewHolder extends RecyclerView.ViewHolder
    {
        public TextView reminderTitle, reminderDescription, reminderTimeline;

        public InspirationEventViewHolder(@NonNull View itemView)
        {
            super(itemView);

            reminderTitle = (TextView) itemView.findViewById(R.id.event_title);
            reminderDescription = (TextView) itemView.findViewById(R.id.event_description);
            reminderTimeline = (TextView) itemView.findViewById(R.id.event_timeline);
        }
    }

    @NonNull
    @Override
    public InspirationEventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_show_event, viewGroup, false);

        mAuth = FirebaseAuth.getInstance();

        return  new InspirationEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InspirationEventViewHolder holder, final int position)
    {
        String currentUserID =mAuth.getCurrentUser().getUid();
        ReminderEvent reminderEvent = reminderEvents.get(position);

        String reminderTitle = reminderEvent.getTitle();
        String reminderDescription = reminderEvent.getDescription();
        String reminderTimeline = reminderEvent.getTimeline();

        holder.reminderTitle.setText(reminderEvent.getTitle());
        holder.reminderDescription.setText(reminderEvent.getDescription());
        holder.reminderTimeline.setText(reminderEvent.getTimeline());

        final String getTitleText = reminderEvents.get(position).getTitle();
        final String getDescriptionText = reminderEvents.get(position).getDescription();
        final String getTimeline = reminderEvents.get(position).getTimeline();
        final String getId = reminderEvents.get(position).getId();

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent InspirationEventDetailsIntent = new Intent(context, InspirationEventDetailsActivity.class);

                InspirationEventDetailsIntent.putExtra("TitleText", getTitleText);
                InspirationEventDetailsIntent.putExtra("DescriptionText", getDescriptionText);
                InspirationEventDetailsIntent.putExtra("TimelineText", getTimeline);
                InspirationEventDetailsIntent.putExtra("Id", getId);

                context.startActivity(InspirationEventDetailsIntent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return reminderEvents.size();
    }


}
