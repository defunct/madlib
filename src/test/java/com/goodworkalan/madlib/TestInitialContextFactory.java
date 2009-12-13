package com.goodworkalan.madlib;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class TestInitialContextFactory implements InitialContextFactory {
    public Context getInitialContext(Hashtable<?, ?> environment)
    throws NamingException {
        InitialContext ic = mock(InitialContext.class);
        when(ic.lookup("naming")).thenReturn("a");
        return ic;
    }
}
