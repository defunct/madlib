package com.goodworkalan.madlib;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class BadInitialContextFactory implements InitialContextFactory {
    public Context getInitialContext(Hashtable<?, ?> environment)
    throws NamingException {
        throw new NamingException();
    }
}
