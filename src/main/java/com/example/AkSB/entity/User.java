package com.example.AkSB.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
//@Getter
//@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User {

        @Id
        private ObjectId id;
        @Indexed(unique = true)//indexing on username
        @NonNull
        private String userName;
        private  String email;
        private boolean sentimentAnalysis;
        @NonNull
        private String password;
        @DBRef//creating reference of users collection journal entries
        private List<JournalEntry> journalEntries=new ArrayList<>();
        private List<String> roles = new ArrayList<>();

}
