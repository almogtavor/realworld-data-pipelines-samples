package flink.playground.async.sink.sink2;


import flink.playground.async.sink.ProcessingTimeService;
import org.apache.flink.annotation.Experimental;
import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.api.common.operators.MailboxExecutor;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.metrics.groups.SinkWriterMetricGroup;
import org.apache.flink.util.UserCodeClassLoader;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.function.Consumer;

/**
 * Base interface for developing a sink. A basic {@link Sink} is a stateless sink that can flush
 * data on checkpoint to achieve at-least-once consistency. Sinks with additional requirements
 * should implement {@link StatefulSink} or TwoPhaseCommittingSink.
 *
 * <p>The {@link Sink} needs to be serializable. All configuration should be validated eagerly. The
 * respective sink writers are transient and will only be created in the subtasks on the
 * taskmanagers.
 *
 * @param <InputT> The type of the sink's input
 */
@PublicEvolving
public interface Sink<InputT> extends Serializable {

    /**
     * Creates a {@link SinkWriter}.
     *
     * @param context the runtime context.
     * @return A sink writer.
     * @throws IOException for any failure during creation.
     */
    SinkWriter<InputT> createWriter(InitContext context) throws IOException;

    /** The interface exposes some runtime info for creating a {@link SinkWriter}. */
    @PublicEvolving
    interface InitContext {
        /**
         * The first checkpoint id when an application is started and not recovered from a
         * previously taken checkpoint or savepoint.
         */
        long INITIAL_CHECKPOINT_ID = 1;

        /**
         * Gets the {@link UserCodeClassLoader} to load classes that are not in system's classpath,
         * but are part of the jar file of a user job.
         *
         * @see UserCodeClassLoader
         */
        UserCodeClassLoader getUserCodeClassLoader();

        /**
         * Returns the mailbox executor that allows to execute {@link Runnable}s inside the task
         * thread in between record processing.
         *
         * <p>Note that this method should not be used per-record for performance reasons in the
         * same way as records should not be sent to the external system individually. Rather,
         * implementers are expected to batch records and only enqueue a single {@link Runnable} per
         * batch to handle the result.
         */
        MailboxExecutor getMailboxExecutor();

        /**
         * Returns a {@link ProcessingTimeService} that can be used to get the current time and
         * register timers.
         */
        ProcessingTimeService getProcessingTimeService();

        /** @return The id of task where the writer is. */
        int getSubtaskId();

        /** @return The number of parallel Sink tasks. */
        int getNumberOfParallelSubtasks();

        /** @return The metric group this writer belongs to. */
        SinkWriterMetricGroup metricGroup();

        /**
         * Returns id of the restored checkpoint, if state was restored from the snapshot of a
         * previous execution.
         */
        OptionalLong getRestoredCheckpointId();

        /**
         * Provides a view on this context as a {@link SerializationSchema.InitializationContext}.
         */
        SerializationSchema.InitializationContext asSerializationSchemaInitializationContext();

        /**
         * Returns a metadata consumer, the {@link SinkWriter} can publish metadata events of type
         * {@link MetaT} to the consumer.
         *
         * <p>It is recommended to use a separate thread pool to publish the metadata because
         * enqueuing a lot of these messages in the mailbox may lead to a performance decrease.
         * thread, and the {@link Consumer#accept} method is executed very fast.
         */
        @Experimental
        default <MetaT> Optional<Consumer<MetaT>> metadataConsumer() {
            return Optional.empty();
        }
    }
}