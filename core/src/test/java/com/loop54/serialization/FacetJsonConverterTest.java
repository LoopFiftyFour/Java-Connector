package com.loop54.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.loop54.model.FacetType;
import com.loop54.model.response.DistinctFacet;
import com.loop54.model.response.Facet;
import com.loop54.model.response.RangeFacet;
import com.loop54.serialization.FacetJsonDeserializer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FacetJsonConverterTest {

	@Test
    public void deserializeUnexpectedProperty() throws IOException {
        String facetString = "{\"unexpected\": \"indeed\", \"name\": \"category1\", \"type\": \"distinct\", \"items\": []}";
        DistinctFacet distinctFacet = deserializeFacet(facetString, DistinctFacet.class);
    }
	
    @Test
    public void deserializeEmptyDistinctFacet() throws IOException {
        String facetString = "{\"approximated\": true, \"name\": \"category1\", \"type\": \"distinct\", \"items\": []}";
        DistinctFacet distinctFacet = deserializeFacet(facetString, DistinctFacet.class);

        assertEquals("category1", distinctFacet.getName());
        assertEquals(FacetType.DISTINCT, distinctFacet.getType());
        assertEquals(false, distinctFacet.hasValues());
        assertEquals(0, distinctFacet.getItems().size());
    }

    @Test
    public void deserializeDistinctStringFacet() throws IOException {
        String facetString = "{\"approximated\": true, \"name\": \"category1\", \"type\": \"distinct\", \"items\": [{\"item\": \"Toys\", \"count\": 3, \"selected\": false}, {\"item\": \"Teddy bears\", \"count\": 2, \"selected\": true}]}";
        DistinctFacet distinctFacet = deserializeFacet(facetString, DistinctFacet.class);

        assertEquals("category1", distinctFacet.getName());
        assertEquals(FacetType.DISTINCT, distinctFacet.getType());
        assertEquals(true, distinctFacet.hasValues());
        assertEquals(2, distinctFacet.getItems().size());
        assertFacetOption(distinctFacet.getItems().get(0), "Toys", 3, false);
        assertFacetOption(distinctFacet.getItems().get(1), "Teddy bears", 2, true);
    }

    @Test
    public void deserializeDistinctDoubleFacet() throws IOException {
        String facetString = "{\"approximated\": true, \"name\": \"vatrate\", \"type\": \"distinct\", \"items\": [{\"item\": 25, \"count\": 10, \"selected\": false}, {\"item\": 13.37, \"count\": 3, \"selected\": true}, {\"item\": 12.5, \"count\": 2, \"selected\": false}]}";
        DistinctFacet distinctFacet = deserializeFacet(facetString, DistinctFacet.class);

        assertEquals("vatrate", distinctFacet.getName());
        assertEquals(FacetType.DISTINCT, distinctFacet.getType());
        assertEquals(true, distinctFacet.hasValues());
        assertEquals(3, distinctFacet.getItems().size());
        assertFacetOption(distinctFacet.getItems().get(0), 25.0, 10, false);
        assertFacetOption(distinctFacet.getItems().get(1), 13.37, 3, true);
        assertFacetOption(distinctFacet.getItems().get(2), 12.5, 2, false);
    }

    private <T> void assertFacetOption(DistinctFacet.DistinctFacetItem item, T value, int count, boolean selected) {
        assertEquals(value, item.getItem(value.getClass()));
        assertEquals(count, item.count);
        assertEquals(selected, item.selected);
    }

    @Test
    public void deserializeEmptyRangeFacet() throws IOException {
        String facetString = "{\"name\": \"category1\", \"type\": \"range\"}";
        RangeFacet rangeFacet = deserializeFacet(facetString, RangeFacet.class);

        assertEquals("category1", rangeFacet.getName());
        assertEquals(FacetType.RANGE, rangeFacet.getType());
        assertEquals(false, rangeFacet.hasValues());
        assertEquals(null, rangeFacet.getMin(String.class));
        assertEquals(null, rangeFacet.getMax(String.class));
        assertEquals(null, rangeFacet.getSelectedMin(String.class));
        assertEquals(null, rangeFacet.getSelectedMax(String.class));
    }

    @Test
    public void deserializeRangeFacet() throws IOException {
        String facetString = "{\"name\": \"price\", \"type\": \"range\", \"min\": 3, \"max\": 246 }";
        RangeFacet rangeFacet = deserializeFacet(facetString, RangeFacet.class);

        assertEquals("price", rangeFacet.getName());
        assertEquals(FacetType.RANGE, rangeFacet.getType());
        assertEquals(true, rangeFacet.hasValues());

        //Get the values as int
        assertEquals(3, (int)rangeFacet.getMin(Integer.class));
        assertEquals(246, (int)rangeFacet.getMax(Integer.class));
        assertEquals(null, rangeFacet.getSelectedMin(Integer.class));
        assertEquals(null, rangeFacet.getSelectedMax(Integer.class));

        //Get the values as double
        assertEquals(3.0, (double)rangeFacet.getMin(Double.class));
        assertEquals(246.0, (double)rangeFacet.getMax(Double.class));
        assertEquals(null, rangeFacet.getSelectedMin(Double.class));
        assertEquals(null, rangeFacet.getSelectedMax(Double.class));
    }

    @Test
    public void deserializeSelectedRangeFacet() throws IOException {
        String facetString = "{\"name\": \"price\", \"type\": \"range\", \"min\": 3, \"max\": 246, \"selectedMin\": 5, \"selectedMax\": 100.5}";
        RangeFacet rangeFacet = deserializeFacet(facetString, RangeFacet.class);

        assertEquals("price", rangeFacet.getName());
        assertEquals(FacetType.RANGE, rangeFacet.getType());
        assertEquals(true, rangeFacet.hasValues());

        //Get the values as int
        assertEquals(3, (int)rangeFacet.getMin(Integer.class));
        assertEquals(246, (int)rangeFacet.getMax(Integer.class));
        assertEquals(5, (int)rangeFacet.getSelectedMin(Integer.class));
        assertEquals(100, (int)rangeFacet.getSelectedMax(Integer.class));

        //Get the values as double
        assertEquals(3.0, (double)rangeFacet.getMin(Double.class));
        assertEquals(246.0, (double)rangeFacet.getMax(Double.class));
        assertEquals(5.0, (double)rangeFacet.getSelectedMin(Double.class));
        assertEquals(100.5, (double)rangeFacet.getSelectedMax(Double.class));
    }

    private <T extends Facet> T deserializeFacet(String json, Class<T> clazz) throws IOException {
        ObjectMapper mapper = Serializer.MAPPER;
        Facet facet = mapper.readValue(json, Facet.class);
        return (T)facet;
    }
}
