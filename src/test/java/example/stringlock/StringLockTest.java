package example.stringlock;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.Test;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringLockTest {
  private ForkJoinPool commonPool = new ForkJoinPool(2);

  @Test
  public void testService1SyncMethod() throws ExecutionException, InterruptedException {
    commonPool.submit(() -> {
      Legend legend = new Legend();

      List<Runnable> actions = Lists.newArrayList(
          legend::codingVenus,
          legend::codingSolar
      );

      long duration = runAndReturnElapseTime(actions);
      assertTrue("Should not be finished within 8 secs!", duration >= 8);
    }).get();

  }

  @Test
  public void lambdaParallelTest() throws ExecutionException, InterruptedException {
    new ForkJoinPool(10).submit(() -> {
      Legend legend = new Legend();

      List<Runnable> actions = IntStream.range(0, 10)
          .mapToObj(i -> (Runnable) legend::noSyncMethod)
          .collect(toList());

      long duration = runAndReturnElapseTime(actions);
      assertTrue("Should not be finished within 11 secs!", duration <= 11);
    }).get();
  }

  @Test
  @SneakyThrows
  public void testStringLockSyncMethod() {
    commonPool.submit(() -> {
      Legend legend = new Legend();
      Steve service2 = new Steve();

      List<Runnable> actions = Lists.newArrayList(
          legend::codingVenus,
          service2::codingVenus
      );

      actions.parallelStream().forEach(Runnable::run);
      long duration = runAndReturnElapseTime(actions);

      assertTrue("Should not be finished within 6 secs!", duration >= 6);
    }).get();
  }

  private long runAndReturnElapseTime(Collection<Runnable> actions) {
    Stopwatch stopwatch = Stopwatch.createStarted();
    actions.parallelStream().parallel().forEach(Runnable::run);
    log.info("time elapsed: {}", stopwatch);

    return stopwatch.elapsed(TimeUnit.SECONDS);
  }
}
