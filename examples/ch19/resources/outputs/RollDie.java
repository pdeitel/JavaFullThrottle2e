import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class RollDie {
   private static void addIn(long[] a, long[] b) {
      IntStream.range(0, a.length)
         .forEach(i -> a[i] += b[i]);
   }

   public static void main(String[] args) {
      final int faces = 6;
      final long rolls = 600_000_000L;
      final int cores =
         Runtime.getRuntime().availableProcessors();

      ForkJoinPool pool = new ForkJoinPool(cores);

      long base = rolls / cores;
      long rem = rolls % cores;

      List<Callable<long[]>> tasks =
         IntStream.range(0, cores)
            .mapToObj(i -> (Callable<long[]>) () -> {
               long chunk = base + (i < rem ? 1 : 0);
               long[] arr = new long[faces];
               ThreadLocalRandom.current()
                  .ints(chunk, 0, faces)
                  .forEach(v -> arr[v]++);
               return arr;
            })
            .toList();

      long[] total;
      try {
         List<Future<long[]>> futs = pool.invokeAll(tasks);
         total = futs.stream()
            .map(f -> {
               try {
                  return f.get();
               } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                  throw new RuntimeException(e);
               } catch (ExecutionException e) {
                  throw new RuntimeException(e.getCause());
               }
            })
            .collect(
               () -> new long[faces],
               (acc, arr) -> addIn(acc, arr),
               (a, b) -> addIn(a, b)
            );
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
         throw new RuntimeException(e);
      } finally {
         pool.shutdown();
      }

      System.out.printf("%4s %9s%n", "Face", "Frequency");
      System.out.printf("%4d %9d%n", 1, total[0]);
      System.out.printf("%4d %9d%n", 2, total[1]);
      System.out.printf("%4d %9d%n", 3, total[2]);
      System.out.printf("%4d %9d%n", 4, total[3]);
      System.out.printf("%4d %9d%n", 5, total[4]);
      System.out.printf("%4d %9d%n", 6, total[5]);
   }
}