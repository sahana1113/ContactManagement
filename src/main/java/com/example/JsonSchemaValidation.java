package com.example;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.InputStream;

public class JsonSchemaValidation {

    public static void main(String[] args) {
        try {
            InputStream schemaStream = new FileInputStream("schema.json");
            JSONObject rawSchema = new JSONObject(new JSONTokener(schemaStream));
            Schema schema = SchemaLoader.load(rawSchema);
            InputStream jsonStream = new FileInputStream("db.json");
            JSONObject jsonData = new JSONObject(new JSONTokener(jsonStream));

            schema.validate(jsonData); 

            System.out.println("Validation successful!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

