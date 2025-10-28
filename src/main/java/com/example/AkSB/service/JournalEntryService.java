package com.example.AkSB.service;

import com.example.AkSB.JournalEntryRepository.JournalEntryRepository;
import com.example.AkSB.entity.JournalEntry;
import com.example.AkSB.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private  UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
     try {
         User user = userService.findByUserName(userName);
         journalEntry.setDate(LocalDateTime.now());
         JournalEntry saved = journalEntryRepository.save(journalEntry);
         user.getJournalEntries().add(saved);
         user.setUserName(null);
         userService.saveEntry(user);
     }catch (Exception e){
         System.out.println(e);
         throw new RuntimeException("An error Occured while saving entry"+e);
     }
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    public void deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x-> x.getId().equals(id));
        userService.saveEntry(user);//updates data of user;
        journalEntryRepository.deleteById(id);
    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

}

