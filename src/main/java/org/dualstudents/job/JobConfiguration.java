package org.dualstudents.job;

import org.dualstudents.model.Candidate;
import org.dualstudents.processor.CandidateProcessor;
import org.dualstudents.writer.CandidateWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

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
    public Step step1(FlatFileItemReader<Candidate> candidateItemReader, CandidateProcessor processor, CandidateWriter writer, DemonstrationListener demonstrationListener) {
        int chunkSize = Integer.parseInt(System.getProperty("chunkSize"));
        return stepBuilderFactory.get("step1")
                .<Candidate, Candidate>chunk(chunkSize)
                .reader(candidateItemReader)
                .processor(processor)
                .writer(writer)
                .listener(demonstrationListener)
                .build();
    }

    @Bean
    public FlatFileItemReader<Candidate> reader() {
        String inputFile = System.getProperty("inputFile");
        return new FlatFileItemReaderBuilder<Candidate>()
                .name("candidateItemReader")
                .resource(new FileSystemResource(inputFile))
                .delimited().delimiter(";")
                .names("ID", "name", "home", "passed")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Candidate.class);
                }})
                .build();
    }

    @Bean
    public CandidateProcessor processor() {
        return new CandidateProcessor();
    }

    @Bean
    public CandidateWriter writer() {
        return new CandidateWriter();
    }

    @Bean
    public DemonstrationListener listener() {
        return new DemonstrationListener();
    }
}
