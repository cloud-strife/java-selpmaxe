package example.stringlock;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Legend {
  private static final Object SERVICE1_LOCK = "";

  void codingVenus() {
    synchronized (SERVICE1_LOCK) {
      log.info("codingVenus start");
      sleepUninterruptibly(5, TimeUnit.SECONDS);
      log.info("codingVenus end");
    }
  }

  void codingSolar() {
    synchronized (SERVICE1_LOCK) {
      log.info("codingSolar start");
      sleepUninterruptibly(3, TimeUnit.SECONDS);
      log.info("codingSolar end");
    }
  }

  void noSyncMethod() {
    log.info("noSyncMethod start");
    sleepUninterruptibly(10, TimeUnit.SECONDS);
    log.info("noSyncMethod end");
  }
}
