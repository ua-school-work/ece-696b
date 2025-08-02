package edu.arizona.josesosa.structural.proxy;

import edu.arizona.josesosa.structural.proxy.exception.ForbiddenException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.StringJoiner;

public class ProxyClass implements InvocationHandler {

    private final Object delegate;

    public ProxyClass(Object delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = 0L;
        try {
            ensureNoForbiddenArguments(args);
            logMethodInvocation(method, args);
            startTime = System.currentTimeMillis();
            return method.invoke(delegate, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            logMethodFailure(method, args);
            throw e;
        } finally {
            logMethodCompletion(method, args, startTime);
        }
    }

    private void ensureNoForbiddenArguments(Object[] args) {
        if (args == null) {
            return;
        }
        for (Object arg : args) {
            if (isArgumentForbidden(arg)) {
                throw new ForbiddenException("Censored");
            }
        }
    }

    private boolean isArgumentForbidden(Object arg) {
        return arg instanceof String && ((String) arg).toLowerCase().contains("secret");
    }

    private void logMethodInvocation(Method method, Object[] args) {
        System.out.println(this.getClass().getSimpleName() + ": #Calling " + buildMethodSignatureString(method, args));
    }

    private void logMethodFailure(Method method, Object[] args) {
        System.out.println("#Proxy method for " + buildMethodSignatureString(method, args) + " ends with Exception");
    }

    private void logMethodCompletion(Method method, Object[] args, long startTime) {
        long executionTime = (startTime == 0L) ? 0 : System.currentTimeMillis() - startTime;
        System.out.println(this.getClass().getSimpleName() + ": #Call " + buildMethodSignatureString(method, args)
                + " over. It took (" + executionTime + " ms)");
    }

    private String buildMethodSignatureString(Method method, Object[] args) {
        return delegate.getClass().getSimpleName() + "#" + method.getName() + "(" + buildArgumentTypesString(args) + ")";
    }

    private String buildArgumentTypesString(Object[] args) {
        if (args == null || args.length == 0) {
            return "";
        }
        StringJoiner joiner = new StringJoiner(",");
        for (Object arg : args) {
            joiner.add(arg.getClass().getSimpleName());
        }
        return joiner.toString();
    }
}