package com.trapero.removalproject.viewmodel;

import com.trapero.removalproject.model.Item;
import com.trapero.removalproject.network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ItemRepository {

    private final ApiInterface itemApi = (ApiInterface) RetrofitClient.getInstance();

    public void addItem(Item item, final AddItemCallback callback) {
        Call<Item> call = itemApi.createItem(item);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void updateItem(String id, String newName, final UpdateItemCallback callback) {
        Item updatedItem = new Item(id, newName);
        Call<Item> call = itemApi.updateItem(id, updatedItem);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void deleteItem(String id, final DeleteItemCallback callback) {
        Call<Void> call = itemApi.deleteItem(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getItems(final GetItemsCallback callback) {
        Call<List<Item>> call = itemApi.getItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface AddItemCallback {
        void onSuccess(Item newItem);
        void onError(String message);
    }

    public interface UpdateItemCallback {
        void onSuccess();
        void onError(String message);
    }

    public interface DeleteItemCallback {
        void onSuccess();
        void onError(String message);
    }

    public interface GetItemsCallback {
        void onSuccess(List<Item> items);
        void onError(String message);
    }
}
