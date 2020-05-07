package org.dualstudents.job;

import org.springframework.batch.core.listener.ChunkListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;

public class DemonstrationListener extends ChunkListenerSupport {

    @Override
    public void afterChunk(ChunkContext context) {
        System.out.println("Finished Chunk, readCount: " + context.getStepContext().getStepExecution().getReadCount());
    }

    @Override
    public void beforeChunk(ChunkContext context) {
        System.out.println("Starting Chunk, readCount: " + context.getStepContext().getStepExecution().getReadCount());
    }
}
