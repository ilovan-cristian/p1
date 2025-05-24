package copilot12354.mpp;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalDateTimeConverter
        implements AttributeConverter<LocalDateTime,String>
{
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Override
    public String convertToDatabaseColumn(LocalDateTime attribute) {
        return attribute == null
                ? null
                : attribute.format(FMT);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        return (dbData == null || dbData.isBlank())
                ? null
                : LocalDateTime.parse(dbData, FMT);
    }
}