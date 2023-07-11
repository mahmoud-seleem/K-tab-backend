package com.example.Backend.jsonConversion;

import com.example.Backend.model.Author;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class AuthorSerializer extends StdSerializer<Author> {

    public AuthorSerializer() {
        this(null);
    }
    protected AuthorSerializer(Class<Author> t) {
        super(t);
    }

    @Override
    public void serialize(Author author, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", author.getAuthorId().toString());
        jsonGenerator.writeStringField("authorName", author.getAuthorName());
        jsonGenerator.writeEndObject();
    }
}
