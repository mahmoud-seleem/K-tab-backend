package com.example.Backend.jsonConversion;

import com.example.Backend.schema.BookInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class BookHeaderSerializer extends StdSerializer<BookInfo> {
    public BookHeaderSerializer(){
        this(null);
    }
    public BookHeaderSerializer(Class<BookInfo> t) {
        super(t);
    }

    @Override
    public void serialize(BookInfo bookInfo, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("bookCoverPath", bookInfo.getBookCoverPath());
        jsonGenerator.writeStringField("title", bookInfo.getTitle());
        jsonGenerator.writeObjectField("bookId",bookInfo.getBookId());
        jsonGenerator.writeEndObject();
    }
}
