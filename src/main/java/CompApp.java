import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


/**
 * @author TemmyTechie
 */

public class CompApp {



        public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

            Logger          log     = LoggerFactory.getLogger(CompApp.class);
            ExecutorService service = Executors.newFixedThreadPool(1);
            long            start   = System.currentTimeMillis();
            log.info("start time: " + start);


            Future<List<Integer>> future = service.submit(() -> {
                log.info("Thread: " + Thread.currentThread().getName());
                List<Integer> primeNumbers = new ArrayList<>();
                for(int i = 0; i < 200; i++)
                {
                    if (isPrime(i)) {
                        primeNumbers.add(i);

                    }
                }
                return primeNumbers;
            });

            service.awaitTermination(1, TimeUnit.MILLISECONDS);
            List<Integer> primes = future.get(1, TimeUnit.SECONDS);

            List<Integer> collect = primes.stream()
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
          log.info(" " + collect);
            service.shutdown();
            long end = System.currentTimeMillis();
            long duration = (end - start);
            log.info("end time: " + duration + " ms");



        }

        private static boolean isPrime(int number) {
            if (number <= 1) {
                return false;
            }
            for (int i = 2; i <= Math.sqrt(number); i++) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }

    }
