package com.mdudzisz.ticketbookingapp.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimestampSerializer extends JsonSerializer<Timestamp> {

    public static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    @Override
    public void serialize(Timestamp timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_8601_FORMAT);
        jsonGenerator.writeString(dateFormat.format(calendar.getTime()));
    }

}
