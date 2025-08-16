package edu.arizona.josesosa.enterprise.http;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UrlQueryStringParserTest {

    @Test
    void parseNullAndEmptyYieldsEmptyMap() {
        assertTrue(UrlQueryStringParser.parse(null).isEmpty());
        assertTrue(UrlQueryStringParser.parse("").isEmpty());
    }

    @Test
    void parsesSinglePairAndMissingValue() {
        Map<String, String> map = UrlQueryStringParser.parse("a=b");
        assertEquals("b", map.get("a"));

        Map<String, String> missingEq = UrlQueryStringParser.parse("a");
        assertEquals("", missingEq.get("a"));

        Map<String, String> emptyVal = UrlQueryStringParser.parse("a=");
        assertEquals("", emptyVal.get("a"));
    }

    @Test
    void lastRepeatedKeyWinsAndTrailingAmpersandIgnored() {
        Map<String, String> map = UrlQueryStringParser.parse("a=1&a=2&");
        assertEquals(1, map.size());
        assertEquals("2", map.get("a"));
    }

    @Test
    void urlDecodingPercentSequencesAndPlusAsSpace() {
        Map<String, String> map = UrlQueryStringParser.parse("first%20name=Jose+Sosa&msg=one%20two&x=1%2B2");
        assertEquals("Jose Sosa", map.get("first name"));
        assertEquals("one two", map.get("msg"));
        assertEquals("1+2", map.get("x"));
    }

    @Test
    void emptyKeyIsIgnored() {
        Map<String, String> map = UrlQueryStringParser.parse("=v&=x&ok=1");
        assertEquals(1, map.size());
        assertEquals("1", map.get("ok"));
    }
}
