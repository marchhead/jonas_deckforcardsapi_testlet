package me.jonas.jdtestlet.core;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

public class JonasTestletApplication {
    private static ExecutorService service = Executors.newFixedThreadPool(10);

    /***
     *
     * @param testPack, the package which testing code should be in
     * @param concurrencyCount, to show how many the testing code will be executed (multiple threading)
     */
    public static void run(String testPack, int concurrencyCount) {

        Set<Class<? extends JonasTestlet>> allClasses = scanTestPackage(testPack);
        allClasses.stream().forEach(tClz -> {

            Method[] methods = tClz.getMethods();
            Arrays.stream(methods).forEach(m -> {
                if (m.getName().startsWith("test")) {
                    runParallel(m, tClz, concurrencyCount);
                }
            });
        });

        service.shutdown();
    }

    private static Set<Class<? extends JonasTestlet>> scanTestPackage(String testPack) {
        Reflections reflections = new Reflections(testPack);

        Set<Class<? extends JonasTestlet>> allClasses =
                reflections.getSubTypesOf(JonasTestlet.class);
        return allClasses;
    }

    private static void runParallel(Method m, Class<? extends JonasTestlet> jTest, int concurrencyCount) {
        AtomicInteger total = new AtomicInteger();
        AtomicInteger success = new AtomicInteger();
        AtomicInteger fail = new AtomicInteger();
        AtomicInteger exception = new AtomicInteger();
        for (int i = 0; i < concurrencyCount; i++) {
            FutureTask<TestResult> testTask = new FutureTask<>(() -> {
                TestResult result = null;
                try {
                    result = (TestResult) m.invoke(jTest.newInstance(), null);
                    total.getAndIncrement();
                    System.out.println("The " + total.get() + "th test: " + result);
                    if (result.getResult() == ResultConstant.SUCCESS) {
                        success.getAndIncrement();
                    } else if (result.getResult() == ResultConstant.FAIL) {
                        fail.getAndIncrement();
                    } else {
                        exception.getAndIncrement();
                    }
                    if (total.get() == concurrencyCount) {
                        System.err.println("@@@@@@@@@@@@@" + jTest.getName() + " Total:" + total.get() + " Success:" + success.get() + " Fail:" + fail.get() + " Exception:" + exception.get());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return result;
            });
            service.submit(testTask);
        }
    }
}