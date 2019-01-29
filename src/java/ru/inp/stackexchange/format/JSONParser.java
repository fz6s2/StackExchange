package ru.inp.stackexchange.format;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Reader;

/**
 * JSON parser for auto parsing annotated classes.
 */
public class JSONParser {
    /**
     * Read object from JSON stream. 
     * Parsing composite dual parameterized types. 
     * For example, {@code List<String>, SEResponse<SEQuestion>}.
     * @param <T> type # 1. Can be a list or other wrapper.
     * @param <E> type # 2. Type of real object.
     * @param reader input stream
     * @param rootClass class of type T
     * @param contentClass class for type E
     * @return {@code T<E>} object
     * @throws IOException 
     */
    public static <T, E> T getObject(Reader reader, Class<T> rootClass, Class<E> contentClass) 
        throws IOException{
        
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper
                .getTypeFactory()
                .constructParametricType(rootClass, contentClass);
        
        return mapper.readValue(reader, type);
    }
    
    /**
     * Read object from JSON stream. 
     * Parsing the stream as a single object.
     * @param <T> type of an object
     * @param reader input stream
     * @param contentClass class of type T
     * @return T object
     * @throws IOException 
     */
    public static <T> T getObject(Reader reader, Class<T> contentClass) 
            throws IOException{
        
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(reader, contentClass);
    }
}
