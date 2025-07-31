import edu.arizona.josesosa.creational.singleton.SaleCounter;
import edu.arizona.josesosa.creational.singleton.Service;
import edu.arizona.josesosa.creational.singleton.impl.ServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UserTest {

    private static final int USERS = 10;

    public static class User implements Runnable {

        @Override
        public void run() {
            Service service = ServiceImpl.getInstance();
            SaleCounter counter = SaleCounter.getInstance();
            Double amount = 100.0;
            amount = service.taxCalculation(amount);
            service.registerSale(counter.getNext(), amount);
        }

    }

    @Test
    public void testOne() throws InterruptedException {
        User u = new User();
        u.run();

        Service service = ServiceImpl.getInstance();
        System.out.println(service.dailyReport(service.now()));

    }

    @Test
    public void testMany() throws InterruptedException {
        for (int i = 1; i < USERS + 1; i++) {
            Thread t = new Thread(new User());
            t.start();
        }

        Thread.sleep(USERS * 1000);

        Service service = ServiceImpl.getInstance(); // this is so ugly
        System.out.println(service.dailyReport(service.now()));

    }

    @Test
    void testSingletonInstance() {
        // Arrange: Get two instances from the SaleCounter
        SaleCounter instance1 = SaleCounter.getInstance();
        SaleCounter instance2 = SaleCounter.getInstance();

        // Assert: The two instances should be the exact same object
        Assertions.assertSame(instance1, instance2, "getInstance() should always return the same instance.");
    }

    @Test
    void testCounterIsThreadSafe() throws InterruptedException {
        // Arrange:
        final int numberOfThreads = 100;
        final int idsPerThread = 1000;
        final int expectedTotalIds = numberOfThreads * idsPerThread;

        // A thread-safe Set to store the generated IDs.
        // If the counter is not thread-safe, duplicates will be generated,
        // and the final size of the set will be less than expectedTotalIds.
        final Set<Integer> generatedIds = Collections.synchronizedSet(new HashSet<>());

        // An ExecutorService to manage our pool of threads
        final ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        // Act:
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < idsPerThread; j++) {
                    generatedIds.add(SaleCounter.getInstance().getNext());
                }
            });
        }

        // Wait for all threads to complete
        executorService.shutdown();
        boolean finished = executorService.awaitTermination(1, TimeUnit.MINUTES);

        // Assert:
        Assertions.assertTrue(finished, "Executor service did not terminate in the allotted time.");
        Assertions.assertEquals(expectedTotalIds, generatedIds.size(), "SaleCounter must be thread-safe and generate unique IDs across all threads.");
    }


}
