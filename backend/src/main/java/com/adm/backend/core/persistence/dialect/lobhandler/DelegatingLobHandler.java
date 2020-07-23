package com.adm.backend.core.persistence.dialect.lobhandler;

import com.adm.backend.core.persistence.dialect.SQLDialect;

import org.springframework.jdbc.support.lob.LobHandler;

public interface DelegatingLobHandler extends LobHandler {
    public SQLDialect getDialect();
}