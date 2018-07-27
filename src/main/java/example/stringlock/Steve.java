package example.stringlock;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class Steve {
  private static final Object SERVICE2_LOCK = "";

  void codingVenus() {
    synchronized (SERVICE2_LOCK) {
      log.info("codingVenus start");
      sleepUninterruptibly(4, TimeUnit.SECONDS);
      log.info("codingVenus end");
    }
  }
}
