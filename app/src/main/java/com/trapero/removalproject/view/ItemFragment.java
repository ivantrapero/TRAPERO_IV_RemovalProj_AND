package com.trapero.removalproject.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.trapero.removalproject.R;
import com.trapero.removalproject.model.Item;
import com.trapero.removalproject.viewmodel.ItemViewModel;

import java.util.ArrayList;

public class ItemFragment extends Fragment {
    private ItemViewModel viewModel;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        setupToolbar(view);
        listView = view.findViewById(R.id.listView);

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        viewModel.getItems().observe(getViewLifecycleOwner(), items -> {
            ArrayList<String> itemNames = new ArrayList<>();
            for (Item item : items) {
                itemNames.add(item.getName());
            }
            adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, itemNames);
            listView.setAdapter(adapter);
        });

        listView.setOnItemClickListener(this::onItemClicked);
        listView.setOnItemLongClickListener(this::onItemLongClicked);

        viewModel.loadItems();
        return view;
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Items");
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(this::onMenuItemClicked);
    }

    private boolean onMenuItemClicked(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_add) {
            showAddItemDialog();
            return true;
        }
        return false;
    }

    private void showAddItemDialog() {
        EditText input = new EditText(requireContext());
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Add Item")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name = input.getText().toString().trim();
                    if (!name.isEmpty()) {
                        viewModel.addItem(new Item(String.valueOf(System.currentTimeMillis()), name));
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void onItemClicked(AdapterView<?> parent, View view, int position, long id) {
        EditText input = new EditText(requireContext());
        String selectedItem = (String) parent.getItemAtPosition(position);
        input.setText(selectedItem);

        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Edit Item")
                .setView(input)
                .setPositiveButton("Update", (dialog, which) -> {
                    String updatedName = input.getText().toString().trim();
                    if (!updatedName.isEmpty()) {
                        Item item = viewModel.getItems().getValue().get(position);
                        viewModel.updateItem(item.getId(), updatedName);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private boolean onItemLongClicked(AdapterView<?> parent, View view, int position, long id) {
        Item item = viewModel.getItems().getValue().get(position);
        viewModel.deleteItem(item.getId());
        Toast.makeText(requireContext(), "Item Deleted", Toast.LENGTH_SHORT).show();
        return true;
    }
}
