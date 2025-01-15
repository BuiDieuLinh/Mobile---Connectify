package com.example.btl_mobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    ImageButton ibtnNotify, ibtnMessage, ibtnTym, ibtnComment, ibtnShare;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.indexconnecttify, container, false);

        ibtnNotify = view.findViewById(R.id.ibtnNotify);
        ibtnMessage = view.findViewById(R.id.ibtnMessage);
        ibtnTym = view.findViewById(R.id.ibtnTym);
        ibtnComment = view.findViewById(R.id.ibtnComment);
        ibtnShare = view.findViewById(R.id.ibtnShare);

        ibtnNotify.setOnClickListener(v -> {
            Intent it = new Intent(getActivity(), actNotification.class);
            startActivity(it);
        });
        ibtnMessage.setOnClickListener(v -> {
            Intent it = new Intent(getActivity(), actMessage.class);
            startActivity(it);
        });
        ibtnTym.setOnClickListener(v -> {
            // Kiểm tra nếu hiện tại là hình ảnh "heart"
            if (((ImageButton) v).getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.heart).getConstantState())) {
                // Nếu là "heart", thay đổi thành "tym"
                ibtnTym.setImageResource(R.drawable.heart__2_);
            } else {
                // Nếu không phải "heart", thay đổi thành "heart"
                ibtnTym.setImageResource(R.drawable.heart);
            }
        });
        return  view;
    }
}