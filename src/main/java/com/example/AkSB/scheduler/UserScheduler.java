package com.example.AkSB.scheduler;

import com.example.AkSB.JournalEntryRepository.UserRepositoryImpl;
import com.example.AkSB.entity.JournalEntry;
import com.example.AkSB.entity.User;
import com.example.AkSB.enums.Sentiment;
import com.example.AkSB.model.SentimentData;
import com.example.AkSB.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserScheduler {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail(){
        List<User> users = userRepository.getUserForSA();
        for(User user:users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment>  sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSetiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCounts=new HashMap<>();
            for(Sentiment sentiment:sentiments){
                if(sentiment!=null){
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
                }
                Sentiment mostFrequentSentiment=null;
                int maxCount=0;
                for(Map.Entry<Sentiment,Integer>entry:sentimentCounts.entrySet()){
                    if(entry.getValue()>maxCount){
                        maxCount= entry.getValue();
                        mostFrequentSentiment=entry.getKey();
                    }
                }
                if(mostFrequentSentiment!=null){
//                    emailService.sendEmail(user.getEmail(),"Sentiment for last Seven Days",mostFrequentSentiment.toString());
                    SentimentData sentimentData=SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last Seven Days"+mostFrequentSentiment).build();
                    kafkaTemplate.send("weekly-sentiments",sentimentData.getEmail(),sentimentData);
                }
            }

        }
    }
}
