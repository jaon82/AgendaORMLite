package agenda.cursoandroidavancado.com.br.agendaormlite.core;

import android.app.Application;
import android.util.Log;

import agenda.cursoandroidavancado.com.br.agendaormlite.model.dao.DataBaseManager;

/**
 * Classe que inicializa o contexto da aplicação
 * Created by marciopalheta on 27/12/14.
 */
public class ApplicationContext extends Application {
    private static final String TAG =
            ApplicationContext.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        // Inicializando configuracoes, BD(ORMLite)
        DataBaseManager.init(this);
    }

    @Override
    public void onTerminate() {
        Log.d(TAG, "onTerminate");
        DataBaseManager.close();
        super.onTerminate();
    }
}
