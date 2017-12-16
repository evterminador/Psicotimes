package com.example.formandocodigo.psicotimes.main.interactor;

import android.app.Activity;

import com.example.formandocodigo.psicotimes.entity.StateUse;
import com.example.formandocodigo.psicotimes.main.net.OrderService;
import com.example.formandocodigo.psicotimes.main.net.entity.AppOrderResponse;
import com.example.formandocodigo.psicotimes.main.net.entity.StateUserOrderResponse;

import java.util.List;

import retrofit2.Call;

/**
 * Created by FormandoCodigo on 04/12/2017.
 */

public interface MainInteractor {
    List<StateUse> findAll();
    void updateApp(Activity activity, OrderService service, Call<AppOrderResponse> call);
    void syncUp(Activity activity, OrderService service, Call<StateUserOrderResponse> call);
}
