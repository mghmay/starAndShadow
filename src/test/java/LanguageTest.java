import com.starAndShadow.may.sakila.model.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LanguageTest {
    int languageId;
    String name;
    Set<Language> languages = new HashSet<>();
    String lastUpdate;

    Language testLanguage = new Language();

    @BeforeEach
    void beforeAll() {
        Language language = new Language();
        languages.add(language);
        testLanguage = new Language(languageId, name, languages,lastUpdate);
    }
    @Test
    void test_getLanguageId(){
        assertEquals(languageId, testLanguage.getLanguageId(), "returns language id.");
    }
    @Test
    void test_getName(){
        assertEquals(name, testLanguage.getName(), "returns name.");
    }
    @Test
    void test_getLanguages(){
        assertEquals(languages, testLanguage.getLanguages(), "returns languages.");
    }
    @Test
    void test_getLastUpdate(){
        assertEquals(lastUpdate, testLanguage.getLastUpdate(), "returns last update.");
    }
}
