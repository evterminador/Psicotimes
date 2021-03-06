package com.example.formandocodigo.psicotimes.domain;

import com.example.formandocodigo.psicotimes.data.disk.StateUseDisk;
import com.example.formandocodigo.psicotimes.data.disk.StateUseDiskImpl;
import com.example.formandocodigo.psicotimes.entity.HistoricState;
import com.example.formandocodigo.psicotimes.entity.StateUse;

import java.util.List;

/**
 * Created by FormandoCodigo on 15/12/2017.
 */

public class StateUseCaseImpl implements StateUseCase {

    private StateUseDisk stateUseDisk;

    public StateUseCaseImpl() {
        stateUseDisk = new StateUseDiskImpl();
    }

    @Override
    public List<StateUse> findStateUseByIdAll(Integer id) {
        return stateUseDisk.findStateUseByIdAll(id);
    }

    @Override
    public List<StateUse> getStateUseAll() {
        return stateUseDisk.getStateUseAll();
    }

    @Override
    public List<StateUse> getStateUseByDate() {
        return stateUseDisk.getStateUseByDate();
    }

    @Override
    public List<HistoricState> getHistoricStateAll() {
        return stateUseDisk.getHistoricStateAll();
    }

}
