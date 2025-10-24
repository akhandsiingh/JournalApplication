package com.example.AkSB.JournalEntryRepository;


import com.example.AkSB.entity.JournalEntry;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId>{

}
