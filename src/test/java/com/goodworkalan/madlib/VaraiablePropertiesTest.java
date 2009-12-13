package com.goodworkalan.madlib;

import static org.testng.Assert.assertEquals;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;

import org.testng.annotations.Test;

public class VaraiablePropertiesTest {
    @Test
    public void unescape() {
        Properties properties = new Properties();
        properties.setProperty("n", "\\n");
        properties.setProperty("r", "\\r");
        properties.setProperty("t", "\\t");
        properties.setProperty("f", "\\f");
        properties.setProperty("b", "\\b");
        properties.setProperty("a", "\\a");
        VariableProperties variables = new VariableProperties(properties);
        assertEquals(variables.getProperty("n"), "\n");
        assertEquals(variables.getProperty("r"), "\r");
        assertEquals(variables.getProperty("t"), "\t");
        assertEquals(variables.getProperty("f"), "\f");
        assertEquals(variables.getProperty("b"), "\b");
        assertEquals(variables.getProperty("a"), "a");
    }

    @Test
    public void defaults() {
        Properties properties = new Properties();
        properties.put("a", "a");
        VariableProperties variables = new VariableProperties(properties);
        assertEquals(variables.getProperty("a", "b"), "a");
        assertEquals(variables.getProperty("b", "b"), "b");
    }

    @Test
    public void expandSystem() {
        System.setProperty("testing.prattle.expand", "a");
        Properties properties = new Properties();
        properties.put("a", "<${testing.prattle.expand}>");
        VariableProperties variables = new VariableProperties(properties);
        assertEquals(variables.getProperty("a"), "<a>");
    }

    @Test
    public void expandLocal() {
        Properties properties = new Properties();
        properties.put("a", "<${b}>");
        properties.put("b", "b");
        VariableProperties variables = new VariableProperties(properties);
        assertEquals(variables.getProperty("a"), "<b>");
    }

    @Test
    public void expandNaming() throws NamingException {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, TestInitialContextFactory.class.getName());

        Properties properties = new Properties();
        properties.put("a", "<${naming}>");
        VariableProperties variables = new VariableProperties(properties, true);
        assertEquals(variables.getProperty("a"), "<a>");
    }

    @Test
    public void expandNull() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, TestInitialContextFactory.class.getName());

        Properties properties = new Properties();
        properties.put("a", "<${b}>");
        VariableProperties variables = new VariableProperties(properties, true);
        assertEquals(variables.getProperty("a"), "<>");

        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, BadInitialContextFactory.class.getName());

        assertEquals(variables.getProperty("a"), "<>");
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void infinate() {
        Properties properties = new Properties();
        properties.setProperty("a", "${b}");
        properties.setProperty("b", "${a}");
        VariableProperties variables = new VariableProperties(properties);
        variables.getProperty("a");
    }

    public void endsWithEscape() {
        Properties properties = new Properties();
        properties.put("a", "\\");
        VariableProperties variables = new VariableProperties(properties);
        assertEquals(variables.getProperty("a"), "\\");
    }

    @Test
    public void loneDollarSign() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, BadInitialContextFactory.class.getName());

        Properties properties = new Properties();
        properties.put("a", "$$");
        VariableProperties variables = new VariableProperties(properties, true);
        assertEquals(variables.getProperty("a"), "$$");
    }

    @Test
    public void rightCurly() {
        Properties properties = new Properties();
        properties.setProperty("a", "}");
        VariableProperties variables = new VariableProperties(properties);
        assertEquals(variables.getProperty("a"), "}");
    }
}
