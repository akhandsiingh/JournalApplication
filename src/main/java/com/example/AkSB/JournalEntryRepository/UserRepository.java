package com.example.AkSB.JournalEntryRepository;

import com.example.AkSB.entity.JournalEntry;
import com.example.AkSB.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
 User findByUserName(String userName);
}