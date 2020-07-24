package com.adm.backend.core.persistence.dialect.lobhandler;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.adm.backend.core.persistence.dialect.AltibaseDialect;
import com.adm.backend.core.persistence.dialect.SQLDialect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;

public class AltibaseLobHandler extends DefaultLobHandler implements DelegatingLobHandler {
    private static final Logger log = LoggerFactory.getLogger(AltibaseLobHandler.class);

    @Override
    public SQLDialect getDialect() {
        return new AltibaseDialect();
    }

    public String getClobAsString(ResultSet rs, int columnIndex) throws SQLException {
        Clob clob = rs.getClob(columnIndex);
        return this.getClobToString(clob);
    }

    protected String getClobToString(Clob clob) throws SQLException {
        char[] buff = new char[2048];
        Reader reader = clob.getCharacterStream();
        StringBuffer sb = new StringBuffer();
        try {
            int readSize;
            while ((readSize = reader.read(buff)) > 0) {
                sb.append(buff, 0, readSize);
            }
        }
        catch (IOException e) {
            log.error("io exception {}", (Object)e.getMessage(), (Object)e);
        }
        log.info(sb.toString());
        return sb.toString();
    }

    public LobCreator getLobCreator() {
        return new AltibaseLobCreator();
    }

    protected class AltibaseLobCreator
    extends DefaultLobHandler.DefaultLobCreator {
        protected AltibaseLobCreator() {
            super();
        }

        public void setClobAsString(PreparedStatement ps, int paramIndex, String content) throws SQLException {
            if (content != null) {
                ps.setCharacterStream(paramIndex, (Reader)new StringReader(content), content.length());
            } else {
                ps.setString(paramIndex, null);
            }
        }
    }
}