package org.dualstudents.job;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(ItemReader<Object> reader, ItemWriter<Object> writer, ItemProcessor<Object, Object> processor, ChunkListener listener) {
        int chunkSize = Integer.parseInt(System.getProperty("chunkSize"));
        return stepBuilderFactory.get("step1")
                .<Object, Object>chunk(chunkSize)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(listener)
                .build();
    }

    @Bean
    public ItemReader<Object> reader() {
        //TODO create reader
        return null;
    }

    @Bean
    public ItemProcessor<Object, Object> processor() {
        //TODO create processor
        return null;
    }

    @Bean
    public ItemWriter<Object> writer() {
        //TODO create writer
        return null;
    }

    @Bean
    public ChunkListener listener() {
        //TODO create listener
        return null;
    }
}
