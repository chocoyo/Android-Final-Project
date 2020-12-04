package com.mhodges.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class ItemFragment extends Fragment {
    private WebView browser;
    private Item item;

    public ItemFragment(Item item) {
        this.item = item;
    }

    public static ItemFragment newInstance(Item item) {
        return new ItemFragment(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        browser = view.findViewById(R.id.browser);
        browser.loadUrl("http://google.com");


        return view;
    }
}