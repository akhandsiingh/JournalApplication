package com.example.AkSB.service;

import com.example.AkSB.JournalEntryRepository.JournalEntryRepository;
import com.example.AkSB.entity.JournalEntry;
import com.example.AkSB.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private  UserService userService;
    private static final Logger logger= LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
     try {
         User user = userService.findByUserName(userName);
         journalEntry.setDate(LocalDateTime.now());
         JournalEntry saved = journalEntryRepository.save(journalEntry);
         user.getJournalEntries().add(saved);
         userService.saveUser(user);
     }catch (Exception e){
         logger.info("exception");
         throw new RuntimeException("An error Occured while saving entry"+e);
     }
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
       boolean removed=false;
        try {
         User user = userService.findByUserName(userName);
          removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
         if (removed){
             userService.saveUser(user);//updates data of user;
             journalEntryRepository.deleteById(id);
         }
     }catch(Exception e){
       log.error("Exception",e);
         throw new RuntimeException("An error Occured"+e);
     }
        return removed;
    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> findByUserName(String userName){
        User user = userService.findByUserName(userName);

        if (user != null) {
            return user.getJournalEntries();
        }

        return Collections.emptyList();
    }
}

