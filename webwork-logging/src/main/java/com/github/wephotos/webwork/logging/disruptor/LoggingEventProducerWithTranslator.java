package com.github.wephotos.webwork.logging.disruptor;

import com.github.wephotos.webwork.logging.WebworkLoggingEvent;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Publishing Using Translators
 *
 * @author TQ
 */
public class LoggingEventProducerWithTranslator {

    private final RingBuffer<WebworkLoggingEvent> ringBuffer;

    private static LoggingEventProducerWithTranslator producer;

    private static AtomicBoolean ATOMIC_BOOLEAN = new AtomicBoolean();

    public LoggingEventProducerWithTranslator(RingBuffer<WebworkLoggingEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<WebworkLoggingEvent, WebworkLoggingEvent> TRANSLATOR = new EventTranslatorOneArg<WebworkLoggingEvent, WebworkLoggingEvent>() {
        public void translateTo(WebworkLoggingEvent event, long sequence, WebworkLoggingEvent source) {
            event.setArgumentArray(source.getArgumentArray());
            event.setLevel(source.getLevel());
            event.setLogger(source.getLogger());
            event.setLoggerName(source.getLoggerName());
            event.setMarker(source.getMarker());
            event.setMessage(source.getMessage());
            event.setThreadName(source.getThreadName());
            event.setThrowable(source.getThrowable());
            event.setTimeStamp(source.getTimeStamp());
        }
    };

    public void onData(WebworkLoggingEvent event) {
        ringBuffer.publishEvent(TRANSLATOR, event);
    }

    /**
     * 获取生产者
     *
     * @return
     */
    public static LoggingEventProducerWithTranslator getProducer() {
        if (!ATOMIC_BOOLEAN.compareAndSet(false, true)) {
            return producer;
        }
        System.err.println("init disruptor...");
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<WebworkLoggingEvent> disruptor = new Disruptor<>(WebworkLoggingEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        // Connect the handler
        disruptor.handleEventsWith(new LoggingEventHandler());

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<WebworkLoggingEvent> ringBuffer = disruptor.getRingBuffer();

        producer = new LoggingEventProducerWithTranslator(ringBuffer);

        return producer;
    }
}
