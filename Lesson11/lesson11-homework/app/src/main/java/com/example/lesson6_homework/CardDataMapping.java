package com.example.lesson6_homework;
import com.google.firebase.Timestamp;
import java.util.HashMap;
import java.util.Map;
public class CardDataMapping {
    public static class Fields {
    public static final String NAME="name";
    public static final String DESCRIPTION="description";
    public static final String DATEOFCREATION="dateofcreation";
    }
    public static CardData toCardData(String id, Map<String, Object> doc) {
        CardData answer = new CardData((String) doc.get(Fields.NAME),
                (String) doc.get(Fields.DESCRIPTION),
                (String)doc.get(Fields.DATEOFCREATION));
        answer.setId(id);
        return answer;
    }
    public static Map<String, Object> toDocument(CardData cardData){
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.NAME, cardData.getTitle());
        answer.put(Fields.DESCRIPTION, cardData.getDescription());
        answer.put(Fields.DATEOFCREATION, cardData.getDateOfCreation());
        return answer;
    }
}
