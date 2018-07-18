package com.loop54.spring.test.model;

import com.loop54.model.Entity;
import com.loop54.model.FacetType;
import com.loop54.model.response.DistinctFacet;
import com.loop54.model.response.Facet;
import com.loop54.model.response.RangeFacet;

import java.util.List;

public class ModelUtil {

    public static EntityModel[] getEntityModelsFromResponse(List<Entity> items){
        return items.stream()
                .map(ModelUtil::createModelFromEntity).toArray(EntityModel[]::new);
    }

    public static EntityModel createModelFromEntity(Entity entity){
        EntityModel model = new EntityModel();

        model.setId(entity.id);
        model.setType(entity.type);
        model.setName(entity.getAttributeValueOrNull("title", String.class));
        model.setPrice(entity.getAttributeValueOrNull("price", Double.class));
        model.setCategory(entity.getAttributeValueOrNull("category", String.class));
        model.setImageUrl(entity.getAttributeValueOrNull("imageurl", String.class));
        model.setManufacturer(entity.getAttributeValueOrNull("manufacturer", String.class));

        return model;
    }

    public static DistinctFacetModel[] getDistinctFacetModelFromResponse(List<Facet> facets){
        return facets.stream()
                .filter(f -> f.getType() == FacetType.DISTINCT)
                .map(f -> createDistinctFacetModel((DistinctFacet) f))
                .toArray(DistinctFacetModel[]::new);
    }

    private static DistinctFacetModel createDistinctFacetModel(DistinctFacet facet){
        DistinctFacetModel model = new DistinctFacetModel();

        model.setName(facet.name);
        model.setOptions(facet.getItems().stream()
                .map(f -> new DistinctFacetOptionModel((String)f.item, f.count, f.selected))
                .toArray(DistinctFacetOptionModel[]::new));

        return model;
    }

    public static RangeFacetModel[] getRangeFacetModelFromResponse(List<Facet> facets){
        return facets.stream()
                .filter(f -> f.getType() == FacetType.RANGE)
                .map(f -> createRangeFacetModel((RangeFacet) f))
                .toArray(RangeFacetModel[]::new);
    }

    private static RangeFacetModel createRangeFacetModel(RangeFacet facet){
        RangeFacetModel model = new RangeFacetModel();

        model.setName(facet.name);
        model.setMin(facet.getMin(Double.class).toString());
        model.setMax(facet.getMax(Double.class).toString());

        return model;
    }
}
