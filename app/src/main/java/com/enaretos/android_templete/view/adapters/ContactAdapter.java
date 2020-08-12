package com.enaretos.android_templete.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.enaretos.android_templete.R;
import com.enaretos.android_templete.databinding.ItemContactStyleBinding;
import com.enaretos.android_templete.models.ContactModel;
import com.enaretos.android_templete.utils.BindableAdapter;
import com.enaretos.android_templete.utils.IContactAdapterActions;
import com.enaretos.android_templete.utils.ISelectContactActions;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.View.INVISIBLE;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> implements BindableAdapter<List<ContactModel>>, IContactAdapterActions {

    private List<ContactModel> Contacts;

    private ISelectContactActions selectContactActions;

    private ImageView contactImage;

    private ImageView favoriteIcon;


    public ContactAdapter(ISelectContactActions selectContactActions) {
        this.selectContactActions = selectContactActions;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContactStyleBinding itemContactStyleBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_contact_style, parent, false);
        return new ContactHolder(itemContactStyleBinding, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        ContactModel currentContact = Contacts.get(position);
        holder.itemContactStyleBinding.setContact(currentContact);
        contactImage = holder.itemContactStyleBinding.smallProfileImage;
        favoriteIcon = holder.itemContactStyleBinding.favoriteIcon;

        if (currentContact.getSmallImageURL() != null && currentContact.getSmallImageURL() != "") {
            Picasso.get()
                    .load(currentContact.getSmallImageURL())
                    .centerCrop()
                    .fit()
                    .into(contactImage);
        }

        if (currentContact.getIsFavorite()) {
            favoriteIcon.setImageResource(R.mipmap.favorite_true);
        } else {
            favoriteIcon.setVisibility(INVISIBLE);
        }

        if (position == Contacts.size() - 1) {
            holder.itemContactStyleBinding.separator.setVisibility(INVISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return (this.Contacts != null) ? this.Contacts.size() : 0;
    }


    @Override
    public void setData(List<ContactModel> data) {
        this.Contacts = data;
        notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(ContactModel option) {
        selectContactActions.onItemClicked(option);
    }

    class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
        }

        private ItemContactStyleBinding itemContactStyleBinding;
        private IContactAdapterActions ContactAdapterActions;

        public ContactHolder(@NonNull ItemContactStyleBinding itemContactStyleBinding, IContactAdapterActions ContactAdapterActions) {
            super(itemContactStyleBinding.getRoot());
            itemContactStyleBinding.getRoot().setOnClickListener(this);
            this.itemContactStyleBinding = itemContactStyleBinding;
            this.ContactAdapterActions = ContactAdapterActions;
        }

        @Override
        public void onClick(View view) {
            this.ContactAdapterActions.onItemClicked(this.itemContactStyleBinding.getContact());
        }
    }
}
