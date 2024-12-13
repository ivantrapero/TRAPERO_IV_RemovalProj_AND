package com.trapero.removalproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.trapero.removalproject.model.Item;

import java.util.List;

public class ItemViewModel extends ViewModel {
    private final ItemRepository repository = new ItemRepository();
    private final MutableLiveData<List<Item>> itemsLiveData = new MutableLiveData<>();

    public LiveData<List<Item>> getItems() {
        return itemsLiveData;
    }

    public void loadItems() {
        itemsLiveData.setValue(repository.getItems());
    }

    public void addItem(Item item) {
        repository.addItem(item, new ItemRepository.AddItemCallback() {
            @Override
            public void onSuccess(Item newItem) {
                // Handle success (e.g., update LiveData)
                loadItems(); // Refresh the list
            }

            @Override
            public void onError(String message) {
                // Handle error
            }
        });
    }

    public void updateItem(String id, String newName) {
        repository.updateItem(id, newName, new ItemRepository.UpdateItemCallback() {
            @Override
            public void onSuccess() {
                loadItems();

            }

            @Override
            public void onError(String message) {

            }
        });

    }

    public void deleteItem(String id) {

        repository.deleteItem(id, new ItemRepository.DeleteItemCallback() {
            @Override
            public void onSuccess() {
                loadItems();

            }

            @Override
            public void onError(String message) {

            }
        });
    }
}